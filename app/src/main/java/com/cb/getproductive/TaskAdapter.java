package com.cb.getproductive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public TaskAdapter(List<Task> tasks, OnTaskClickListener listener) {
        this.tasks = tasks;
        this.listener = listener;
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
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView taskText;

        TaskViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.taskCheckbox);
            taskText = itemView.findViewById(R.id.taskText);

            checkBox.setOnClickListener(v -> {
                Task task = tasks.get(getAdapterPosition());
                task.setCompleted(checkBox.isChecked());
                listener.onTaskClick(task);
            });
        }

        void bind(Task task) {
            taskText.setText(task.getText());
            checkBox.setChecked(task.isCompleted());
            taskText.setAlpha(task.isCompleted() ? 0.5f : 1.0f);  // Grey out completed tasks
        }
    }
}

