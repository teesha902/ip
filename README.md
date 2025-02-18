# 🐷PiggyPlanner User Guide

![PiggyPlanner GUI](Ui.png)

🐷PiggyPlanner is your friendly, task-managing chatbot, designed to help you organize your daily tasks, deadlines, and events with ease.

Whether you prefer the speed and precision of a Command Line Interface (CLI) or the convenience of an intuitive Graphical User Interface (GUI), PiggyPlanner is built to support both. It’s perfect for students, professionals, or anyone who wants a simple yet powerful way to stay on top of their schedule.

Just type your commands or interact with the GUI, and let PiggyPlanner handle the rest—like your personal assistant, but cuter. 🐽✨

---

## 🏁 Getting Started
Follow these steps to get PiggyPlanner up and running on your computer.

### 1. Install Java
Ensure you have **Java 17 or above** installed on your computer.

- Mac users: Please ensure you have the **exact JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html)**.

You can verify your Java version by running this command in your terminal: *java -version*

### 2. Download the JAR file
Download the latest version of **PiggyPlanner.jar** from [Releases](https://github.com/teesha902/ip/releases) on this repository.

### 3. Set up PiggyPlanner
- Copy the downloaded `PiggyPlanner.jar` file into a folder you want to use as PiggyPlanner’s home folder.
- Open a command terminal.
- Navigate to the folder where you placed `PiggyPlanner.jar` using: *cd path/to/your/folder*

### 4. Run the Application
Run PiggyPlanner with the following command: *java -jar PiggyPlanner.jar*

A **GUI window** should appear within a few seconds, ready to assist you with your task planning!

---

## 📖 Features 

PiggyPlanner offers a variety of commands to help you manage your tasks efficiently. Each command is designed to be intuitive and easy to use.

---

### 1. Viewing All Tasks: `list`

Displays all tasks currently in your planner, including their status (done or not done).

**Format:** `list`

---

### 2. Adding a To-Do Task: `todo`

Adds a task without any date/time attached to it.

**Format:** `todo TASK_DESCRIPTION`

**Example:** `todo Buy groceries`

*Note:*  
A To-Do task is the simplest task type with just a description.

---

### 3. Adding a Deadline Task: `deadline`

Adds a task with a deadline.

**Format:** `deadline TASK_DESCRIPTION /by d/M/yyyy HHmm`
**Example:** `deadline Submit assignment /by 25/2/2025 2359`

*Note:*
- Date and time must follow the **`d/M/yyyy HHmm`** format (e.g., 25/2/2025 2359).
- Tasks with the same name and deadline are treated as duplicates.

---

### 4. Adding an Event Task: `event`

Adds a task with a start and end time.

**Format:** `event TASK_DESCRIPTION /from d/M/yyyy HHmm /to d/M/yyyy HHmm`

**Example:** `event Project meeting /from 26/2/2025 1000 /to 26/2/2025 1200`

*Note:*
- Start time must be before end time.
- Date and time must follow the **`d/M/yyyy HHmm`** format.
- Tasks with the same name, start, and end times are treated as duplicates.

---

### 5. Marking a Task as Done: `mark`

Marks a task as done based on its number in the task list.

**Format:** `mark TASK_NUMBER`

**Example:** `mark 1`

*Note:*
- Task numbers are based on the order displayed by the `list` command.

---

### 6. Marking a Task as Not Done: `unmark`

Marks a task as not done (incomplete) based on its number in the task list.

**Format:** `unmark TASK_NUMBER`

**Example:** `unmark 1`

---

### 7. Deleting a Task: `delete`

Removes a task from the planner based on its number in the task list.

**Format:** `delete TASK_NUMBER`

**Example:** `delete 2`

---

### 8. Finding Tasks by Keyword: `find`

Searches for tasks containing specific keywords (case-insensitive).

**Format:** `find KEYWORD [MORE_KEYWORDS...]`

**Examples:** `find groceries meeting project`

*Note:*
- You can enter **one or more keywords**.
- Tasks containing **any of the keywords** will be shown.

---

### 9. Viewing Tasks on a Specific Day: `agenda`

Displays **deadlines and events** occurring on a specific date.

**Format:** `agenda for d/M/yyyy`

**Example:** `agenda for 26/2/2025`

*Note:*
- Date must follow the **`d/M/yyyy`** format.
- Only **Deadline and Event tasks** are considered for the selected day.

---

### 10. Viewing Help: `help`

Displays a summary of all commands and their formats.

**Format:** `help`

---

### 11. Exiting the Program: `bye`

Closes the PiggyPlanner application and saves the current list to your device.

**Format:** `bye`

---


## 📋 Command Summary

Here’s a quick reference table of all the commands available in **PiggyPlanner**:

| **Command**    | **Format**                                               | **Description**                                              |
|----------------|----------------------------------------------------------|---------------------------------------------------------------|
| `list`         | `list`                                                   | View all tasks.                                                |
| `todo`         | `todo TASK_DESCRIPTION`                                 | Add a ToDo task.                                               |
| `deadline`     | `deadline TASK_DESCRIPTION /by DATE TIME`               | Add a Deadline task with a due date and time.                  |
| `event`        | `event TASK_DESCRIPTION /from DATE TIME /to DATE TIME`  | Add an Event task with a start and end time.                   |
| `mark`         | `mark TASK_NUMBER`                                      | Mark a task as done.                                            |
| `unmark`       | `unmark TASK_NUMBER`                                    | Mark a task as not done.                                        |
| `delete`       | `delete TASK_NUMBER`                                    | Delete a task.                                                  |
| `find`         | `find KEYWORDS`                                         | Search for tasks containing any of the provided keywords.       |
| `agenda`       | `agenda for DATE`                                       | View all deadlines and events occurring on a specific date.     |
| `help`         | `help`                                                  | Display a list of all available commands and their formats.     |
| `bye`          | `bye`                                                   | Exit **PiggyPlanner**.                                           |

---

## 📝 Input Formatting Notes

- **Date & Time Format:** `d/M/yyyy HHmm`  
  *(e.g., `18/2/2025 2359` for 11:59 PM on February 18, 2025)*
- **Task Numbers:** Refer to the task numbers shown by the `list` command.
- **Commands are Case-Insensitive:**  
  `TODO` works the same as `todo`, `LiSt` works the same as `list`.
- **Avoid Extra Spaces:**  
  Commands must follow the exact format. Extra spaces may cause errors.

---

## ⚠️ Error Handling Notes

If you encounter an error message, **check the following common issues**:

| **Error**                                                | **Possible Cause**                                                |
|----------------------------------------------------------|---------------------------------------------------------------------|
| `I don't understand` or `Unknown Command`                | Invalid command, spelling mistake, or unsupported command.         |
| `Task number does not exist`                             | Task number is out of range or task list is empty.                  |
| `Invalid date format`                                     | Date is not in `d/M/yyyy HHmm` format (e.g., `18/2/2025 2359`).    |
| `Duplicate task detected`                                 | You tried adding a task that already exists.                       |
| `Missing description or details`                         | Task description, date, or other fields are missing.               |

✅ If you are unsure, type `help` for a list of commands.

---

## ❓ FAQ (Frequently Asked Questions)

### **Q: How do I see all my tasks?**
A: Use the `list` command.

---

### **Q: How do I delete a task?**
A: Find the task number using `list`, then run `delete TASK_NUMBER`.

---

### **Q: What if I forget the exact task name?**
A: Use `find KEYWORD` to search for tasks containing that keyword.

---

### **Q: What happens if I enter a wrong command?**
A: PiggyPlanner will **show an error message** and **prompt you to try again**.

---

### **Q: Can I undo a command?**
A: **Undo is not available** yet. Be careful when using `delete`.

---

## 🎯 Conclusion

🎉 **Congratulations on getting started with PiggyPlanner!**  
This planner is your **personal task manager**, designed to **streamline your productivity** and **keep you organized** with a touch of **fun and personality 🐷**.

Whenever you're stuck, simply type `help` to see the available commands.  
**PiggyPlanner** is built to **grow with you** – feel free to **suggest improvements** or **tweak it to suit your needs**.

Start planning your tasks today, and **let PiggyPlanner keep you on track!**

> **🐷 PiggyPlanner - Plan Smart, Squeal Less!**
