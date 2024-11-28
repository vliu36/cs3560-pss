public class Transient extends Task {
    
    public Transient(String name, Type type, double start, double duration, int date) {
        this.name = name;
        this.type = type;
        this.startTime = start;
        this.duration = duration;
        this.startDate = date;
    }
}
