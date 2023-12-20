package com.example.fima.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fima.db";
    private static final String USERS = "users";
    private static final String USER_EXPENSES = "user_expenses";
    private static final String TARGET = "target";
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String DATE = "date";
    private static final String AMOUNT = "amount";
    private static final String TITLE = "title";

    // them cac thuoc tinh bang to-do
    private static final String TOTAL_BUDGET = "totalBudget";
    private static final String SAVED_BUDGET = "saved_budget";
    private static final String PRIORITY_LEVEL = "priority_level";
    private static final String TARGET_TYPE = "target_type";
    private static final String IMG_SRC = "img_src";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private static DBHandler instance;

    public static synchronized DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context.getApplicationContext());
        }
        return instance;
    }

    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUsersQuery = "CREATE TABLE " + USERS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT, " +
                EMAIL + " TEXT, " +
                PASSWORD + " TEXT);";
        db.execSQL(createTableUsersQuery);
        String createTableUsersExpensesQuery = "CREATE TABLE " + USER_EXPENSES + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_ID + " INTEGER, " +
                TYPE + " INTEGER, " +
                DESCRIPTION + " TEXT, " +
                DATE + " TEXT, " +
                AMOUNT + " REAL);";
        db.execSQL(createTableUsersExpensesQuery);
        // sửa bảng này
        String createTableUsersTodoQuery = "CREATE TABLE " + TARGET + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_ID + " INTEGER, " +
                // name
                TITLE + " TEXT, " +
                TOTAL_BUDGET + " REAL, " +
                SAVED_BUDGET + " REAL, " +
                DATE + " DATE, " +
                TARGET_TYPE + " TEXT, " +
                PRIORITY_LEVEL + " INTEGER, " +
                IMG_SRC + " TEXT);";
        db.execSQL(createTableUsersTodoQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + USERS + ", " + USER_EXPENSES + ", " + TARGET;
        db.execSQL(sql);
        onCreate(db);
    }


    public void addExpense(UserExpense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TYPE, expense.getType());
        cv.put(AMOUNT, expense.getAmount());
        cv.put(DATE, expense.getDate());
        cv.put(DESCRIPTION, expense.getDescription());
        db.insert(USER_EXPENSES, null, cv);
        db.close();
    }

    public void updateExpense(UserExpense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TYPE, expense.getType());
        cv.put(AMOUNT, expense.getAmount());
        cv.put(DATE, expense.getDate());
        cv.put(DESCRIPTION, expense.getDescription());
        String whereClause = ID + " = ?";
        String[] whereArgs = {String.valueOf(expense.getId())};
        db.update(USER_EXPENSES, cv, whereClause, whereArgs);
    }

    public void deleteExpense(UserExpense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = ID + " = ?";
        String[] whereArgs = {String.valueOf(expense.getId())};
        db.delete(USER_EXPENSES, whereClause, whereArgs);
    }

    public ArrayList<UserExpense> fetchExpensesByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor
                = db.rawQuery("SELECT * FROM " + USER_EXPENSES + " WHERE " + DATE + " = " + "'" + date + "'", null);

        ArrayList<UserExpense> expenses
                = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                expenses.add(new UserExpense(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DATE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(AMOUNT))));
            } while (cursor.moveToNext());
        }
        Collections.reverse(expenses);
        cursor.close();
        return expenses;
    }

    // câu lệnh them bang TARGET
    public void addTarget(Target target) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, target.getPlanName());
        cv.put(TOTAL_BUDGET, target.getTotalBudget());
        cv.put(SAVED_BUDGET, target.getSavedBudget());
        cv.put(DATE, target.getDeadline());
        cv.put(TARGET_TYPE, target.getTargetType());
        cv.put(PRIORITY_LEVEL, target.getPriorityLevel());
        cv.put(IMG_SRC, target.getImgSrc());
        db.insert(TARGET, null, cv);
        db.close();
    }

    public void updateTarget(Target target) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, target.getPlanName());
        cv.put(TOTAL_BUDGET, target.getTotalBudget());
        cv.put(SAVED_BUDGET, target.getSavedBudget());
        cv.put(DATE, target.getDeadline());
        cv.put(PRIORITY_LEVEL, target.getPriorityLevel());
        cv.put(TARGET_TYPE, target.getTargetType());
        cv.put(IMG_SRC, target.getImgSrc());
        String whereClause = ID + " = ?";
        String[] whereArgs = {String.valueOf(target.getID())};
        db.update(TARGET, cv, whereClause, whereArgs);

    }

    public void deleteTarget(Target target) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = ID + " = ?";
        String[] whereArgs = {String.valueOf(target.getID())};
        db.delete(TARGET, whereClause, whereArgs);
    }

    public ArrayList<Target> fetchTargetByType(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor
                = db.rawQuery("SELECT * FROM " + TARGET + " WHERE " + TARGET_TYPE + " = " + "'" + type + "'", null);

        ArrayList<Target> targets
                = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                targets.add(new Target(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(TOTAL_BUDGET)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(SAVED_BUDGET)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TARGET_TYPE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PRIORITY_LEVEL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(IMG_SRC))
                ));

            } while (cursor.moveToNext());
        }
        Collections.reverse(targets);
        cursor.close();
        return targets;
    }

    // select toan bo du lieu
    public ArrayList<Target> fetchAllTarget() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor
                = db.rawQuery("SELECT * FROM " + TARGET, null);

        ArrayList<Target> targets
                = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                targets.add(new Target(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(TOTAL_BUDGET)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(SAVED_BUDGET)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TARGET_TYPE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PRIORITY_LEVEL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(IMG_SRC))
                ));
            } while (cursor.moveToNext());
        }
        Collections.reverse(targets);
        cursor.close();
        return targets;
    }


}
