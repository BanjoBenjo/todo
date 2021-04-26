package com.example.todo.notifications;

import android.content.Context;

import java.time.LocalDateTime;

public interface Notification {
    void do_notify(Context context);
    void cancel(Context context);
}

