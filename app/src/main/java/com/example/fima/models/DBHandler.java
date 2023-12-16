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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
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
    public boolean checkUserIsExit(String email)
    {
        String sql = "SELECT * FROM " + USERS +
                " WHERE " + EMAIL + " = " + "'" + email + "'";
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
    public boolean addUser(String firstname, String lastname, String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues us = new ContentValues();
        us.put(FIRSTNAME, firstname);
        us.put(LASTNAME, lastname);
        us.put(EMAIL, email);
        us.put(PASSWORD, password);
        long rowID = db.insert(USERS, null, us);
        db.close();
        if (rowID == -1)
        {
            return  false;
        }
        return true;
    }
    // Update thông tin ca nhan
    public boolean updateProfile (String firstname, String lastname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues us = new ContentValues();
        us.put(FIRSTNAME, firstname);
        us.put(LASTNAME, lastname);
        String whereClause =  ID + " = ?";
        String[] whereArgs = {String.valueOf(User.getInstance().getId())};
        int row = db.update(USERS, us, whereClause, whereArgs);
        db.close();
        if (row > 0)
        {
            return true;
        }
        return false;
    }
    // Update mat khau
    public boolean updatePassword(String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues us = new ContentValues();
        us.put(PASSWORD, password);
        String whereClause =  ID + " = ?";
        String[] whereArgs = {String.valueOf(User.getInstance().getId())};
        int row = db.update(USERS, us, whereClause, whereArgs);
        db.close();
        if (row > 0)
        {
            return true;
        }
        return false;
    }
    public boolean updateForgetPassword(String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues us = new ContentValues();
        us.put(PASSWORD, password);
        String whereClause =  EMAIL + " = ?";
        String[] whereArgs = {email};
        int row = db.update(USERS, us, whereClause, whereArgs);
        db.close();
        if (row > 0)
        {
            return true;
        }
        return false;
    }
    // Hàm kiểm tra đăng nhập
    public Map<String, String> checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD};
        String selection = EMAIL + " = ? AND " + PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(USERS, columns, selection, selectionArgs, null, null, null);

        Map<String, String> userData = null;

        if (cursor.moveToFirst()) {
            userData = new HashMap<>();
            userData.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ID))));
            userData.put("firstname", cursor.getString(cursor.getColumnIndexOrThrow(FIRSTNAME)));
            userData.put("lastname", cursor.getString(cursor.getColumnIndexOrThrow(LASTNAME)));
            userData.put("email", cursor.getString(cursor.getColumnIndexOrThrow(EMAIL)));
            userData.put("password", cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD)));
        }

        cursor.close();
        db.close();

        return userData;
    }
    public boolean checkPassword(String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Lấy thông tin người dùng hiện tại từ lớp User
        User currentUser = User.getInstance();

        String[] columns = {ID};
        String selection = ID + " = ? AND " + PASSWORD + " = ?";
        String[] selectionArgs = {String.valueOf(currentUser.getId()), password};

        Cursor cursor = db.query(USERS, columns, selection, selectionArgs, null, null, null);

        boolean isPasswordCorrect = cursor.getCount() != 0;

        cursor.close();
        db.close();

        return isPasswordCorrect;
    }

    public boolean deleteUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = ID + " = ?";
        String[] whereArgs = {String.valueOf(User.getInstance().getId())};
        int row = db.delete(USERS, whereClause, whereArgs);
        if (row > 0)
        {
            return true;
        }
        return false;
    }
    // Check infor of account to change password
    public boolean checkInforForgetPass(String firstname, String lastname, String email)
    {
        String sql = "SELECT * FROM " + USERS +
                " WHERE " + FIRSTNAME + " = " + "'" + firstname + "'" +
                " AND " + LASTNAME + " = " + "'" + lastname + "'" +
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