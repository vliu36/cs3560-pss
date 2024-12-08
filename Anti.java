import Interfaces.AntiInterface;
import Interfaces.TaskInterface;

public class Anti extends Task implements AntiInterface {
    TaskInterface blockedTask;

    public Anti(String name, String type, double start, double duration, int date, TaskInterface blocked) {
        super(name, type, start, duration, date);
        this.blockedTask = blocked;
    }
    public TaskInterface getBlockedTask() {
        return blockedTask;
    }
}

