import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        Invoker theInvoker = new Invoker();

        BasicTask basicTask1 = new BasicTask("Einkaufen");
        BasicTask basicTask2 = new BasicTask("Fahrrad putzen");

        tasks.add(basicTask1);
        tasks.add(basicTask2);

        //complete basicTask1
        Complete completeCommand = new Complete(basicTask1);
        theInvoker.setCommand(completeCommand);
        theInvoker.clickDo();
        theInvoker.clickUndo();
        theInvoker.clickRedo();

        //delete basicTask2
        Delete deleteCommand = new Delete(tasks, basicTask2);
        theInvoker.setCommand(deleteCommand);
        for(Task task : tasks) {
            task.print();
        }
        theInvoker.clickDo();
        for(Task task : tasks) {
            task.print();
        }
        theInvoker.clickUndo();
        for(Task task : tasks) {
            task.print();
        }
    }
}
