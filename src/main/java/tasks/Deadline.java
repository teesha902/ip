package tasks;

public class Deadline extends Task{
    //tasks that need to be done before a specific date/time
    protected String due;

    public Deadline(String name, String due) {
        super(name);
        this.due = due;
    }

    public Deadline(String name, String due, boolean isDone) {
        super(name);
        this.due = due;
        this.isDone = isDone;
    }

    public String getDue() {
        return due;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + due + ")";
    }
}
