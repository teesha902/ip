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

//FORMAT: [status] | [TYPE: name] | [deadline OR start-end OR "-"]
public class Storage {
    private static final String FILE_PATH = "data/taskList.txt";

    //check if file/list is empty
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

    //load_file method: reconstruct list everytime program starts
    public static ArrayList<Task> loadList() throws PiggyException {
        ArrayList<Task> taskList = new ArrayList<>();
        ensureFileExists();

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
                //String type = typeAndName.split(":")[0].trim(); // Extract type - 1st char only
                if (typeSplit.length < 2) {
                    System.out.println("Warning: Skipping invalid task format - " + currLine);
                    continue;
                }
                String type = typeSplit[0].trim();
                String description = typeSplit[1].trim();
                //String description = typeAndName.split(":")[1].trim();
                String timeInfo = taskParts[2].trim(); // Extract any time info

                Task currTask;
                if (type.equals("T")) {
                    currTask = new ToDo(description, isDone);
                } else if (type.equals("D")) {
                    if (!timeInfo.startsWith("by: ")) {
                        System.out.println("Warning: Skipping invalid deadline format - " + currLine);
                        continue;
                    }
                    String by = timeInfo.substring(4).trim(); // Extract the deadline
                    currTask = new Deadline(description, by, isDone);
                } else if (type.equals("E")) { // Event
                    if (!timeInfo.startsWith("from: ") || !timeInfo.contains(", to: ")) {
                        System.out.println("Warning: Skipping invalid event format - " + currLine);
                        continue;
                    }
                    String[] eventParts = timeInfo.split(", to: ");
                    String from = eventParts[0].substring(6).trim(); // Extract start time
                    String to = eventParts[1].trim(); // Extract end time
                    currTask = new Event(description, from, to, isDone);
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

    //updateList: update list as items are added/deleted/marked/unmarked/
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
                if (task instanceof ToDo) {
                    type = "T";
                } else if (task instanceof Deadline) {
                    type = "D";
                } else {
                    type = "E";
                }
                String name = task.toString().split("] ")[1]; // Extract name from task's toString()
                String timeInfo = "--"; // Default for ToDo tasks

                if (task instanceof Deadline) {
                    timeInfo = "by: " + ((Deadline) task).getDue(); // Deadline time info
                } else if (task instanceof Event) {
                    timeInfo = "from: " + ((Event) task).getStart() + ", to: " + ((Event) task).getEnd(); // Event time info
                }

                // Write formatted task to output file
                writer.write(status + " | " + type + ": " + name + " | " + timeInfo);
                writer.newLine(); // Move to next line
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
