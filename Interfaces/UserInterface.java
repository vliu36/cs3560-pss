package Interfaces;

public interface UserInterface {
    public String getName();
    public void setName(String name);
    public boolean validatePassword(String input);
    public ScheduleInterface getSchedule();
    public void setSchedule(ScheduleInterface schedule);
}
