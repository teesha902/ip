package storage;

import java.io.BufferedReader; //read data source file (line by line) - input needs to be wrapped by FileReader
import java.io.BufferedWriter; //read data source file (line by line) - input needs to be wrapped by FileReader
import java.io.File; //no r/w - only interacts with file metadata (exists, size, etc.)
import java.io.FileReader; // reads file contents (char by char)
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import exception.PiggyException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

/**
 * Handles the storage and retrieval of tasks from a file.
 * Provides methods to load, save, and ensure the file's existence.
 */
public class Storage {
    private static final String FILE_PATH = "data/taskList.txt";
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Ensures the task file and its parent directory exist.
     */
    private static void ensureFileExists() {
        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile();

            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while ensuring the file exists: " + e.getMessage());
        }
    }

    /**
     * Loads the list of tasks from the file into an ArrayList.
     * Parses each line and reconstructs corresponding Task objects.
     *
     * @return An ArrayList of Task objects representing the saved tasks.
     * @throws PiggyException If an error occurs while loading tasks from the file.
     */
    public static ArrayList<Task> loadList() throws PiggyException {
        ensureFileExists();
        ArrayList<Task> taskList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String currLine;

            while ((currLine = reader.readLine()) != null) {
                Task task = parseTask(currLine);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (Exception e) {
            throw new PiggyException("An error occurred while loading tasks: " + e.getMessage());
        }

        assert taskList != null : "Task list should never be null after loading";
        return taskList;
    }

    /**
     * Parses a task entry from the file and reconstructs the corresponding Task object.
     *
     * @param line The line read from the file.
     * @return A Task object or null if parsing fails.
     */
    private static Task parseTask(String line) {
        String[] taskParts = line.split(" \\| ");
        if (taskParts.length < 3) {
            System.out.println("Warning: Skipping corrupted entry - " + line);
            return null;
        }

        String status = taskParts[0].trim();
        boolean isDone = status.equals("X");
        String typeAndName = taskParts[1].trim();

        if (!typeAndName.contains(":")) {
            System.out.println("Warning: Skipping malformed task entry - " + line);
            return null;
        }

        String[] typeSplit = typeAndName.split(":");
        if (typeSplit.length < 2) {
            System.out.println("Warning: Skipping invalid task format - " + line);
            return null;
        }

        String type = typeSplit[0].trim();
        String description = typeSplit[1].trim();
        String timeInfo = taskParts[2].trim();

        return createTask(type, description, timeInfo, isDone, line);
    }

    /**
     * Creates a Task object based on the provided parameters.
     *
     * @param type        The task type (T, D, E).
     * @param description The task description.
     * @param timeInfo    The time information for Deadline/Event.
     * @param isDone      Whether the task is marked as done.
     * @param line        The original line from the file for error logging.
     * @return A Task object or null if creation fails.
     */
    private static Task createTask(String type, String description, String timeInfo, boolean isDone, String line) {
        try {
            switch (type) {
            case "T":
                return new ToDo(description, isDone);
            case "D":
                return createDeadline(description, timeInfo, isDone, line);
            case "E":
                return createEvent(description, timeInfo, isDone, line);
            default:
                System.out.println("Warning: Unknown task type in file: " + type);
                return null;
            }
        } catch (PiggyException e) {
            System.out.println("Warning: Skipping invalid entry - " + line);
            return null;
        }
    }

    private static Task createDeadline(String description, String timeInfo, boolean isDone, String line)
            throws PiggyException {
        if (!timeInfo.startsWith("by: ")) {
            throw new PiggyException("Invalid time format for deadline - " + line);
        }
        String dateStr = timeInfo.substring(4).trim();
        LocalDateTime by = LocalDateTime.parse(dateStr, INPUT_FORMATTER);
        return new Deadline(description, by, isDone);
    }

    private static Task createEvent(String description, String timeInfo, boolean isDone, String line)
            throws PiggyException {
        if (!timeInfo.startsWith("from: ") || !timeInfo.contains(", to: ")) {
            throw new PiggyException("Invalid time format for event - " + line);
        }
        String[] eventParts = timeInfo.split(", to: ");
        LocalDateTime from = LocalDateTime.parse(eventParts[0].substring(6).trim(), INPUT_FORMATTER);
        LocalDateTime to = LocalDateTime.parse(eventParts[1].trim(), INPUT_FORMATTER);
        return new Event(description, from, to, isDone);
    }

    /**
     * Updates the storage file with the current list of tasks.
     * Creates a backup before writing and restores it if an error occurs.
     *
     * @param taskList The list of tasks to be saved.
     * @throws PiggyException If an error occurs while updating the file.
     */
    public static void updateList(ArrayList<Task> taskList) throws PiggyException {
        ensureFileExists();
        File originalFile = new File(FILE_PATH);
        File backupFile = new File(FILE_PATH + ".bak");

        // Backup previous valid file
        if (originalFile.exists()) {
            originalFile.renameTo(backupFile);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : taskList) {
                writer.write(formatTask(task));
                writer.newLine();
            }
        } catch (Exception e) {
            if (backupFile.exists()) {
                backupFile.renameTo(originalFile);
            }
            throw new PiggyException("An error occurred while updating the task list: " + e.getMessage());
        } finally {
            if (backupFile.exists()) {
                backupFile.delete();
            }
        }
    }

    /**
     * Formats a Task object into a string suitable for storage.
     *
     * @param task The task to format.
     * @return A formatted string representation of the task.
     */
    private static String formatTask(Task task) {
        String status = task.status();
        String type;
        String timeInfo = "--";

        if (task instanceof ToDo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
            LocalDateTime dueDate = ((Deadline) task).getDueDate();
            timeInfo = "by: " + dueDate.format(OUTPUT_FORMATTER);
        } else if (task instanceof Event) {
            type = "E";
            LocalDateTime startTime = ((Event) task).getStart();
            LocalDateTime endTime = ((Event) task).getEnd();
            timeInfo = "from: " + startTime.format(OUTPUT_FORMATTER) + ", to: " + endTime.format(OUTPUT_FORMATTER);
        } else {
            return "";
        }
        return status + " | " + type + ": " + task.getName() + " | " + timeInfo;
    }
}
