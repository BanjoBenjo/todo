package com.example.todo;

public interface Command {
    void execute();
    void undo();
    void redo();
}
