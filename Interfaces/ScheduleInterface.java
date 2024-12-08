package Interfaces;

public interface ScheduleInterface {

    boolean addTask(String name, String type, double startTime, double duration, int startDate);

    boolean removeTask(String name);

    boolean checkOverlap(TaskInterface task);

    void viewTask(String name);

    boolean editTask(String name, double newStartTime, double newDuration, int newStartDate);

    boolean exportSchedule(String fileName);

    boolean importSchedule(String fileName);

    void printSchedule(String timeFrame);

    void viewTasksForTimeframe(int timeframe, int startDate);
    
}
