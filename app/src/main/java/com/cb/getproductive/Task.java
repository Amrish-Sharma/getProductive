package com.cb.getproductive;

public class Task {
    private String text;
    private boolean completed;

    private long datetimeCreated;

    public Task(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
        this.datetimeCreated = System.currentTimeMillis();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getDatetimeCreated() {
        return datetimeCreated;
    }

    public void setDatetimeCreated(long datetimeCreated) {
        this.datetimeCreated = datetimeCreated;
    }
}
