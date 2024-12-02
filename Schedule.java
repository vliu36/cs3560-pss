import Interfaces.ScheduleInterface; // Import the interface.
import Interfaces.TaskInterface;
import java.util.ArrayList;
import java.util.List;

public class Schedule implements ScheduleInterface {

    // Attribute: List to store tasks.
    private List<Task> tasks;

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
                    newTask = new Recurring(name, type, startTime, duration, startDate);
        }
        else {
            return false;
        }

        // Check overlap
        if(checkOverlap(newTask.getStartTime(), newTask.getDuration(), newTask.getStartDate())) {
            System.out.println("Error: overlap w/ existing class");
            return false; // Task not added
        }

        tasks.add(newTask); // Add task to the list
        System.out.println("Task added successfully: " + name);
        return true;
    }

    @Override
    public boolean removeTask(String name) {
        for (Task task : tasks) {
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
    public boolean checkOverlap(String time, double duration, String date) {
        // Implementation
        return false;
    }

    @Override
    public String viewTask(String name) {
        for (Task task : tasks) {
            if (task.getName().equals(name)) {
                return task.toString();
            }
        }

        return "Error: Task not found";
    }

    @Override
    public boolean editTask(String name, double newStartTime, double newDuration, int newStartDate) {
        for (Task task : tasks) {
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

    @Override
    public boolean exportSchedule(String fileName) {
        // Implementation
        return false;
    }

    @Override
    public boolean importSchedule(String fileName) {
        // Implementation
        return false;
    }

    @Override
    public String printSchedule(String timeFrame) {
        StringBuilder scheduleOutput = new StringBuilder("Schedule (" + timeFrame + "):\n");

        for (Task task : tasks) {
            // Filtering tasks based on the time frame can be implemented later
            scheduleOutput.append(task.toString()).append("\n");
        }

        return scheduleOutput.toString();
    }
}
