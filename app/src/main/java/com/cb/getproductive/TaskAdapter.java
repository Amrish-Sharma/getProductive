package com.cb.getproductive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    private OnTaskToggleListener toggleListener;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; // milliseconds


    public interface OnTaskToggleListener {
        void onTaskToggle(Task task);
    }

    public TaskAdapter(List<Task> tasks, OnTaskToggleListener toggleListener) {
        this.tasks = tasks;
        this.toggleListener = toggleListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskText.setText(task.getText());
        holder.checkBox.setChecked(task.isCompleted());

        // Enable editing only for pending tasks
        holder.taskText.setEnabled(false);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            toggleListener.onTaskToggle(task);
            holder.taskText.setEnabled(false); // Disable editing when task is completed
        });
        holder.taskText.setOnClickListener(new View.OnClickListener() {
            private long lastClickTime = 0;

            @Override
            public void onClick(View v) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    if (!task.isCompleted()) {
                        holder.taskText.setEnabled(true);
                        holder.taskText.requestFocus();
                    }
                }
                lastClickTime = clickTime;
            }
        });
        holder.taskText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                task.setText(holder.taskText.getText().toString());
                toggleListener.onTaskToggle(task);
                holder.taskText.setEnabled(false); // Disable editing when focus is lost
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        EditText taskText;
        CheckBox checkBox;


       public  TaskViewHolder(View itemView) {
            super(itemView);
           taskText = itemView.findViewById(R.id.taskText);
           checkBox = itemView.findViewById(R.id.taskCheckbox);

        }

    }
}

