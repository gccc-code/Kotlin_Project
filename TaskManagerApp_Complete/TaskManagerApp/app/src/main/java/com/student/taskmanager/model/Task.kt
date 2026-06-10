package com.student.taskmanager.model

/**
 * Data class representing a Task.
 * Demonstrates use of Kotlin data classes, enums, and default parameters.
 */
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val dueDate: String,
    var isCompleted: Boolean = false
) {
    /**
     * Returns a short summary of the task for display purposes.
     */
    fun getSummary(): String {
        val status = if (isCompleted) "✓ Done" else "Pending"
        return "[$status] $title — Due: $dueDate (${priority.label})"
    }
}

/**
 * Enum representing task priority levels.
 * Demonstrates Kotlin enum classes with properties.
 */
enum class Priority(val label: String, val colorHex: String) {
    LOW("Low", "#4CAF50"),
    MEDIUM("Medium", "#FF9800"),
    HIGH("High", "#F44336");

    companion object {
        /**
         * Safely get a Priority from a string value.
         */
        fun fromLabel(label: String): Priority {
            return values().find { it.label.equals(label, ignoreCase = true) } ?: MEDIUM
        }
    }
}
