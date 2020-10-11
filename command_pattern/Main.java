import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Task> openTasks = new ArrayList<>();
        List<Task> completedTasks = new ArrayList<>();
        Invoker theInvoker = new Invoker();

        BasicTask basicTask1 = new BasicTask("Einkaufen");
        BasicTask basicTask2 = new BasicTask("Fahrrad putzen");

        openTasks.add(basicTask1);
        openTasks.add(basicTask2);

        //complete basicTask1
        Complete completeCommand = new Complete(openTasks, completedTasks, basicTask1);
        theInvoker.setCommand(completeCommand);
        for(Task task : openTasks) {
            task.print();
        }
        theInvoker.clickDo();
        for(Task task : openTasks) {
            task.print();
        }
        theInvoker.clickUndo();
        for(Task task : openTasks) {
            task.print();
        }
        theInvoker.clickRedo();
        for(Task task : openTasks) {
            task.print();
        }

        //delete basicTask2
        /*
        Delete deleteCommand = new Delete(openTasks, basicTask2);
        theInvoker.setCommand(deleteCommand);
        for(Task task : openTasks) {
            task.print();
        }
        theInvoker.clickDo();
        for(Task task : openTasks) {
            task.print();
        }
        theInvoker.clickUndo();
        for(Task task : openTasks) {
            task.print();
        }*/
    }
}
