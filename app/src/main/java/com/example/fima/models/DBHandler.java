package com.example.fima.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fima.db";
    private static final String USERS = "users";
    private static final String USER_EXPENSES = "user_expenses";
    private static final String USER_TODO = "user_todo";
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String DATE = "date";
    private static final String AMOUNT = "amount";
    private static final String TITLE = "title";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private static DBHandler instance;
    public static synchronized DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context.getApplicationContext());
        }
        return instance;
    }

    private DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableUsersQuery = "CREATE TABLE " + USERS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIRSTNAME + " TEXT, " + LASTNAME + " TEXT, " +
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
        String createTableUsersTodoQuery = "CREATE TABLE " + USER_TODO + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_ID + " INTEGER, " +
                TITLE + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                DATE + " TEXT, " +
                AMOUNT + " REAL);";
        db.execSQL(createTableUsersTodoQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        db.execSQL("DROP TABLE IF EXISTS " + USER_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TODO);
        onCreate(db);
    }
    // Kiem tra user
    boolean checkUserIsExit(String firstName, String lastName, String email)
    {
        String sql = "SELECT * FROM " + USERS +
                " WHERE " + FIRSTNAME + " = " + "'" + firstName + "'" +
                " AND " + LASTNAME + " = " + "'" + lastName + "'" +
                " AND " + EMAIL + " = " + "'" + email + "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    // Them mot user moi
    void addUser(Context context, User user)
    {
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        String email = user.getEmail();
        // Kiem tra ton tai khong
        if (checkUserIsExit(firstName, lastName, email))
        {
            // Toast.makeText(this, "Account exits!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            // values.put();
        }
    }


    public void addExpense(UserExpense expense){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TYPE, expense.getType());
        cv.put(AMOUNT, expense.getAmount());
        cv.put(DATE, expense.getDate());
        cv.put(DESCRIPTION, expense.getDescription());
        db.insert(USER_EXPENSES, null, cv);
        db.close();
    }

    public void updateExpense(UserExpense expense){
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

    public void deleteExpense(UserExpense expense){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = ID + " = ?";
        String[] whereArgs = {String.valueOf(expense.getId())};
        db.delete(USER_EXPENSES, whereClause,whereArgs);
    }

    public ArrayList<UserExpense> fetchExpensesByDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor
                = db.rawQuery("SELECT * FROM " + USER_EXPENSES + " WHERE " + DATE + " = " + "'"+date+"'", null);

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

}