package com.example.fitpals;

public class Workout {
    private long id;
    private String title;
    private String description;
    private long creatorId;
    private boolean isJoint;
    private int duration;
    private int calories;
    private String createdAt;

    public Workout() {
    }

    public Workout(String title, String description, long creatorId) {
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.isJoint = false;
    }

    public Workout(long id, String title, String description, long creatorId, boolean isJoint, int duration, int calories, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.isJoint = isJoint;
        this.duration = duration;
        this.calories = calories;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isJoint() {
        return isJoint;
    }

    public void setJoint(boolean joint) {
        isJoint = joint;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
