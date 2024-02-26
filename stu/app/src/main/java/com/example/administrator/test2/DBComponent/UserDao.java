package com.example.administrator.test2.DBComponent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.test2.DBComponent.Entity.User_Data;

public class UserDao {
    public  DBService dbHelper;
    public SQLiteDatabase sqLiteDatabase;
    private  long log;
    public UserDao(Context context){
        dbHelper=new DBService(context);
    }

    public  long  insert_user (User_Data userData_){
        sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username", userData_.getUsername());
        values.put("password", userData_.getPassword());
        return   sqLiteDatabase.insert("user_data",null,values);  //返回-1则插入失败
    }
    public int query_user(String user) {
         SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor=db.rawQuery("select * from user_data order by id desc",null);  //返回游标
            while(cursor.moveToNext()){
                String user_temp= cursor.getString(cursor.getColumnIndex("username"));
                if(user_temp==null){
                    return 0;
                }else if(user_temp.equals(user)){
                    return 1;
                }
            }
            cursor.close();
        }
        return 0;
    }
    public int query_user(String user,String password) {

         SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor=db.rawQuery("select * from user_data order by id desc",null);  //返回游标
            while(cursor.moveToNext()){
                String user_temp= cursor.getString(cursor.getColumnIndex("username"));
                String password_temp= cursor.getString(cursor.getColumnIndex("password"));
                if(user_temp==null){
                    return 0;
                }else if(user_temp.equals(user)&&password_temp.equals(password)){
                    return 1;
                }
            }
            cursor.close();
        }
        return 0;
    }
}
