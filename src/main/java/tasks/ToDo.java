package tasks;

public class ToDo extends Task{
    //tasks without any date/time attached to it

    public ToDo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
