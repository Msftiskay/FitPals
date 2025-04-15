package com.example.fitpals;

public class WorkoutExercise {
    private long id;
    private long workoutId;
    private long exerciseId;
    private int sets;
    private int reps;
    private float weight;
    private int restTime;
    private String createdAt;
    private String exerciseName;
    private String muscleGroup;

    public WorkoutExercise() {
    }

    public WorkoutExercise(long id, long workoutId, long exerciseId, int sets, int reps, float weight, int restTime, String createdAt, String exerciseName, String muscleGroup) {
        this.id = id;
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.restTime = restTime;
        this.createdAt = createdAt;
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}

