import Interfaces.AntiInterface;
import Interfaces.ScheduleInterface;
import Interfaces.TaskInterface;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule implements ScheduleInterface {

    private List<TaskInterface> tasks;

    public Schedule() {
        tasks = new ArrayList<>();
    }

    @Override
    public boolean addTask(String name, String type, double startTime, double duration, int startDate) {
        TaskInterface newTask;

        if (type.equalsIgnoreCase("cancellation")) {
            // Anti-task
            newTask = new Anti(name, type, startTime, duration, startDate, null);
        } else {
            // Transient task
            newTask = new Transient(name, type, startTime, duration, startDate);
        }

        if (checkOverlap(newTask)) {
            System.out.println("Error: Overlapping task. Cannot add.");
            return false;
        }

        tasks.add(newTask);
        return true;
    }

    @Override
    public boolean addTask(String name, String type, double startTime, double duration, int startDate, int endDate, int frequency) {
        if (!type.equalsIgnoreCase("recurring")) {
            System.out.println("Error: Invalid task type for recurring tasks.");
            return false;
        }

        // Create a Recurring task object
        Recurring newTask = new Recurring(name, type, startTime, duration, startDate, endDate, frequency);

        // Generate occurrences temporarily just for overlap checking
        List<TaskInterface> occurrences = newTask.getOccurrances();

        // Check each occurrence for overlap
        for (TaskInterface occurrence : occurrences) {
            if (checkOverlap(occurrence)) {
                System.out.println("Error: Recurring task occurrence overlaps with existing tasks.");
                return false; // Abort if any occurrence overlaps
            }
        }

        // If no overlaps, add only the Recurring object itself (not the occurrences)
        tasks.add(newTask);
        return true;
    }

    @Override
    public boolean removeTask(String name) {
        for (TaskInterface task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
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
            // Skip Anti-tasks during overlap checks
            if (task instanceof AntiInterface) continue;

            // Check date overlap
            if (Math.max(newTask.getStartDate(), task.getStartDate()) <= Math.min(newTask.getEndDate(), task.getEndDate())) {
                // Check time overlap
                if (Math.max(newTask.getStartTime(), task.getStartTime()) < Math.min(newTask.getEndTime(), task.getEndTime())) {
                    return true; // Overlapping times
                }
            }
        }
        return false; // No overlap
    }

    @Override
    public void viewTask(String name) {
        for (TaskInterface task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                task.printTask();
                return;
            }
        }
        System.out.println("Task not found.");
    }

    @Override
    public boolean editTask(String name, double newStartTime, double newDuration, int newStartDate) {
        for (TaskInterface task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                tasks.remove(task);

                task.setStartTime(newStartTime);
                task.setDuration(newDuration);
                task.setStartDate(newStartDate);

                if (checkOverlap(task)) {
                    System.out.println("Error: Overlapping task. Cannot edit.");
                    tasks.add(task); // revert add
                    return false;
                }

                tasks.add(task);
                System.out.println("Task edited successfully: " + name);
                return true;
            }
        }

        System.out.println("Error: Task not found.");
        return false;
    }

    @Override
    public boolean exportSchedule(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean importSchedule(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void printSchedule(String timeFrame) {
        // This method is not strictly needed if we rely on viewTasksForTimeframe
        // But if you use it, similar logic to viewTasksForTimeframe should apply.
        // For demonstration, let's just call viewTasksForTimeframe with a default date.
        int today = getCurrentDate();
        int timeframe = 4; // all by default
        if (timeFrame.equalsIgnoreCase("day")) timeframe = 1;
        else if (timeFrame.equalsIgnoreCase("week")) timeframe = 2;
        else if (timeFrame.equalsIgnoreCase("month")) timeframe = 3;
        viewTasksForTimeframe(timeframe, today);
    }

    private int getCurrentDate() {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        return currentDate.getYear() * 10000 + currentDate.getMonthValue() * 100 + currentDate.getDayOfMonth();
    }

    @Override
    public void viewTasksForTimeframe(int timeframe, int startDate) {
        // Create a combined list of tasks to display
        List<TaskInterface> displayList = new ArrayList<>();

        // 1. Expand recurring tasks into occurrences
        for (TaskInterface task : tasks) {
            if (task instanceof Recurring) {
                Recurring r = (Recurring) task;
                // Add occurrences instead of the recurring task
                displayList.addAll(r.getOccurrances());
            } else {
                // Transient or Anti tasks as is
                displayList.add(task);
            }
        }

        // 2. Apply anti-tasks: remove canceled occurrences and the anti-task itself
        List<TaskInterface> finalList = new ArrayList<>(displayList);
        for (TaskInterface t : displayList) {
            if (t instanceof Anti) {
                Anti a = (Anti) t;
                TaskInterface blockedOccurrence = findMatchingOccurrence(finalList, a);
                if (blockedOccurrence != null) {
                    finalList.remove(a);
                    finalList.remove(blockedOccurrence);
                } else {
                    // If no matching occurrence found, decide if you want to remove anti-tasks or not.
                    // Currently, if anti can't find a match, it stays.
                    // If you don't want anti-tasks without a match:
                    // finalList.remove(a);
                }
            }
        }

        // 3. Sort finalList by date/time
        finalList.sort((t1, t2) -> {
            int dateComp = Integer.compare(t1.getStartDate(), t2.getStartDate());
            if (dateComp != 0) return dateComp;
            return Double.compare(t1.getStartTime(), t2.getStartTime());
        });

        System.out.println("\n========= SCHEDULE =========");
        for (TaskInterface task : finalList) {
            int taskStartDate = task.getStartDate();
            if ((timeframe == 1 && taskStartDate == startDate) || 
                (timeframe == 2 && isWithinWeek(startDate, taskStartDate)) ||
                (timeframe == 3 && isWithinMonth(startDate, taskStartDate)) ||
                (timeframe == 4)) {
                task.printTask();
            }
        }
        System.out.println("============================");
    }

    private TaskInterface findMatchingOccurrence(List<TaskInterface> list, Anti anti) {
        for (TaskInterface occ : list) {
            // Not a recurring or anti-task. Occurrences are transient-like, so no need to exclude transient
            if (!(occ instanceof Recurring) && !(occ instanceof Anti)) {
                if (occ.getName().equalsIgnoreCase(anti.getName()) &&
                    occ.getStartDate() == anti.getStartDate() &&
                    Math.abs(occ.getStartTime() - anti.getStartTime()) < 0.0001) {
                    return occ;
                }
            }
        }
        return null;
    }

    private boolean isWithinWeek(int startDate, int taskStartDate) {
        java.time.LocalDate start = parseDateToLocalDate(startDate);
        java.time.LocalDate taskStart = parseDateToLocalDate(taskStartDate);
        java.time.LocalDate weekEnd = start.plusDays(6);
        return (!taskStart.isBefore(start)) && (!taskStart.isAfter(weekEnd));
    }

    private boolean isWithinMonth(int startDate, int taskStartDate) {
        java.time.LocalDate start = parseDateToLocalDate(startDate);
        java.time.LocalDate taskStart = parseDateToLocalDate(taskStartDate);
        return start.getYear() == taskStart.getYear() && start.getMonth() == taskStart.getMonth();
    }

    private java.time.LocalDate parseDateToLocalDate(int date) {
        int year = date / 10000;
        int month = (date % 10000) / 100;
        int day = date % 100;
        return java.time.LocalDate.of(year, month, day);
    }

    @Override
    public TaskInterface findTaskByName(String taskName) {
        for (TaskInterface task : tasks) {
            if (task.getName().equalsIgnoreCase(taskName)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void writeToFile(String filePath) {
        // Sort tasks by date/time
        tasks.sort((t1, t2) -> {
            int comp = Integer.compare(t1.getStartDate(), t2.getStartDate());
            return comp != 0 ? comp : Double.compare(t1.getStartTime(), t2.getStartTime());
        });

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("[\n");
            boolean first = true;

            for (TaskInterface task : tasks) {
                if (!first) writer.write(",\n");
                first = false;

                if (task instanceof Recurring) {
                    // Write recurring in the top-level format
                    Recurring r = (Recurring) task;
                    writer.write("  {\n");
                    writer.write("    \"Name\" : \"" + r.getName() + "\",\n");
                    writer.write("    \"Type\" : \"" + r.getType() + "\",\n");
                    writer.write("    \"StartDate\" : " + r.getStartDate() + ",\n");
                    writer.write("    \"StartTime\" : " + r.getStartTime() + ",\n");
                    writer.write("    \"Duration\" : " + r.getDuration() + ",\n");
                    writer.write("    \"EndDate\" : " + r.getRecurringEndDate() + ",\n");
                    writer.write("    \"Frequency\" : " + r.getFrequency() + "\n");
                    writer.write("  }");
                } else if (task instanceof AntiInterface) {
                    // Anti-task
                    AntiInterface a = (AntiInterface) task;
                    writer.write("  {\n");
                    writer.write("    \"Name\" : \"" + a.getName() + "\",\n");
                    writer.write("    \"Type\" : \"Cancellation\",\n");
                    writer.write("    \"Date\" : " + a.getStartDate() + ",\n");
                    writer.write("    \"StartTime\" : " + a.getStartTime() + ",\n");
                    writer.write("    \"Duration\" : " + a.getDuration() + "\n");
                    writer.write("  }");
                } else {
                    // Transient
                    writer.write("  {\n");
                    writer.write("    \"Name\" : \"" + task.getName() + "\",\n");
                    writer.write("    \"Type\" : \"" + task.getType() + "\",\n");
                    writer.write("    \"Date\" : " + task.getStartDate() + ",\n");
                    writer.write("    \"StartTime\" : " + task.getStartTime() + ",\n");
                    writer.write("    \"Duration\" : " + task.getDuration() + "\n");
                    writer.write("  }");
                }
            }

            writer.write("\n]\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public void readFromFile(String filePath) {
        tasks.clear();

        try {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line.trim());
                }
            }

            String content = sb.toString();
            if (content.startsWith("[") && content.endsWith("]")) {
                content = content.substring(1, content.length()-1).trim();
            } else {
                System.out.println("Invalid JSON format. Expected array.");
                return;
            }

            List<String> objects = splitIntoJsonObjects(content);
            for (String obj : objects) {
                Map<String, String> fields = parseJsonObject(obj);

                String name = fields.get("Name");
                String type = fields.get("Type");

                if (type == null) {
                    System.out.println("Invalid object: no type found");
                    continue;
                }

                if (type.equalsIgnoreCase("Cancellation")) {
                    // Anti-task
                    int date = Integer.parseInt(fields.get("Date"));
                    double st = Double.parseDouble(fields.get("StartTime"));
                    double du = Double.parseDouble(fields.get("Duration"));
                    tasks.add(new Anti(name, type, st, du, date, null));
                } else if (fields.containsKey("Frequency") && fields.containsKey("EndDate") && fields.containsKey("StartDate")) {
                    // Recurring
                    int sd = Integer.parseInt(fields.get("StartDate"));
                    double st = Double.parseDouble(fields.get("StartTime"));
                    double du = Double.parseDouble(fields.get("Duration"));
                    int ed = Integer.parseInt(fields.get("EndDate"));
                    int freq = Integer.parseInt(fields.get("Frequency"));
                    tasks.add(new Recurring(name, type, st, du, sd, ed, freq));
                } else if (fields.containsKey("Date")) {
                    // Transient
                    int d = Integer.parseInt(fields.get("Date"));
                    double st = Double.parseDouble(fields.get("StartTime"));
                    double du = Double.parseDouble(fields.get("Duration"));
                    tasks.add(new Transient(name, type, st, du, d));
                } else {
                    System.out.println("Unknown task format in JSON.");
                }
            }

            System.out.println("Tasks loaded from " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    private List<String> splitIntoJsonObjects(String content) {
        List<String> objects = new ArrayList<>();
        int braceCount = 0;
        int startIndex = -1;
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '{') {
                if (braceCount == 0) {
                    startIndex = i;
                }
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    // End of object
                    objects.add(content.substring(startIndex, i+1));
                }
            }
        }
        return objects;
    }

    private Map<String, String> parseJsonObject(String obj) {
        Map<String, String> map = new HashMap<>();
        obj = obj.trim();
        if (obj.startsWith("{") && obj.endsWith("}")) {
            obj = obj.substring(1, obj.length()-1).trim();
        }

        String[] lines = obj.split(",");
        for (String line : lines) {
            line = line.trim();
            if (line.endsWith(",")) line = line.substring(0, line.length()-1).trim();
            int colonIndex = line.indexOf(':');
            if (colonIndex == -1) continue;
            String key = line.substring(0, colonIndex).trim();
            String value = line.substring(colonIndex+1).trim();

            key = stripQuotes(key);
            value = stripQuotes(value);

            map.put(key, value);
        }

        return map;
    }

    private String stripQuotes(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length()-1);
        }
        return s;
    }
}
