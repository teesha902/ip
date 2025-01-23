package tasks;

public class Task {
    protected String name;
    protected boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public String status() {
        if (isDone) {
            return "X";
        }
        return " ";
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.status() + "] " + this.name;
    }
}
