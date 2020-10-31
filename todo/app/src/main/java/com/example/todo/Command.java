package com.example.todo;

public interface Command {
    void execute();
    void undo();
    //redo() is optional, is implemented in Invoker.clickRedo()
}
