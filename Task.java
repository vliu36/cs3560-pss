import Interfaces.TaskInterface;
public class Task implements TaskInterface {
    protected String name;
    protected Type type;
    protected double startTime;
    protected double duration;
    protected int startDate;

    @Override
    public TaskInterface newTask(String name, Type type, double start, double duration, int date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newTask'");
    }
    @Override
    public double getStartTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStartTime'");
    }

    @Override
    public void setStartTime(double time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStartTime'");
    }

    @Override
    public double getDuration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDuration'");
    }

    @Override
    public void setDuration(double time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDuration'");
    }

    @Override
    public int getStartDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStartDate'");
    }

    @Override
    public void setStartDate(int date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStartDate'");
    }
    @Override
    public int getEndDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEndDate'");
    }

    @Override
    public double getEndTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEndTime'");
    }

    @Override
    public void printTask() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printTask'");
    }
    
}
