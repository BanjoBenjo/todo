import java.util.List;

public class Delete implements Command {
    private final List<Task> theTasks;
    private final Task theTask;

    public Delete(List<Task> newTasks, Task newTask) {
        theTasks = newTasks;
        theTask = newTask;
    }

    @Override
    public void execute() {
        theTasks.remove(theTask);
        System.out.println(theTask.toString() + " deleted");
    }

    public void undo() {
        theTasks.add(theTask);
        System.out.println("undo delete");
    }

    public void redo() {
        this.execute();
    }
}
