# 📱 Task Manager — Android/Kotlin OJT Portfolio Project

A beginner-friendly Android application built with **Kotlin** to demonstrate core mobile development
skills for an OJT/internship application.

---

## 📌 About This Project

**Task Manager** is a simple but well-structured Android app that lets users:
- View a list of tasks with priority levels (High / Medium / Low)
- Add new tasks with a title, description, due date, and priority
- Mark tasks as complete (with visual strike-through)
- Delete tasks with a confirmation dialog
- Filter tasks (All / Pending Only) and sort by priority

---

## 🛠 Tech Stack

| Technology | Purpose |
|---|---|
| **Kotlin** | Primary programming language |
| **Android SDK 34** | Target platform |
| **RecyclerView + ListAdapter** | Efficient list rendering |
| **Material Design 3** | UI components (FAB, TextInputLayout, CardView) |
| **AppCompatActivity** | Base activity |
| **AlertDialog** | Add/delete confirmation modals |
| **Options Menu** | Filter and sort actions |

---

## 📂 Project Structure

```
app/src/main/java/com/student/taskmanager/
│
├── model/
│   └── Task.kt              # Data class + Priority enum
│
├── adapter/
│   └── TaskAdapter.kt       # RecyclerView adapter with DiffUtil
│
├── utils/
│   └── TaskRepository.kt    # Data layer (Repository pattern)
│
└── MainActivity.kt          # Single-screen entry point
```

---

## 💡 Kotlin Concepts Demonstrated

| Concept | Where Used |
|---|---|
| **Data classes** | `Task.kt` — clean model with auto `equals`, `copy`, `toString` |
| **Enum classes with properties** | `Priority` enum with `label` and `colorHex` |
| **Companion object** | `Priority.fromLabel()` factory method |
| **Lambda functions** | Adapter callbacks (`onToggleComplete`, `onDeleteTask`) |
| **Extension-like patterns** | `getSummary()` on Task |
| **String templates** | Throughout (e.g., `"Due: ${task.dueDate}"`) |
| **Null safety** | `?: MEDIUM` default in `fromLabel()`, `?: return false` in repository |
| **Higher-order functions** | `filter`, `count`, `sortedBy`, `removeIf` on lists |
| **Default parameters** | `isCompleted: Boolean = false` in Task constructor |
| **`when` expression** | `getTasks(filterCompleted)` return branching |

---

## 🚀 How to Run

1. Open **Android Studio** (Hedgehog or newer)
2. Clone or copy this project folder
3. Open the project (`File → Open`)
4. Wait for Gradle sync to complete
5. Run on an emulator or physical device (API 24+)

> **Minimum SDK:** 24 (Android 7.0 Nougat)
> **Target SDK:** 34 (Android 14)

---

## 🔮 Possible Improvements (for future versions)

- [ ] Persist tasks using **Room Database**
- [ ] Add due date **DatePicker** dialog
- [ ] Implement **MVVM architecture** with ViewModel + LiveData
- [ ] Add task **categories/tags**
- [ ] Push **notifications** for upcoming due dates
- [ ] Migrate UI to **Jetpack Compose**

---

## 👤 About the Developer

Built as a portfolio project for an OJT internship application.
Currently a **2nd year BS Computer Science** student with a focus on Android/Mobile Development.

---

*This project intentionally keeps architecture simple and beginner-appropriate,
while demonstrating clean code practices suitable for an entry-level Android role.*
