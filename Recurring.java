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
        int currDate = startDate;
        while (currDate <= recurringEndDate) {
            tasks.add(new Transient(name, type, startTime, duration, currDate));
            currDate += frequency;
            if (startDate + 30 == currDate) {
                currDate -= 30;
                currDate += 100;
            }
        }
    }
}
