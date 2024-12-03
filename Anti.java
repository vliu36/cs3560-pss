import Interfaces.TaskInterface;

public class Anti extends Task {
    private Recurring associatedRecurringTask;

    public Anti(String name, String type, double start, double duration, int date, Recurring recurringTask) {
        super(name, type, start, duration, date);
        if (!isValidAntiTask(start, duration, date, recurringTask)) {
            throw new IllegalArgumentException("Invalid Anti task: Does not match any occurrence of the recurring task.");
        }
        this.associatedRecurringTask = recurringTask;
    }

    public Recurring getAssociatedRecurringTask() {
        return associatedRecurringTask;
    }

    public void setAssociatedRecurringTask(Recurring recurringTask) {
        if (!isValidAntiTask(this.startTime, this.duration, this.startDate, recurringTask)) {
            throw new IllegalArgumentException("Invalid Anti task: Does not match any occurrence of the recurring task.");
        }
        this.associatedRecurringTask = recurringTask;
    }

    private boolean isValidAntiTask(double start, double duration, int date, Recurring recurringTask) {
        int taskDate = recurringTask.getStartDate();
        while (taskDate <= recurringTask.getEndDate()) {
            if (taskDate == date && recurringTask.getStartTime() == start && recurringTask.getDuration() == duration) {
                return true; // Matches a valid instance
            }
            taskDate += recurringTask.getFrequency(); // Increment by frequency
        }
        return false; // No match found
    }

    @Override
    public void printTask() {
        System.out.println("Anti-Task: Cancels [" + associatedRecurringTask.getName() + "] on " + startDate);
    }
}
