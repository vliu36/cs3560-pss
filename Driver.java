import Interfaces.ScheduleInterface;
import Interfaces.TaskInterface;
import java.util.Scanner;
import java.io.File;

public class Driver {
  public static void main(String[] args) {
    int choice = 0;
    String name = null;
    String type = null;
    double startTime = 0.0;
    double duration = 0.0;
    int date = 0;
    ScheduleInterface schedule = new Schedule();
    Scanner scnr = new Scanner(System.in);

    // Loops the main menu until user exits
    while (choice != 8) {
      printMenu();
      choice = validateInput(1, 8, scnr);
      scnr.nextLine();
      if (choice == 1) {
        System.out.println("Please select a timeframe: ");
        System.out.println("1 - View a day");
        System.out.println("2 - View a week");
        System.out.println("3 - View a month");
        System.out.println("4 - View all");

        int timeframe = validateInput(1, 4, scnr);

        int startDate = -1;
        if (timeframe != 4) {
          System.out.print("\nEnter the start date (YYYY/MM/DD): ");
          String dateInput = scnr.next();
          startDate = parseDate(dateInput);
          while (startDate == -1) {
            System.out.print("Invalid date. Please re-enter (YYYY/MM/DD): ");
            dateInput = scnr.next();
            startDate = parseDate(dateInput);
          }
        }

        schedule.viewTasksForTimeframe(timeframe, startDate);

        System.out.println("\nReturn to main menu? (y/n)");
        String returnChoice = scnr.next().toLowerCase();
        if (!returnChoice.equals("y")) {
          break;
        }
      } else if (choice == 2) {
        System.out.print("\nEnter the task type (e.g., appointment, cancellation, recurring): ");
        type = scnr.nextLine();
        System.out.print("\nEnter the name: ");
        name = scnr.nextLine();

        boolean taskAdded = false; // Flag for success/failure

        if (type.equalsIgnoreCase("cancellation")) {
            System.out.print("Enter the name of the task to block: ");
            String blockedTaskName = scnr.nextLine();

            TaskInterface blockedTask = schedule.findTaskByName(blockedTaskName);
            if (blockedTask == null) {
                System.out.println("\nError: Task to block not found.");
            } else {
                taskAdded = schedule.addTask(name, type, blockedTask.getStartTime(), blockedTask.getDuration(), blockedTask.getStartDate());
            }
        } else if (type.equalsIgnoreCase("recurring")) {
            System.out.print("\nEnter the start time (24-hour format HH:MM): ");
            String startTimeInput = scnr.nextLine();
            startTime = parseTime(startTimeInput);
            System.out.print("\nEnter the duration (hours and minutes HH:MM): ");
            String durationInput = scnr.nextLine();
            duration = parseDuration(durationInput);
            System.out.print("\nEnter the start date (YYYY/MM/DD): ");
            String startDateInput = scnr.next();
            int startDate = parseDate(startDateInput);
            System.out.print("\nEnter the end date (YYYY/MM/DD): ");
            String endDateInput = scnr.next();
            int endDate = parseDate(endDateInput);
            System.out.print("\nEnter the frequency (in days): ");
            int frequency = scnr.nextInt();
            taskAdded = schedule.addTask(name, type, startTime, duration, startDate, endDate, frequency);
        } else {
            System.out.print("\nEnter the start time (24-hour format HH:MM): ");
            String startTimeInput = scnr.nextLine();
            startTime = parseTime(startTimeInput);
            System.out.print("\nEnter the duration (hours and minutes HH:MM): ");
            String durationInput = scnr.nextLine();
            duration = parseDuration(durationInput);
            System.out.print("\nEnter the date (YYYY/MM/DD): ");
            String dateInput = scnr.next();
            date = parseDate(dateInput);
            taskAdded = schedule.addTask(name, type, startTime, duration, date);
        }
        // Print the success/failure message
        if (taskAdded) {
            System.out.println("Task added successfully: " + name);
        } else {
            System.out.println("Error: Could not add task.");
        }
      } else if (choice == 3) {
        System.out.print("Enter task name: ");
        name = scnr.nextLine();
        System.out.println();
        schedule.viewTask(name);

        System.out.println();
        System.out.println("Return to main menu? (y/n)");
        name = scnr.nextLine().toLowerCase();
        if (!name.equals("y")) {
          break;
        }
      } else if (choice == 4) { // Delete a task
        System.out.print("Enter the task name to delete: ");
        name = scnr.nextLine();
        if (schedule.removeTask(name)) {
          System.out.println("Task removed successfully.");
        } else {
          System.out.println("Error: Task not found.");
        }

        System.out.println("\nReturn to main menu? (y/n)");
        String returnChoice = scnr.next().toLowerCase();
        if (!returnChoice.equals("y")) {
          break;
        }
      } else if (choice == 5) { // Edit a task
        System.out.print("Enter the task name to edit: ");
        name = scnr.nextLine();

        // View the task to edit
        schedule.viewTask(name);

        // Prompt user for new details
        System.out.print("\nEnter the new start time (24-hour format HH:MM): ");
        String startTimeInput = scnr.nextLine();
        double newStartTime = parseTime(startTimeInput);

        System.out.print("\nEnter the new duration (hours and minutes HH:MM): ");
        String durationInput = scnr.nextLine();
        double newDuration = parseDuration(durationInput);

        System.out.print("\nEnter the new start date (YYYY/MM/DD): ");
        String dateInput = scnr.nextLine();
        int newStartDate = parseDate(dateInput);

        if (schedule.editTask(name, newStartTime, newDuration, newStartDate)) {
          System.out.println("Task edited successfully.");
        } else {
          System.out.println("Error: Task not found or overlap with existing task.");
        }

        System.out.println("\nReturn to main menu? (y/n)");
        String returnChoice = scnr.next().toLowerCase();
        if (!returnChoice.equals("y")) {
          break;
        }
      } else if (choice == 6) { // Export Schedule to a File
        System.out.print("Enter file path to export: ");
        String filePath = scnr.nextLine();
        schedule.writeToFile(filePath);
        System.out.println("Schedule exported successfully to " + filePath);
      } else if (choice == 7) { // Import Schedule from a File
        System.out.print("Enter file path to import: ");
        String filePath = scnr.nextLine();
        schedule.readFromFile(filePath);
        System.out.println("Schedule imported successfully from " + filePath);
      } else if (choice == 8) {
        break;
      }
    }
    scnr.close();
  }

