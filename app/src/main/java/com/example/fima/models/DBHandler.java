package com.example.fima.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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

    private DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
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
        String sql = "Drop table if exists " + USERS + " , " + USER_EXPENSES + ", " + USER_TODO;
        db.execSQL(sql);
        onCreate(db);
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
    private static String getNextDay(String dateString) {
        try {
            // Định dạng ngày
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(dateString);

            // Lấy ngày tiếp theo
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);

            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<UserExpense> fetchExpensesBetweenDates(String startDate, String endDate){


        ArrayList<UserExpense> expenses
                = new ArrayList<>();
        String date = startDate;
        while (!date.equals(getNextDay(endDate))){
            expenses.addAll(fetchExpensesByDate(date));
            date = getNextDay(date);
        }



        Collections.sort(expenses, new Comparator<UserExpense>() {
            @Override
            public int compare(UserExpense e1, UserExpense e2) {
                return Integer.compare(e1.getType(), e2.getType());
            }
        });

        for (UserExpense expense : expenses) {
            expense.setDescription("");
        }
        int i = 0;
        while (i < expenses.size()-1){
            if(expenses.get(i).getType() == expenses.get(i+1).getType()) {
                expenses.get(i).setAmount(expenses.get(i).getAmount()+expenses.get(i+1).getAmount());

                expenses.remove(i+1);
            }
            else i++;
        }

        return expenses;
    }

    public ArrayList<UserExpense> fetchExpensesBetweenDates1(String startDate, String endDate){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor
                = db.rawQuery("SELECT * FROM " + USER_EXPENSES + " WHERE " +DATE + " BETWEEN " +"'" +startDate+
                "'"  + " AND " +"'" +endDate+ "'" , null);

        ArrayList<UserExpense> expenses
                = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                expenses.add(new UserExpense(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(TYPE)),
                        "",
                        cursor.getString(cursor.getColumnIndexOrThrow(DATE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(AMOUNT))));
            } while (cursor.moveToNext());
        }
        Collections.reverse(expenses);
        cursor.close();

        Collections.sort(expenses, new Comparator<UserExpense>() {
            @Override
            public int compare(UserExpense e1, UserExpense e2) {
                return Integer.compare(e1.getType(), e2.getType());
            }
        });

        ArrayList<UserExpense> list = new ArrayList<>();
        int i = 0;
        while (i < expenses.size()-1){
            if(expenses.get(i).getType() == expenses.get(i+1).getType()) {
                expenses.get(i).setAmount(expenses.get(i).getAmount()+expenses.get(i+1).getAmount());
                expenses.remove(i+1);
            }
            else i++;
        }

        return expenses;
    }



}
