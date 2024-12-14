package Interfaces;

public interface TaskInterface {

    public String getType();
    // Each Type value defines the type of Task created
    // Recurring Task: CLASS, STUDY, SLEEP, EXERCISE, WORK, MEAL
    // Transient Task: VISIT, SHOPPING, APPOINTMENT
    // Anti Task: CANCELLATION
    enum Type {
        CLASS,
        STUDY,
        SLEEP,
        EXERCISE,
        WORK,
        MEAL,
        VISIT,
        SHOPPING,
        APPOINTMENT,
        CANCELLATION
    }

    public String getName();
    public void setName(String name);
    public double getStartTime();
    public void setStartTime(double time);
    public double getDuration();
    public void setDuration(double time);
    public int getStartDate();
    public void setStartDate(int date);
    public int getEndDate();
    public double getEndTime();
    public void printTask();
    public String toJSON();
}