  private static void printMenu() {
    System.out.println("========= PERSONAL SCHEDULING SYSTEM =========");
    System.out.println("1 - View Schedule");
    System.out.println("2 - Add a Task");
    System.out.println("3 - View a Task");
    System.out.println("4 - Delete a Task");
    System.out.println("5 - Edit a Task");
    System.out.println("6 - Export Schedule to a File");
    System.out.println("7 - Import Schedule from a File");
    System.out.println("8 - Exit");
  }

  // Validates input between num1 and num2, inclusive
  private static int validateInput(int num1, int num2, Scanner source) {
    while (true) {
      int choice = source.nextInt();
      if (choice >= num1 && choice <= num2) {
        return choice;
      } else {
        System.out.println("Invalid input, please enter a value between " + num1 + " and " + num2);
      }
    }
  }

  private static double validateInput(double num1, double num2, Scanner source) {
    while (true) {
      double choice = source.nextDouble();
      if (choice >= num1 && choice <= num2) {
        return choice;
      } else {
        System.out.println("Invalid input, please enter a value between " + num1 + " and " + num2);
      }
    }
  }

  private static int parseDate(String dateInput) {
    try {
      // Validate the format YYYY/MM/DD
      String[] parts = dateInput.split("/");
      if (parts.length != 3) {
        throw new IllegalArgumentException("Invalid date format. Use YYYY/MM/DD.");
      }

      int year = Integer.parseInt(parts[0]);
      int month = Integer.parseInt(parts[1]);
      int day = Integer.parseInt(parts[2]);

      // Validate ranges
      if (month < 1 || month > 12) {
        throw new IllegalArgumentException("Invalid month: " + month);
      }
      if (day < 1 || day > 31) {
        throw new IllegalArgumentException("Invalid day: " + day);
      }

      // Convert the date to an integer format for storage
      return (year * 10000) + (month * 100) + day;
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return -1; // Invalid date
    }
  }

  private static double parseTime(String timeInput) {
    try {
      String[] parts = timeInput.split(":");
      if (parts.length != 2) {
        throw new IllegalArgumentException("Invalid time format. Use HH:MM.");
      }
      int hours = Integer.parseInt(parts[0]);
      int minutes = Integer.parseInt(parts[1]);

      if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
        throw new IllegalArgumentException("Time out of range.");
      }

      return hours + (minutes / 60.0);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return -1; // Invalid time
    }
  }

  private static double parseDuration(String durationInput) {
    try {
      String[] parts = durationInput.split(":");
      if (parts.length != 2) {
        throw new IllegalArgumentException("Invalid duration format. Use HH:MM.");
      }
      int hours = Integer.parseInt(parts[0]);
      int minutes = Integer.parseInt(parts[1]);

      if (hours < 0 || minutes < 0 || minutes > 59) {
        throw new IllegalArgumentException("Duration out of range.");
      }

      return hours + (minutes / 60.0);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return -1; // Invalid duration
    }
  }
}

