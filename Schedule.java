import Interfaces.ScheduleInterface; // Import the interface.
import java.io.*;
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
    public boolean addTask(String name, String type, String startTime, double duration, String startDate) {
        Task newTask = new Task(name, type, startTime, duration, startDate);

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                writer.write(task.getName() + "," + task.getType() + "," + task.getStartTime() + "," + task.getDuration() + "," + task.getStartDate());
                writer.newLine();
            }
            System.out.println("Schedule exported successfully to " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("Error exporting schedule: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean importSchedule(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            task.clear(); //clear existing tasks before importing
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskDetails = line.split(",");
                if (taskDetails.length == 5) {
                    String name = taskDetails[0];
                    String type = taskDetails[1];
                    String startTime = taskDetails[2];
                    double duration = Double.parseDouble(taskDetails[3]);
                    String startDate = taskDetails[4];
                    Task newTask = new Task(name, type, startTime, duration, startDate);
                    tasks.add(newTask);
                }
            }
            System.out.println("Schedule imported successfully from " + fileName);
            return true;
        } catch (IOException e) {
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
