package com.cb.getproductive

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private val PENDING_TASKS_KEY = "pending_tasks"
    private val COMPLETED_TASKS_KEY = "completed_tasks"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var pendingTasks: MutableList<Task>
    private lateinit var completedTasks: MutableList<Task>
    private lateinit var pendingAdapter: TaskAdapter
    private lateinit var completedAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setContentView(R.layout.task_item)

        sharedPreferences = getSharedPreferences("tasks_pref", MODE_PRIVATE)

        pendingTasks = loadTasks(PENDING_TASKS_KEY).toMutableList()
        completedTasks = loadTasks(COMPLETED_TASKS_KEY).toMutableList()


        pendingTasksList.layoutManager = LinearLayoutManager(this)
        completedTasksList.layoutManager = LinearLayoutManager(this)

        pendingAdapter = TaskAdapter(pendingTasks) { task -> toggleTask(task) }
        completedAdapter = TaskAdapter(completedTasks) { task -> toggleTask(task) }

        pendingTasksList.adapter = pendingAdapter
        completedTasksList.adapter = completedAdapter

        addTaskButton.setOnClickListener {
            val taskText = taskInput.text.toString().trim()
            if (taskText.isNotEmpty()) {
                addNewTask(taskText)
                taskInput.text.clear()
            }
        }
    }

    private fun toggleTask(task: Task) {
        if (task.isCompleted) {
            pendingTasks.remove(task)
            completedTasks.add(task)
        } else {
            completedTasks.remove(task)
            pendingTasks.add(task)
        }
        task.isCompleted = !task.isCompleted
        pendingAdapter.notifyDataSetChanged()
        completedAdapter.notifyDataSetChanged()
        saveTasks()
    }

    private fun addNewTask(text: String) {
        val task = Task(text, false)
        pendingTasks.add(task)
        pendingAdapter.notifyDataSetChanged()
        saveTasks()
    }

    private fun loadTasks(key: String): List<Task> {
        val json = sharedPreferences.getString(key, null)
        val type: Type = object : TypeToken<List<Task>>() {}.type
        return if (json != null) Gson().fromJson(json, type) else emptyList()
    }

    private fun saveTasks() {
        sharedPreferences.edit().apply {
            putString(PENDING_TASKS_KEY, Gson().toJson(pendingTasks))
            putString(COMPLETED_TASKS_KEY, Gson().toJson(completedTasks))
            apply()
        }
    }
}
