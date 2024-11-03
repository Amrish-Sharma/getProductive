package com.cb.getproductive;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String PENDING_TASKS_KEY = "pending_tasks";
    private static final String COMPLETED_TASKS_KEY = "completed_tasks";
    private static final String ARCHIVED_TASKS_KEY = "archived_tasks";

    private List<Task> pendingTasks;
    private List<Task> completedTasks;
    private List<Task> archivedTasks;

    private TaskAdapter pendingAdapter;
    private TaskAdapter completedAdapter;
    private ArchivedTaskAdapter archivedAdapter;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("tasks_pref", MODE_PRIVATE);

        // Load tasks from SharedPreferences
        pendingTasks = loadTasks(PENDING_TASKS_KEY);
        completedTasks = loadTasks(COMPLETED_TASKS_KEY);
        archivedTasks = loadTasks(ARCHIVED_TASKS_KEY);

        RecyclerView pendingTasksList = findViewById(R.id.pendingTasksList);
        RecyclerView completedTasksList = findViewById(R.id.completedTasksList);
        ExpandableListView archivedTasksList = findViewById(R.id.archivedTasksList);


        pendingTasksList.setLayoutManager(new LinearLayoutManager(this));
        completedTasksList.setLayoutManager(new LinearLayoutManager(this));

        // Set up adapters for pending and completed tasks
        pendingAdapter = new TaskAdapter(pendingTasks, this::toggleTask);
        completedAdapter = new TaskAdapter(completedTasks, this::toggleTask);
        archivedAdapter = new ArchivedTaskAdapter(this, archivedTasks);

        pendingTasksList.setAdapter(pendingAdapter);
        completedTasksList.setAdapter(completedAdapter);
        archivedTasksList.setAdapter(archivedAdapter);

        // Set up button for adding new tasks
        EditText taskInput = findViewById(R.id.taskInput);
        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(v -> {
            String taskText = taskInput.getText().toString().trim();
            if (!TextUtils.isEmpty(taskText)) {
                addNewTask(taskText);
                taskInput.setText("");
            }
        });
        // Archive tasks that have been completed for more than 24 hours
        archiveOldCompletedTasks();
    }

    // Method to toggle task between pending and completed
    private void toggleTask(Task task) {
        if (task.isCompleted()) {
            pendingTasks.remove(task);
            completedTasks.add(task);
        } else {
            completedTasks.remove(task);
            pendingTasks.add(task);
        }
        pendingAdapter.notifyDataSetChanged();
        completedAdapter.notifyDataSetChanged();
        saveTasks();
    }

    // Method to add new task to pending list
    private void addNewTask(String text) {
        Task task = new Task(text, false);
        pendingTasks.add(task);
        pendingAdapter.notifyDataSetChanged();
        saveTasks();
    }

    // Method to load tasks from SharedPreferences
    private List<Task> loadTasks(String key) {
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        return json != null ? new Gson().fromJson(json, type) : new ArrayList<>();
    }

    // Method to save tasks to SharedPreferences
    private void saveTasks() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PENDING_TASKS_KEY, new Gson().toJson(pendingTasks));
        editor.putString(COMPLETED_TASKS_KEY, new Gson().toJson(completedTasks));
        editor.putString(ARCHIVED_TASKS_KEY, new Gson().toJson(archivedTasks));
        editor.apply();
    }

    // Method to archive tasks that have been completed for more than 24 hours
    private void archiveOldCompletedTasks() {
        long currentTime = System.currentTimeMillis();
        List<Task> tasksToArchive = new ArrayList<>();
        for (Task task : completedTasks) {
            if (currentTime - task.getDatetimeCreated() > 24 * 60 * 60 * 1000) { // 24 hours in milliseconds
                tasksToArchive.add(task);
            }
        }
        completedTasks.removeAll(tasksToArchive);
        archivedTasks.addAll(tasksToArchive);
        completedAdapter.notifyDataSetChanged();
        archivedAdapter.notifyDataSetChanged();
        saveTasks();
    }
}

