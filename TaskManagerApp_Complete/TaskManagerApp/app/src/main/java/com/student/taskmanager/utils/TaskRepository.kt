package com.student.taskmanager.utils

import com.student.taskmanager.model.Priority
import com.student.taskmanager.model.Task

/**
 * TaskRepository acts as the single source of truth for task data.
 * Demonstrates the Repository pattern — a key Android architecture concept.
 *
 * In a real app, this would connect to a Room database or REST API.
 */
class TaskRepository {

    private val tasks = mutableListOf<Task>()
    private var nextId = 1

    init {
        // Seed with sample tasks so the app has data on first launch
        seedSampleTasks()
    }

    /**
     * Returns all tasks, optionally filtered by completion status.
     */
    fun getTasks(filterCompleted: Boolean? = null): List<Task> {
        return when (filterCompleted) {
            true  -> tasks.filter { it.isCompleted }
            false -> tasks.filter { !it.isCompleted }
            null  -> tasks.toList()
        }
    }

    /**
     * Adds a new task to the repository.
     * Returns the newly created Task.
     */
    fun addTask(title: String, description: String, priority: Priority, dueDate: String): Task {
        val task = Task(
            id          = nextId++,
            title       = title,
            description = description,
            priority    = priority,
            dueDate     = dueDate
        )
        tasks.add(task)
        return task
    }

    /**
     * Toggles the completion status of a task by ID.
     * Returns true if the task was found and updated.
     */
    fun toggleComplete(taskId: Int): Boolean {
        val task = tasks.find { it.id == taskId } ?: return false
        task.isCompleted = !task.isCompleted
        return true
    }

    /**
     * Deletes a task by ID.
     * Returns true if the task was successfully removed.
     */
    fun deleteTask(taskId: Int): Boolean {
        return tasks.removeIf { it.id == taskId }
    }

    /**
     * Returns tasks sorted by priority (HIGH → MEDIUM → LOW).
     */
    fun getTasksSortedByPriority(): List<Task> {
        val order = listOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW)
        return tasks.sortedBy { order.indexOf(it.priority) }
    }

    /**
     * Returns a summary string for all tasks (demonstrates string templates).
     */
    fun getSummaryStats(): String {
        val total     = tasks.size
        val completed = tasks.count { it.isCompleted }
        val pending   = total - completed
        return "Total: $total | Completed: $completed | Pending: $pending"
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private fun seedSampleTasks() {
        addTask(
            title       = "Set up Android Studio",
            description = "Install Android Studio, set up emulator, and run Hello World.",
            priority    = Priority.HIGH,
            dueDate     = "2024-06-10"
        )
        addTask(
            title       = "Study Kotlin Coroutines",
            description = "Go through the official Kotlin coroutines documentation and write sample code.",
            priority    = Priority.MEDIUM,
            dueDate     = "2024-06-15"
        )
        addTask(
            title       = "Build OJT Portfolio App",
            description = "Create a Task Manager app to showcase in my internship application.",
            priority    = Priority.HIGH,
            dueDate     = "2024-06-20"
        )
        addTask(
            title       = "Review Jetpack Compose basics",
            description = "Watch intro tutorials on Jetpack Compose and convert one screen.",
            priority    = Priority.LOW,
            dueDate     = "2024-06-25"
        )
    }
}
