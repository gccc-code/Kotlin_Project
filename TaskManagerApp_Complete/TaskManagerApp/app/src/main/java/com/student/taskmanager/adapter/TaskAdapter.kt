package com.student.taskmanager.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.student.taskmanager.R
import com.student.taskmanager.model.Task

/**
 * TaskAdapter binds a list of Tasks to a RecyclerView.
 *
 * Key concepts demonstrated:
 *  - ListAdapter with DiffUtil for efficient list updates
 *  - ViewHolder pattern
 *  - Lambda callbacks for user interactions
 */
class TaskAdapter(
    private val onToggleComplete: (Task) -> Unit,
    private val onDeleteTask: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    // -------------------------------------------------------------------------
    // ViewHolder
    // -------------------------------------------------------------------------

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle:       TextView    = itemView.findViewById(R.id.tvTaskTitle)
        private val tvDescription: TextView    = itemView.findViewById(R.id.tvTaskDescription)
        private val tvDueDate:     TextView    = itemView.findViewById(R.id.tvDueDate)
        private val tvPriority:    TextView    = itemView.findViewById(R.id.tvPriority)
        private val btnComplete:   ImageButton = itemView.findViewById(R.id.btnToggleComplete)
        private val btnDelete:     ImageButton = itemView.findViewById(R.id.btnDeleteTask)

        /**
         * Bind a Task to this ViewHolder's views.
         */
        fun bind(task: Task) {
            tvTitle.text       = task.title
            tvDescription.text = task.description
            tvDueDate.text     = "Due: ${task.dueDate}"
            tvPriority.text    = task.priority.label

            // Set priority badge color
            tvPriority.setBackgroundColor(Color.parseColor(task.priority.colorHex))

            // Strike-through title if completed
            if (task.isCompleted) {
                tvTitle.paintFlags = tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvTitle.alpha = 0.5f
                btnComplete.setImageResource(R.drawable.ic_check_circle)
            } else {
                tvTitle.paintFlags = tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                tvTitle.alpha = 1.0f
                btnComplete.setImageResource(R.drawable.ic_circle_outline)
            }

            // Wire up click listeners using lambda callbacks
            btnComplete.setOnClickListener { onToggleComplete(task) }
            btnDelete.setOnClickListener   { onDeleteTask(task) }
        }
    }

    // -------------------------------------------------------------------------
    // Adapter overrides
    // -------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // -------------------------------------------------------------------------
    // DiffUtil — tells RecyclerView exactly what changed (avoids full redraws)
    // -------------------------------------------------------------------------

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem == newItem
    }
}
