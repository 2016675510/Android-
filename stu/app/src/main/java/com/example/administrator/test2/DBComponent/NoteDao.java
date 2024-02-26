package com.example.administrator.test2.DBComponent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.test2.ActivityView.FragmentControlActivity;
import com.example.administrator.test2.ActivityView.FragmentView.ListNoteActivity;
import com.example.administrator.test2.ActivityView.LoginActivity;
import com.example.administrator.test2.DBComponent.Entity.Note_Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NoteDao {
    public  DBService dbHelper;
    public SQLiteDatabase sqLiteDatabase;
    private  long log;
    public NoteDao(Context context){
        dbHelper=new DBService(context);
    }


    /***
     * 添加数据
     */
    public boolean insertDate(Note_Data note_data){

        //方式1  SQL语句的方式
      /*  String stu_sql="insert into "+UtilDB.DATABASE_TABLE+"("+UtilDB.USER_CONTENT+","+UtilDB.USER_YEAR+","+UtilDB.USER_TIME+") values('"+userContent+"','"+userYear+"','"+userTime+"')";
        sqLiteDatabase.execSQL(stu_sql);
        return true;*/
        //方式2
        sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBService.USER_CONTENT,note_data.getUserContent());
        contentValues.put(DBService.USER_TIME_YEAR,note_data.getUserTimeYear());
        contentValues.put(DBService.USER_TIME,note_data.getUserTime());
        contentValues.put(DBService.USER_Title,note_data.getUserTitle());
        contentValues.put(DBService.USER_NAME,LoginActivity.user.getText().toString());

        return sqLiteDatabase.insert("note_data",null,contentValues)>0;
    }

    /***
     * 删除数据
     * @param id
     * @return
     */
    public boolean deleteData(String id){
        /*String sql = "delete from "+UtilDB.DATABASE_TABLE+" where "+UtilDB.USER_ID+" = "+id;
        sqLiteDatabase.execSQL(sql);
        return true;*/
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql=DBService.USER_ID+"=?";
        String[] contentValuesArray=new String[]{String.valueOf(id)};
        return db.delete("note_data",sql,contentValuesArray)>0;
    }

    /****
     * 修改数据
     * @param id
     * @param content
     * @param userYear
     * @param userTime
     * @return
     */
    public boolean updateData(int id,String content,String userYear,String userTime,String  userTitle){
      /*  String sql = "update "+UtilDB.DATABASE_TABLE+" set "+UtilDB.USER_CONTENT+" = '"+content+"',"
                +UtilDB.USER_YEAR+" = '"+userYear+"',"+UtilDB.USER_TIME+"='"+userTime+"' where "+UtilDB.USER_ID+" = "+id;
        sqLiteDatabase.execSQL(sql);
        return true;*/
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String  ids=String.valueOf(id).toString();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBService.USER_CONTENT,content);
        contentValues.put(DBService.USER_Title,userTitle);
        contentValues.put(DBService.USER_TIME,userTime);
        contentValues.put(DBService.USER_TIME_YEAR,userYear);
        String sql=DBService.USER_ID+"=?";
        String[] strings=new String[]{ids};
        return db.update("note_data",contentValues,sql,strings)>0;
    }
public Note_Data query(int id){
    Note_Data list = new Note_Data();
    SQLiteDatabase db = dbHelper.getReadableDatabase();

    if (db.isOpen()) {
//        String username = LoginActivity.user.getText().toString();
        String query = "SELECT * FROM note_data WHERE id = ?";
        Cursor cursor=db.rawQuery("select * from note_data where id ="+id,null);  //返回游标
         while (cursor.moveToNext()) {
            String user_temp = cursor.getString(cursor.getColumnIndex("username"));
            if (user_temp.equals(LoginActivity.user.getText().toString())) {
                 list.setUserContent(cursor.getString(cursor.getColumnIndex(DBService.USER_CONTENT)));
                list.setUserTitle(cursor.getString(cursor.getColumnIndex(DBService.USER_Title)));
                list.setUserTime(cursor.getString(cursor.getColumnIndex(DBService.USER_TIME)));
                list.setUserTimeYear(cursor.getString(cursor.getColumnIndex(DBService.USER_TIME_YEAR)));
            }
        }

        cursor.close();
    }

    return list;
}
    /***
     * 查询所有数据
     * @return
     */
    public  List<Note_Data> query() {
        List<Note_Data> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String str="";

        if (db.isOpen()) {
            String username = LoginActivity.user.getText().toString();
            String query = "SELECT * FROM note_data WHERE username = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username});
            while (cursor.moveToNext()) {
                String user_temp = cursor.getString(cursor.getColumnIndex("username"));
                if (user_temp.equals(LoginActivity.user.getText().toString())) {
                    Note_Data userInfo = new Note_Data();
                    userInfo.setUserContent(cursor.getString(cursor.getColumnIndex(DBService.USER_CONTENT)));
                    userInfo.setUserTitle(cursor.getString(cursor.getColumnIndex(DBService.USER_Title)));
                    userInfo.setUserTime(cursor.getString(cursor.getColumnIndex(DBService.USER_TIME)));
                    userInfo.setUserTimeYear(cursor.getString(cursor.getColumnIndex(DBService.USER_TIME_YEAR)));
                    list.add(userInfo);
                    str+=userInfo.getUserContent()+"\n";
                    Map<String,Object> map=new HashMap<String, Object>();
                    String text=userInfo.getUserContent() ;
                    int indexOfNewLine = text.indexOf('\n'); // 获取第一个换行符的索引位置
                    String beforeNewLine="";
                    if (indexOfNewLine != -1) { // 如果找到换行符
                          beforeNewLine = text.substring(0, indexOfNewLine); // 截取换行符之前的内容
                     } else {
                        beforeNewLine=userInfo.getUserContent() ;
                    }
                    map.put("content",beforeNewLine);
                    map.put("title",userInfo.getUserTitle());
                    map.put("time",userInfo.getUserTime());
                    map.put("delete","删除");
                    map.put("id",cursor.getString(cursor.getColumnIndex(DBService.USER_ID)));
                    ListNoteActivity.datas.add(map);
                }
            }
            str+="结果：\n";
//             Iterator<Note_Data> iterator = list.iterator();
//            while (iterator.hasNext()) {
//                Object next =  iterator.next();
//                str+=((Note_Data)next).getUserContent()+"\n";
//            }
            for (Note_Data note_data : list) {
                str+=note_data.getUserContent()+"\n";
                System.out.println();
            }
            Log.d("d",str);
            cursor.close();
        }

        return list;
    }

    /***
     * 获取当前日历
     * @return
     */
    public static final String showTimeYear(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(new Date());
    }


    /***
     * 获取当前时间
     * @return
     */
    public static final String showTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ahh:mm");
        return simpleDateFormat.format(new Date());
    }
}
