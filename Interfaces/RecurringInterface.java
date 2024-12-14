package Interfaces;

public interface RecurringInterface extends TaskInterface{

    public int getFrequency();
    public void setFrequency(int frequency);
    public int getRecurringEndDate();
    public void setRecurringEndDate(int date);
}