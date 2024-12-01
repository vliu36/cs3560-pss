import Interfaces.TaskInterface;

// Superclass for Transient, Anti, Recurring Tasks
// Do not create pure objects of this base class, instead create instances of its subclasses and call this constructor in its constructor
public class Task implements TaskInterface {
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
        System.out.println(startDate + " " + formatTime(startTime) + " -- " + getEndDate() + " " + formatTime(getEndTime()) + " " + name + " " + type);
    }
            
    private String formatTime(double time) {
        String timeString = "";
        boolean isAm = true;
        if (time > 11.75) {
            time -= 12;
            isAm = false;
        }
        timeString += (int)time + ":";
        timeString += (int)((time - (int)time) * 60);
        if (timeString.length() < 4) {
            timeString += "0";
        }
        if (isAm) {
            timeString += "am";
        }
        else {
            timeString += "pm";
        }
        return timeString;
    }
}
