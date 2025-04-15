package com.example.fitpals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fitfriend.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_FRIENDS = "friends";
    private static final String TABLE_WORKOUTS = "workouts";
    private static final String TABLE_EXERCISES = "exercises";
    private static final String TABLE_WORKOUT_EXERCISES = "workout_exercises";
    private static final String TABLE_JOINT_WORKOUTS = "joint_workouts";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // USER Table columns
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PROFILE_PIC = "profile_pic";

    // FRIENDS Table columns
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_FRIEND_ID = "friend_id";
    private static final String KEY_STATUS = "status";

    // WORKOUTS Table columns
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CREATOR_ID = "creator_id";
    private static final String KEY_IS_JOINT = "is_joint";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_CALORIES = "calories";

    // EXERCISES Table columns
    private static final String KEY_EXERCISE_NAME = "name";
    private static final String KEY_MUSCLE_GROUP = "muscle_group";
    private static final String KEY_INSTRUCTION = "instruction";

    // WORKOUT_EXERCISES Table columns
    private static final String KEY_WORKOUT_ID = "workout_id";
    private static final String KEY_EXERCISE_ID = "exercise_id";
    private static final String KEY_SETS = "sets";
    private static final String KEY_REPS = "reps";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_REST_TIME = "rest_time";

    // JOINT_WORKOUTS Table columns
    private static final String KEY_INVITER_ID = "inviter_id";
    private static final String KEY_INVITEE_ID = "invitee_id";
    private static final String KEY_ACCEPTED = "accepted";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_PROFILE_PIC + " TEXT,"
                + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Friends table
        String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIENDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_FRIEND_ID + " INTEGER,"
                + KEY_STATUS + " TEXT,"
                + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_FRIEND_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_FRIENDS_TABLE);

        // Create Workouts table
        String CREATE_WORKOUTS_TABLE = "CREATE TABLE " + TABLE_WORKOUTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_CREATOR_ID + " INTEGER,"
                + KEY_IS_JOINT + " INTEGER DEFAULT 0,"
                + KEY_DURATION + " INTEGER DEFAULT 0,"
                + KEY_CALORIES + " INTEGER DEFAULT 0,"
                + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(" + KEY_CREATOR_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_WORKOUTS_TABLE);

        // Create Exercises table
        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_EXERCISE_NAME + " TEXT,"
                + KEY_MUSCLE_GROUP + " TEXT,"
                + KEY_INSTRUCTION + " TEXT,"
                + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_EXERCISES_TABLE);

        // Create Workout_Exercises table
        String CREATE_WORKOUT_EXERCISES_TABLE = "CREATE TABLE " + TABLE_WORKOUT_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_WORKOUT_ID + " INTEGER,"
                + KEY_EXERCISE_ID + " INTEGER,"
                + KEY_SETS + " INTEGER DEFAULT 3,"
                + KEY_REPS + " INTEGER DEFAULT 10,"
                + KEY_WEIGHT + " REAL DEFAULT 0,"
                + KEY_REST_TIME + " INTEGER DEFAULT 60,"
                + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(" + KEY_WORKOUT_ID + ") REFERENCES " + TABLE_WORKOUTS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_EXERCISE_ID + ") REFERENCES " + TABLE_EXERCISES + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_WORKOUT_EXERCISES_TABLE);

        // Create Joint_Workouts table
        String CREATE_JOINT_WORKOUTS_TABLE = "CREATE TABLE " + TABLE_JOINT_WORKOUTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_WORKOUT_ID + " INTEGER,"
                + KEY_INVITER_ID + " INTEGER,"
                + KEY_INVITEE_ID + " INTEGER,"
                + KEY_ACCEPTED + " INTEGER DEFAULT 0,"
                + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(" + KEY_WORKOUT_ID + ") REFERENCES " + TABLE_WORKOUTS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_INVITER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_INVITEE_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_JOINT_WORKOUTS_TABLE);

        // Add default exercises
        insertDefaultExercises(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOINT_WORKOUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertDefaultExercises(SQLiteDatabase db) {
        String[][] defaultExercises = {
                {"Push-Up", "Chest", "Start in a plank position with hands shoulder-width apart. Lower body until chest nearly touches the floor, then push back up."},
                {"Squat", "Legs", "Stand with feet shoulder-width apart. Lower your body by bending knees and pushing hips back. Return to starting position."},
                {"Plank", "Core", "Hold a push-up position with arms extended or resting on forearms. Keep body in a straight line."},
                {"Lunges", "Legs", "Step forward with one leg, lowering your hips until both knees are bent at 90 degrees. Return to starting position."},
                {"Bicep Curl", "Arms", "Hold dumbbells at sides with palms facing forward. Bend at elbows to bring weights toward shoulders."},
                {"Tricep Dip", "Arms", "Sit on edge of bench, hands gripping edge. Slide buttocks off bench and lower body by bending elbows."},
                {"Pull-Up", "Back", "Hang from a bar with palms facing away. Pull body up until chin clears the bar."},
                {"Deadlift", "Back", "Stand with feet hip-width apart, bend at hips and knees to grip barbell. Lift bar by extending hips and knees."},
                {"Shoulder Press", "Shoulders", "Sit or stand with dumbbells at shoulder height. Press weights upward until arms are extended."},
                {"Crunches", "Core", "Lie on back with knees bent. Place hands behind head and lift shoulders off the floor."}
        };

        for (String[] exercise : defaultExercises) {
            ContentValues values = new ContentValues();
            values.put(KEY_EXERCISE_NAME, exercise[0]);
            values.put(KEY_MUSCLE_GROUP, exercise[1]);
            values.put(KEY_INSTRUCTION, exercise[2]);
            db.insert(TABLE_EXERCISES, null, values);
        }
    }

    // -------- USER CRUD OPERATIONS --------
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PROFILE_PIC, user.getProfilePic());

        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public User getUser(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[] { KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_PROFILE_PIC, KEY_CREATED_AT },
                KEY_ID + "=?",
                new String[] { String.valueOf(id) },
                null, null, null, null
        );

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );
        cursor.close();
        return user;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[] { KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_PROFILE_PIC, KEY_CREATED_AT },
                KEY_EMAIL + "=?",
                new String[] { email },
                null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            cursor.close();
            return user;
        }
        return null;
    }

    // -------- FRIEND CRUD OPERATIONS --------
    public long addFriend(long userId, long friendId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_FRIEND_ID, friendId);
        values.put(KEY_STATUS, "pending");

        long id = db.insert(TABLE_FRIENDS, null, values);
        db.close();
        return id;
    }

    public int updateFriendStatus(long id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, status);

        return db.update(TABLE_FRIENDS, values, KEY_ID + "=?", new String[] { String.valueOf(id) });
    }

    public List<Friend> getUserFriends(long userId) {
        List<Friend> friendsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FRIENDS +
                " WHERE " + KEY_USER_ID + "=" + userId +
                " OR " + KEY_FRIEND_ID + "=" + userId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Friend friend = new Friend(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getLong(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                friendsList.add(friend);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return friendsList;
    }

    // -------- EXERCISE CRUD OPERATIONS --------
    public List<Exercise> getAllExercises() {
        List<Exercise> exerciseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return exerciseList;
    }

    // -------- WORKOUT CRUD OPERATIONS --------
    public long createWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, workout.getTitle());
        values.put(KEY_DESCRIPTION, workout.getDescription());
        values.put(KEY_CREATOR_ID, workout.getCreatorId());
        values.put(KEY_IS_JOINT, workout.isJoint() ? 1 : 0);

        long workoutId = db.insert(TABLE_WORKOUTS, null, values);
        db.close();
        return workoutId;
    }

    public long addExerciseToWorkout(long workoutId, long exerciseId, int sets, int reps, float weight, int restTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_EXERCISE_ID, exerciseId);
        values.put(KEY_SETS, sets);
        values.put(KEY_REPS, reps);
        values.put(KEY_WEIGHT, weight);
        values.put(KEY_REST_TIME, restTime);

        long id = db.insert(TABLE_WORKOUT_EXERCISES, null, values);
        db.close();
        return id;
    }

    public List<Workout> getUserWorkouts(long userId) {
        List<Workout> workoutList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_WORKOUTS +
                " WHERE " + KEY_CREATOR_ID + "=" + userId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getLong(3),
                        cursor.getInt(4) == 1,
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getString(7)
                );
                workoutList.add(workout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return workoutList;
    }

    public List<WorkoutExercise> getWorkoutExercises(long workoutId) {
        List<WorkoutExercise> exerciseList = new ArrayList<>();
        String selectQuery = "SELECT we.*, e.name, e.muscle_group FROM " + TABLE_WORKOUT_EXERCISES + " we" +
                " JOIN " + TABLE_EXERCISES + " e ON we." + KEY_EXERCISE_ID + "=e." + KEY_ID +
                " WHERE we." + KEY_WORKOUT_ID + "=" + workoutId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                WorkoutExercise exercise = new WorkoutExercise(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getLong(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getFloat(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                );
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return exerciseList;
    }

    // -------- JOINT WORKOUT OPERATIONS --------
    public long createJointWorkout(long workoutId, long inviterId, long inviteeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // First mark the workout as joint
        ContentValues workoutValues = new ContentValues();
        workoutValues.put(KEY_IS_JOINT, 1);
        db.update(TABLE_WORKOUTS, workoutValues, KEY_ID + "=?", new String[] { String.valueOf(workoutId) });

        // Then create joint workout entry
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_INVITER_ID, inviterId);
        values.put(KEY_INVITEE_ID, inviteeId);

        long id = db.insert(TABLE_JOINT_WORKOUTS, null, values);
        db.close();
        return id;
    }

    public int acceptJointWorkout(long jointWorkoutId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ACCEPTED, 1);

        return db.update(TABLE_JOINT_WORKOUTS, values, KEY_ID + "=?", new String[] { String.valueOf(jointWorkoutId) });
    }

    public List<Workout> getJointWorkoutInvites(long userId) {
        List<Workout> invites = new ArrayList<>();
        String selectQuery = "SELECT w.* FROM " + TABLE_WORKOUTS + " w" +
                " JOIN " + TABLE_JOINT_WORKOUTS + " jw ON w." + KEY_ID + "=jw." + KEY_WORKOUT_ID +
                " WHERE jw." + KEY_INVITEE_ID + "=" + userId +
                " AND jw." + KEY_ACCEPTED + "=0";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getLong(3),
                        cursor.getInt(4) == 1,
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getString(7)
                );
                invites.add(workout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return invites;
    }
}