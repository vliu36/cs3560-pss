import Interfaces.AntiInterface;

public class Anti extends Task {

    public Anti(String name, String type, double start, double duration, int date) {
        if (overrideRecurring())
        {
            super(name, type, start, duration, date);
        }
        else{
            System.out.println("error, no recurring task to overwrite")
        }
    }
    
    public boolean overrideRecurring() {
        //check for the recurring task in order for the anti task to be created
    }
//i know this probably doesnt work but im just showing the basic idea i had
}

