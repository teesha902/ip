package storage;

import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;
import exception.PiggyException;

import java.util.ArrayList;
import java.io.File; //no r/w - only interacts with file metadata (exists, size, etc.)
import java.io.BufferedReader; //read data source file (line by line) - input needs to be wrapped by FileReader
import java.io.FileReader; // reads file contents (char by char)
import java.io.BufferedWriter; //read data source file (line by line) - input needs to be wrapped by FileReader
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles the storage and retrieval of tasks from a file.
 * Provides methods to load, save, and ensure the file's existence.
 */
public class Storage {

    private static final String FILE_PATH = "data/taskList.txt";
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Handles the storage and retrieval of tasks from a file.
     * Provides methods to load, save, and ensure the file's existence.
     */
    private static void ensureFileExists() {
        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile(); // The "data" folder

            // Create the "data" folder if it doesn't exist
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            // Create the "taskList.txt" file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while ensuring the file exists...\n" + e.getMessage());
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
        ArrayList<Task> taskList = new ArrayList<>();
        ensureFileExists();
        //DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String currLine;

            while ((currLine = reader.readLine()) != null) {
                String[] taskParts = currLine.split(" \\| ");

                if (taskParts.length < 3) {
                    System.out.println("Warning: Skipping corrupted entry - " + currLine);
                    continue; // Skip this line
                }

                String status = taskParts[0].trim();
                boolean isDone = status.equals("X"); // "X" means done
                String typeAndName = taskParts[1].trim();
                if (!typeAndName.contains(":")) {
                    System.out.println("Warning: Skipping malformed task entry - " + currLine);
                    continue;
                }
                String[] typeSplit = typeAndName.split(":");
                if (typeSplit.length < 2) {
                    System.out.println("Warning: Skipping invalid task format - " + currLine);
                    continue;
                }
                String type = typeSplit[0].trim();
                String description = typeSplit[1].trim();
                String timeInfo = taskParts[2].trim(); // Extract any time info

                Task currTask;
                if (type.equals("T")) {
                    currTask = new ToDo(description, isDone);
                } else if (type.equals("D")) {
                    try {
                        if (!timeInfo.startsWith("by: ")) {
                            throw new PiggyException("Invalid time format - " + currLine);
                        }
                        String dateStr = timeInfo.substring(4).trim();
                        LocalDateTime by = LocalDateTime.parse(dateStr, INPUT_FORMATTER);
                        currTask = new Deadline(description, by, isDone);
                    } catch (Exception e) {
                        System.out.println("Warning: Skipping invalid deadline format - " + currLine);
                        continue;
                    }
                } else if (type.equals("E")) { // Event
                    try {
                        if (!timeInfo.startsWith("from: ") || !timeInfo.contains(", to: ")) {
                            throw new Exception();
                        }
                        String[] eventParts = timeInfo.split(", to: ");
                        LocalDateTime from = LocalDateTime.parse(eventParts[0].substring(6).trim(), INPUT_FORMATTER);
                        LocalDateTime to = LocalDateTime.parse(eventParts[1].trim(), INPUT_FORMATTER);
                        currTask = new Event(description, from, to, isDone);
                    } catch (Exception e) {
                        System.out.println("Warning: Skipping invalid event format - " + currLine);
                        continue;
                    }
                } else {
                    throw new PiggyException("Unknown task type in file: " + type);
                }
                taskList.add(currTask);
            }
        } catch (Exception e) {
            throw new PiggyException("An error occurred while loading tasks: " + e.getMessage());
        }
        return taskList;
    }

    /**
     * Updates the storage file with the current list of tasks.
     * Creates a backup before writing and restores it if an error occurs.
     *
     * @param taskList The list of tasks to be saved.
     * @throws PiggyException If an error occurs while updating the file.
     */
    public static void updateList(ArrayList<Task> taskList) throws PiggyException {
        ensureFileExists(); // Make sure the file exists before writing
        File originalFile = new File(FILE_PATH);
        File backupFile = new File(FILE_PATH + ".bak");

        // Backup previous valid file
        if (originalFile.exists()) {
            originalFile.renameTo(backupFile);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : taskList) {
                String status = task.status();
                String type;
                String timeInfo = "--"; // Default for ToDo tasks

                if (task instanceof ToDo) {
                    type = "T";
                } else if (task instanceof Deadline) {
                    type = "D";
                    LocalDateTime dueDate = ((Deadline) task).getDueDate();
                    timeInfo = "by: " + dueDate.format(OUTPUT_FORMATTER);
                } else {
                    type = "E";
                    LocalDateTime startTime = ((Event) task).getStart();
                    LocalDateTime endTime = ((Event) task).getEnd();
                    timeInfo = "from: " + startTime.format(OUTPUT_FORMATTER) + ", to: " + endTime.format(OUTPUT_FORMATTER);
                }

                writer.write(status + " | " + type + ": " + task.getName() + " | " + timeInfo);
                writer.newLine();
            }
        } catch (Exception e) {
            // Restore from backup if writing fails
            if (backupFile.exists()) {
                backupFile.renameTo(originalFile);
            }
            throw new PiggyException("An error occurred while updating the taskList: " + e.getMessage());
        } finally {
            // Delete backup file after successful save
            if (backupFile.exists()) {
                backupFile.delete();
            }
        }
    }
}
