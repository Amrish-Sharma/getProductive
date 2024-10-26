package com.cb.getproductive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val listener: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.taskCheckbox)
        private val taskText: TextView = itemView.findViewById(R.id.taskText)

        fun bind(task: Task) {
            taskT

            ext.text = task.text
            checkBox.isChecked = task.isCompleted
            taskText.alpha = if (task.isCompleted) 0.5f else 1.0f

            checkBox.setOnClickListener {
                task.isCompleted = checkBox.isChecked
                listener(task)
            }
        }
    }
}
