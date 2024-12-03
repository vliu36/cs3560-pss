import Interfaces.ScheduleInterface;
import java.util.Scanner;

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
        choice = validateInput(1, 4, scnr);
        if (choice == 1) {
          // TODO: Schedule print timeframe
        }
        else if (choice == 2) {
          // TODO: Schedule print timeframe
        }
        else if (choice == 3) {
          // TODO: Schedule print timeframe
        }
        else {
          System.out.println();
          System.out.println("========= YOUR SCHEDULE =========");
          schedule.printSchedule("");
          System.out.println("=================================");
        }

        System.out.println();
        scnr.nextLine();
        System.out.println("Return to main menu? (y/n)");
        name = scnr.nextLine().toLowerCase();
        if (!name.equals("y")) {
          break;
        }
      }
      else if (choice == 2) {
        System.out.print("\nEnter the task type: ");
        type = scnr.nextLine();
        System.out.print("\nEnter the name: ");
        name = scnr.nextLine();
        System.out.print("\nEnter the start time (0.0 - 23.75): ");
        startTime = validateInput(0, 23.75, scnr);
        System.out.print("\nEnter the duration (0.0 - 23.75): ");
        duration = validateInput(0, 23.75, scnr);
        System.out.print("\nEnter the date (YYYY/MM/DD): ");
        date = validateInput(0, 99991231, scnr);
        if (!schedule.addTask(name, type, startTime, duration, date)) {
          System.out.println("\nError: Task overlaps with another task");
        }
        //TODO: Implement functionality and make loop for invalid input

        System.out.println();
        scnr.nextLine();
        System.out.println("Return to main menu? (y/n)");
        name = scnr.nextLine().toLowerCase();
        if (!name.equals("y")) {
          break;
        }
      }
      else if (choice == 3) {
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
      }
      else if (choice == 4) {
        System.out.print("Enter task name: ");
        name = scnr.nextLine();
        System.out.println();
        if (schedule.removeTask(name)) {
          System.out.println("Task removed successfully");
        }
        else {
          System.out.println("Error: Task not found");
        }
        //TODO: Implement functionality

        System.out.println();
        scnr.nextLine();
        System.out.println("Return to main menu? (y/n)");
        name = scnr.nextLine().toLowerCase();
        if (!name.equals("y")) {
          break;
        }
      }
      else if (choice == 5) {
        System.out.print("Enter task name: ");
        name = scnr.nextLine();
        System.out.println();

        schedule.viewTask(name);
        System.out.print("\nEnter the new start time (0.0 - 23.75): ");
        startTime = scnr.nextDouble();
        System.out.print("\nEnter the new duration (0.0 - 23.75): ");
        duration = scnr.nextDouble();
        System.out.print("\nEnter the new date (YYYY/MM/DD): ");
        date = scnr.nextInt();
        if (!schedule.editTask(name, startTime, duration, date)) {
          System.out.println("\nError: Task overlaps with another task");
        }
        //TODO: Implement invalid input loop

        System.out.println();
        scnr.nextLine();
        System.out.println("Return to main menu? (y/n)");
        name = scnr.nextLine().toLowerCase();
        if (!name.equals("y")) {
          break;
        }
      }
      else if (choice == 6) {
        System.out.println("Please select a timeframe: ");
        System.out.println("1 - Export a day");
        System.out.println("2 - Export a week");
        System.out.println("3 - Export a month");
        System.out.println("4 - Export all");
        choice = validateInput(1, 4, scnr);
        //TODO: Implement functionality

        System.out.println();
        scnr.nextLine();
        System.out.println("Return to main menu? (y/n)");
        name = scnr.nextLine().toLowerCase();
        if (!name.equals("y")) {
          break;
        }
      }
      else if (choice == 7) {
        System.out.println("Enter file path: ");
        name = scnr.nextLine();
        readFile(name);
        //TODO: Implement functionality

        System.out.println();
        scnr.nextLine();
        System.out.println("Return to main menu? (y/n)");
        name = scnr.nextLine().toLowerCase();
        if (!name.equals("y")) {
          break;
        }
      }
      else if (choice == 8){
        scnr.close();
        break;
      }
    }
  }

  // Prints the main menu
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
      }
      else {
        System.out.println("Invalid input, please enter a value between " + num1 + " and " + num2);
      }
    }
  }

  private static double validateInput(double num1, double num2, Scanner source) {
    while (true) {
      double choice = source.nextDouble();
      if (choice >= num1 && choice <= num2) {
        return choice;
      }
      else {
        System.out.println("Invalid input, please enter a value between " + num1 + " and " + num2);
      }
    }
  }

  // TODO: Implement read file functionality
  private static boolean readFile(String fpath) {
    return false;
  }

  // TODO: Implement write file functionality
  private static boolean writeFile(String fpath) {

    return false;
  }
}
