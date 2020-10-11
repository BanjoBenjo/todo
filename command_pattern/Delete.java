import java.util.List;

public class Delete implements Command {
    private List<Task> openTasks;
    private Task theTask;

    public Delete(List<Task> newTasks, Task newTask) {
        openTasks = newTasks;
        theTask = newTask;
    }

    @Override
    public void execute() {
        openTasks.remove(theTask);
        System.out.println(theTask.toString() + " deleted");
    }

    public void undo() {
        openTasks.add(theTask);
        System.out.println("undo delete");
    }

    public void redo() {
        execute();
    }
}
