package Interfaces;

public class ScheduleInterface {

    boolean addTask(String name, String type, String startTime, double duration, String startDate);

    boolean removeTask(String name);

    boolean checkOverlap(String time, double duration, String date);

    String viewTask(String name);

    boolean editTask(String name, String newStartTime, double newDuration, String newStartDate);

    boolean exportSchedule(String fileName);

    boolean importSchedule(String fileName);

    String printSchedule(String timeFrame);
    
}
