package com.example.fitpals;

public class Exercise {
    private long id;
    private String name;
    private String muscleGroup;
    private String instruction;
    private String createdAt;

    public Exercise() {
    }

    public Exercise(long id, String name, String muscleGroup, String instruction, String createdAt) {
        this.id = id;
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.instruction = instruction;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}