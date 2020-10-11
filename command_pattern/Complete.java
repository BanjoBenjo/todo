import java.util.List;

public class Complete implements Command {
    private List<Task> openTasks;
    private List<Task> completedTasks;
    private Task theTask;
    private int theIndex;

    public Complete(List<Task> newOpenTasks, List<Task> newCompletedTasks, Task newTask) {
        openTasks = newOpenTasks;
        completedTasks = newCompletedTasks;
        theTask = newTask;
    }

    @Override
    public void execute() {
        completedTasks.add(theTask);
        theIndex = openTasks.indexOf(theTask);
        openTasks.remove(theTask);
    }

    public void undo() {
        openTasks.add(theIndex, theTask);
        completedTasks.remove(theTask);
    }

    public void redo() {
        execute();
    }
}
