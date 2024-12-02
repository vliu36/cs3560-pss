import Interfaces.ScheduleInterface; // Import the interface.
import java.io.*;
import java.time.LocalDate; 
import java.time.LocalTime; 
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

public class Schedule implements ScheduleInterface {

    // Attribute: List to store tasks.
    private List<Task> tasks;

    // Constructor: Initialize the list of tasks.
    public Schedule() {
        tasks = new ArrayList<>();
    }

    @Override
    public boolean addTask(String name, String type, String startTime, double duration, String startDate) {
        Task newTask = new Task(name, type, startTime, duration, startDate);

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
    public boolean checkOverlap(Task newTask) {
        LocalTime newTaskStartTime = LocalTime.parse(newTask.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime newTaskEndTime = newTaskStartTime.plusMinutes((long)(newTask.getDuration() * 60));
        LocalDate newTaskDate = LocalDate.parse(newTask.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (Task task : tasks) {
            LocalTime existingTaskStartTime = LocalTime.parse(task.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime existingTaskEndTime = existingTaskStartTime.plusMinutes((long)(task.getDuration() * 60));
            LocalDate existingTaskDate = LocalDate.parse(task.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (newTaskDate.equals(existingTaskDate)) {
                if (newTaskStartTime.isBefore(existingTaskEndTime) && newTaskEndTime.isAfter(existingTaskStartTime)) {
                    return true; // Overlap found
                }
            }
        }
        
        return false; // No Overlap
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
    public boolean editTask(String name, String newStartTime, double newDuration, String newStartDate) {
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
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(tasks, writer);
            System.out.println("Schedule exported successfully to " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("Error exporting schedule: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean importSchedule(String fileName) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(fileName)) {
            tasks.clear(); // Clear existing tasks before importing
            Task[] importedTasks = gson.fromJson(reader, Task[].class);
            for (Task task : importedTasks) {
                tasks.add(task);
            }
            System.out.println("Schedule imported successfully from " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("Error importing schedule: " + e.getMessage());
            return false;
        }
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
