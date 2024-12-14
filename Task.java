import Interfaces.TaskInterface;

// Superclass for Transient, Anti, Recurring Tasks
// Do not create pure instances of this base class, instead create instances of its subclasses and call this constructor 
// in the subclass constructor
public class Task implements TaskInterface {
    public enum Type {
        APPOINTMENT,
        CANCELLATION,
        RECURRING,
        VISIT,
        SHOPPING,
        CLASS,
        STUDY,
        SLEEP,
        EXERCISE,
        WORK,
        MEAL;
    }
    
    protected String name;
    protected Type type;
    protected double startTime;
    protected double duration;
    protected int startDate;

    protected Task(String name, String type, double start, double duration, int date) {
        this.name = name;
        this.type = Type.valueOf(type.toUpperCase());
        this.startTime = start;
        this.duration = duration;
        this.startDate = date;
    }
    protected Task(String name, Type type, double start, double duration, int date) {
        this.name = name;
        this.type = type;
        this.startTime = start;
        this.duration = duration;
        this.startDate = date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
    return type.toString();
    }
    
    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double time) {
        this.startTime = time;
    }


    public double getDuration() {
        return duration;
    }


    public void setDuration(double time) {
        this.duration = time;
    }


    public int getStartDate() {
        return startDate;
    }


    public void setStartDate(int date) {
        this.startDate = date;
    }

    public int getEndDate() {
        if (startTime + duration > 23.75) {
            return startDate + 1;
        }
        else {
            return startDate;
        }
    }

    public double getEndTime() {
        if (startTime + duration > 23.75) {
            return (startTime + duration) - 24;
        }
        else {
            return startTime + duration;
        }
    }
    public void printTask() {
        String startDateFormatted = String.format("%04d/%02d/%02d", startDate / 10000, (startDate % 10000) / 100, startDate % 100);
        System.out.printf("%s %s -- %s %s %s %s%n",
            startDateFormatted,
            formatTime(startTime),
            getFormattedEndDate(),
            formatTime(getEndTime()),
            name.toUpperCase(),
            type.toString().toUpperCase()
        );
    }

    public String getFormattedEndDate() {
        int year = getEndDate() / 10000;
        int month = (getEndDate() % 10000) / 100;
        int day = getEndDate() % 100;
        return String.format("%04d/%02d/%02d", year, month, day);
    }

    private String formatTime(double time) {
        int hours = (int) time;
        int minutes = (int) ((time - hours) * 60);
        boolean isAm = hours < 12;

        if (hours == 0) {
            hours = 12; // Midnight
        } else if (hours >= 12) {
            if (hours > 12) {
                hours -= 12; // Convert to 12-hour format
            }
            isAm = false;
        }

        return String.format("%02d:%02d%s", hours, minutes, isAm ? "am" : "pm");
    }
    public static Task parseTask(String line) {
        try {
            String[] parts = line.split(";"); // Split the line into components
            String name = parts[0];
            String type = parts[1];
            double startTime = Double.parseDouble(parts[2]);
            double duration = Double.parseDouble(parts[3]);
            int startDate = Integer.parseInt(parts[4]);

            // Create and return a new Task object
            return new Task(name, type, startTime, duration, startDate);
        } catch (Exception e) {
            System.out.println("Error parsing task: " + e.getMessage());
            return null; // Return null if parsing fails
        }
    }
}
