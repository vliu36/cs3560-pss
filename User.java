import Interfaces.ScheduleInterface;
import Interfaces.UserInterface;

public class User implements UserInterface {
    private String name;
    private String password;
    private Schedule schedule;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.schedule = new Schedule();
    }
    public User(String name, String password, Schedule sch) {
        this.name = name;
        this.password = password;
        this.schedule = sch;
    }

    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }

    @Override
    public boolean validatePassword(String input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validatePassword'");
    }

    @Override
    public ScheduleInterface getSchedule() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSchedule'");
    }

    @Override
    public void setSchedule(ScheduleInterface schedule) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSchedule'");
    }
    
}
