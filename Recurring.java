import java.util.ArrayList;
import Interfaces.RecurringInterface;
import Interfaces.TaskInterface;

public class Recurring extends Task implements RecurringInterface {
    private int frequency;
    private int recurringEndDate;
    private ArrayList<TaskInterface> tasks;

    public Recurring(String name, String type, double start, double duration, int date, int recurringEndDate, int frequency) {
        super(name, type, start, duration, date);
        this.frequency = frequency;
        this.recurringEndDate = recurringEndDate;
        this.tasks = new ArrayList<>(); // Initialize the tasks list
        generateOccurrances();
    }
    
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getRecurringEndDate() {
        return recurringEndDate;
    }

    public void setRecurringEndDate(int date) {
        recurringEndDate = date;
    }

    public ArrayList<TaskInterface> getOccurrances() {
        return tasks;
    }

    private void generateOccurrances() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
    
        int currDate = startDate;
        while (currDate <= recurringEndDate) {
            tasks.add(new Transient(name, type.toString(), startTime, duration, currDate));
    
            // Increment by frequency (in days)
            currDate += frequency;
    
            // Handle month overflow (e.g., December 31 to January 1)
            int year = currDate / 10000;
            int month = (currDate % 10000) / 100;
            int day = currDate % 100;
    
            if (day > 31) {
                day -= 31;
                month++;
            }
            if (month > 12) {
                month -= 12;
                year++;
            }
    
            currDate = (year * 10000) + (month * 100) + day;
        }
    }
    
}
