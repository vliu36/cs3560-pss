package Interfaces;

public interface AntiInterface extends TaskInterface{
    //getters and setters for name
    public String getName();
    public void setName();
    
    //method checks for a recurring task that the anti task can override
    //if there is no recurring task that matches the start time and duration the anti task will not be created
    public void overrideRecurring();

}
