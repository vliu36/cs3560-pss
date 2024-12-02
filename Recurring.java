import Interfaces.RecurringInterface;

public class Recurring extends Task {

    private int frequency;

    public Recurring(String name, String type, double start, double duration, int date, int frequency) {
        super(name, type, start, duration, date);
        this.frequency = frequency;
    } 
    
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
