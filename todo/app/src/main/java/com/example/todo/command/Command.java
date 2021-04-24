package com.example.todo.command;

public interface Command {
    // every command holds the main operation in execute() and the inverse operation in undo()
    void execute();
    void undo();
    // helper functions for Database
    String getType();
    int getTaskId();
}
