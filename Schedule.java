import Interfaces.ScheduleInterface; // Import the interface.
import java.io.*;
import Interfaces.TaskInterface;
import java.util.ArrayList;
import java.util.List;

public class Schedule implements ScheduleInterface {

    // Attribute: List to store tasks.
    private List<TaskInterface> tasks;

    // Constructor: Initialize the list of tasks.
    public Schedule() {
        tasks = new ArrayList<>();
    }

    @Override
    public boolean addTask(String name, String type, double startTime, double duration, int startDate) {
        TaskInterface newTask = null;
        type = type.toLowerCase();
        if (type.equals("cancellation")) {
            newTask = new Anti(name, type, startTime, duration, startDate);
        }
        else if (type.equals("visit") || type.equals("shopping") || type.equals("appointment")) {
            newTask = new Transient(name, type, startTime, duration, startDate);
        }
        else if (type.equals("class") || type.equals("study") || type.equals("sleep") ||
                type.equals("exercise") || type.equals("work") || type.equals("meal")) {
                    newTask = new Recurring(name, type, startTime, duration, startDate, -1);
        }
        else {
            return false;
        }

        // Check overlap
        if(checkOverlap(newTask)) {
            System.out.println("Error: overlap with existing task");
            return false; // Task not added
        }

        tasks.add(newTask); // Add task to the list
        System.out.println("Task added successfully: " + name);
        return true;
    }

    @Override
    public boolean removeTask(String name) {
        for (TaskInterface task : tasks) {
            if(task.getName().equals(name)) {
                tasks.remove(task);
                System.out.println("Task successfully removed.");
                return true;
            }
        }

        System.out.println("Error: Task not found.");
        return false; // Task not found
    }

    @Override
    public boolean checkOverlap(TaskInterface newTask) {
        for (TaskInterface task : tasks) {
            if (task instanceof Anti) {
                continue;
            }
            if (task.getName().equals(newTask.getName())) {
                return true;
            }
            if (Math.max(newTask.getStartDate(), task.getStartDate()) < Math.min(newTask.getEndDate(), task.getEndDate())) {
                return true;
            }
            else if (newTask.getStartDate() == task.getStartDate()) {
                if (Math.max(newTask.getStartTime(), task.getStartTime()) < Math.min(newTask.getEndTime(), task.getEndTime())) {
                    return true;
                }
            }

        }
        return false; // No Overlap
    }

    @Override
    public void viewTask(String name) {
        for (TaskInterface task : tasks) {
            if (task.getName().equals(name)) {
                task.printTask();
                break;
            }
        }
    }

    @Override
    public boolean editTask(String name, double newStartTime, double newDuration, int newStartDate) {
        for (TaskInterface task : tasks) {
            if (task.getName().equals(name)) {
                task.setStartTime(newStartTime);
                task.setDuration(newDuration);
                task.setStartDate(newStartDate);
                System.out.println("Task edited successfully: " + name);
                return true;
            }
        }

        System.out.println("Error: Task not found.");
        return false; // Task not found
    }

    public boolean exportSchedule(String fileName) {
        return false;
    //     Gson gson = new Gson();
    //     try (Writer writer = new FileWriter(fileName)) {
    //         gson.toJson(tasks, writer);
    //         System.out.println("Schedule exported successfully to " + fileName);
    //         return true;
    //     } catch (IOException e) {
    //         System.out.println("Error exporting schedule: " + e.getMessage());
    //         return false;
    //     }
    }

    public boolean importSchedule(String fileName) {
        return false;
    //     Gson gson = new Gson();
    //     try (Reader reader = new FileReader(fileName)) {
    //         tasks.clear(); // Clear existing tasks before importing
    //         Task[] importedTasks = gson.fromJson(reader, Task[].class);
    //         for (Task task : importedTasks) {
    //             tasks.add(task);
    //         }
    //         System.out.println("Schedule imported successfully from " + fileName);
    //         return true;
    //     } catch (IOException e) {
    //         System.out.println("Error importing schedule: " + e.getMessage());
    //         return false;
    //     }
    }

    @Override
    public void printSchedule(String timeFrame) {
        for (TaskInterface task : tasks) {
            // Filtering tasks based on the time frame can be implemented later
            task.printTask();
        }
    }
}
