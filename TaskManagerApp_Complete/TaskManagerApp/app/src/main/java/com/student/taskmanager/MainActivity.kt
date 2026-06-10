package com.student.taskmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.student.taskmanager.adapter.TaskAdapter
import com.student.taskmanager.model.Priority
import com.student.taskmanager.utils.TaskRepository

/**
 * MainActivity — the single screen of the Task Manager app.
 *
 * Concepts demonstrated:
 *  - AppCompatActivity lifecycle (onCreate)
 *  - RecyclerView setup with a custom Adapter
 *  - AlertDialog for user input
 *  - Spinner for enum selection
 *  - Repository pattern usage
 *  - Options menu
 */
class MainActivity : AppCompatActivity() {

    // Repository is our data layer
    private val repository = TaskRepository()

    // Adapter holds the list and handles click events
    private lateinit var adapter: TaskAdapter

    // UI references
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvStats: TextView
    private lateinit var btnAddTask: com.google.android.material.floatingactionbutton.FloatingActionButton

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind views
        recyclerView = findViewById(R.id.recyclerViewTasks)
        tvStats      = findViewById(R.id.tvStats)
        btnAddTask   = findViewById(R.id.fabAddTask)

        setupRecyclerView()
        refreshUI()

        // FAB opens the Add Task dialog
        btnAddTask.setOnClickListener { showAddTaskDialog() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_priority -> {
                val sorted = repository.getTasksSortedByPriority()
                adapter.submitList(sorted.toMutableList())
                Toast.makeText(this, "Sorted by priority", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_show_pending -> {
                adapter.submitList(repository.getTasks(filterCompleted = false).toMutableList())
                Toast.makeText(this, "Showing pending tasks", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_show_all -> {
                refreshUI()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // -------------------------------------------------------------------------
    // Setup
    // -------------------------------------------------------------------------

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            onToggleComplete = { task ->
                repository.toggleComplete(task.id)
                refreshUI()
            },
            onDeleteTask = { task ->
                // Confirm before deleting
                AlertDialog.Builder(this)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete \"${task.title}\"?")
                    .setPositiveButton("Delete") { _, _ ->
                        repository.deleteTask(task.id)
                        refreshUI()
                        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    // -------------------------------------------------------------------------
    // Add Task Dialog
    // -------------------------------------------------------------------------

    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)

        val etTitle       = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val etDueDate     = dialogView.findViewById<EditText>(R.id.etDueDate)
        val spinnerPriority = dialogView.findViewById<Spinner>(R.id.spinnerPriority)

        // Populate the Priority spinner using enum values
        val priorityLabels = Priority.values().map { it.label }
        spinnerPriority.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            priorityLabels
        )

        AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title       = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()
                val dueDate     = etDueDate.text.toString().trim()
                val priority    = Priority.fromLabel(spinnerPriority.selectedItem.toString())

                // Basic validation
                if (title.isEmpty()) {
                    Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                repository.addTask(
                    title       = title,
                    description = description.ifEmpty { "No description" },
                    priority    = priority,
                    dueDate     = dueDate.ifEmpty { "No due date" }
                )
                refreshUI()
                Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Refreshes the RecyclerView and stats bar from the repository.
     */
    private fun refreshUI() {
        adapter.submitList(repository.getTasks().toMutableList())
        tvStats.text = repository.getSummaryStats()
    }
}
