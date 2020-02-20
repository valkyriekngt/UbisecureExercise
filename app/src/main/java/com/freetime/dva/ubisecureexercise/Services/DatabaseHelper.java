package com.freetime.dva.ubisecureexercise.Services;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.freetime.dva.ubisecureexercise.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db";
    private static final String ID = "ID";
    private static final String USERTABLE_NAME = "user_table";
    private static final String FIRST_NAME_COL = "FIRSTNAME";
    private static final String LAST_NAME_COL = "LASTNAME";
    private static final String PASSWORD_COL = "PASSWORD";
    private static final String USERNAME_COL = "USERNAME";
    private static final String EMAIL_COL = "EMAIL";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ USERTABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, PASSWORD TEXT, USERNAME TEXT, EMAIL TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIRST_NAME_COL, user.getFirstName());
        contentValues.put(LAST_NAME_COL, user.getLastName());
        contentValues.put(PASSWORD_COL, user.getPassword());
        contentValues.put(USERNAME_COL, user.getUserName());
        contentValues.put(EMAIL_COL, user.getEmail());

        db.insert(USERTABLE_NAME,null,  contentValues);
        db.close();
    }

    public User getCurrentUser(String username){
        User user = new User();
        String[] columns = {
                FIRST_NAME_COL,
                LAST_NAME_COL,
                USERNAME_COL,
                PASSWORD_COL,
                EMAIL_COL
        };
        String criteria = USERNAME_COL + "=?";
        String[] criteriaArgs = {username};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ USERTABLE_NAME+ " WHERE USERNAME = \" aerogant \" " , null);
        if(cursor.moveToFirst()) {
            //Cursor cursor = db.query(USERTABLE_NAME, columns,criteria, criteriaArgs, null, null, null );
            user.setFirstName(cursor.getString(cursor.getColumnIndex(FIRST_NAME_COL)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(LAST_NAME_COL)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(USERNAME_COL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD_COL)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_COL)));
        }
        cursor.close();
        db.close();

        return user;



    }

    public List<User> getAllUser(){

        String[] columns = {
                FIRST_NAME_COL,
                LAST_NAME_COL,
                PASSWORD_COL,
                USERNAME_COL,
                EMAIL_COL
        };
        String sortOrder = USERNAME_COL + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USERTABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setFirstName(cursor.getString(cursor.getColumnIndex(FIRST_NAME_COL)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(LAST_NAME_COL)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(USERNAME_COL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD_COL)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_COL)));

                userList.add(user);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    //Check if user exists or not
    public boolean checkUser(String username){
        String[] columns = {ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String criteria = USERNAME_COL + " =?";

        String[] criteriaArgs = {username};

        Cursor cursor = db.query(
                USERTABLE_NAME,
                columns,
                criteria,
                criteriaArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean loginCheck(String username, String password) {
        String[] columns = {ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String criteria = USERNAME_COL + " = ?" + " AND " + PASSWORD_COL + " = ?";

        String[] criteriaArgs = {username, password};

        Cursor cursor = db.query(
                USERTABLE_NAME,
                columns,
                criteria,
                criteriaArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


}
