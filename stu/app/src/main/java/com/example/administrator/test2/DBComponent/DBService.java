package com.example.administrator.test2.DBComponent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBService extends SQLiteOpenHelper {
    //字段
    public static final String USER_ID="id";
    public static final String USER_CONTENT="content";
    public static final String USER_Title="usertitle";
    public static final String USER_TIME="usertime";
    public static final String USER_TIME_YEAR="usertimeyear";
    public static final String USER_NAME="username";
    public static final String PASSWORD="password";
    public static final String ToDoContent="todoContent";
    public static final String IsFinish="isFinish";


    private  final static int DATABASE_VERSION=4;
    private final static String DATABASE_NAME="project.db";
     private final static String create_usertable_sql="create table "+"user_data"+
            "(id integer primary key autoincrement,username text,password text)";
    private final static String create_note_table_sql="create table "+"note_data"+
            "(id integer primary key autoincrement,"+USER_NAME+ " text,"+ USER_CONTENT+ " text," +USER_Title+" text,"+USER_TIME +" text,"+USER_TIME_YEAR+ " text )";
    private final static String create_todo_table_sql="create table "+"todo_data"+
            "(id integer primary key autoincrement,"+USER_NAME+ " text,"+ ToDoContent+ " text," +IsFinish+" int "+  "   )";

    private static SQLiteOpenHelper instance;
    public  DBService(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db) {
     //建表
        db.execSQL(create_usertable_sql);
        db.execSQL(create_note_table_sql);
        db.execSQL(create_todo_table_sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         //建表
        db.execSQL(create_usertable_sql);
        db.execSQL(create_note_table_sql);
        db.execSQL(create_todo_table_sql);
        onCreate(db);
    }

    public DBService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }
}
