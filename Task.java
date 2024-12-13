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

    public Type getType() {
        return type;
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
            return startDate++;
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
}
