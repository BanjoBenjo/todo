package com.example.todo.command;

public interface Command {
    /**
     * Interface for the commands, without execute() and undo() they would not be operational.
     */
    void execute();
    void undo();

    // helper methods
    String getType();
    int getTaskId();
}
