package com.example.administrator.test2.DBComponent;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.test2.ActivityView.FragmentView.ListNoteActivity;
import com.example.administrator.test2.ActivityView.LoginActivity;
import com.example.administrator.test2.DBComponent.Entity.Note_Data;
import com.example.administrator.test2.DBComponent.Entity.Todo_Data;
import com.example.administrator.test2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoDao {
    public  DBService dbHelper;
    public SQLiteDatabase sqLiteDatabase;
    private  long log;
    private final Context myContext;
     public TodoDao(Context context){
         myContext=context;
         dbHelper=new DBService(context);
    }

    /****
     * 修改数据
     * @param id
     * @param content
     * @param userYear
     * @param userTime
     * @return
     */
    public boolean updateData(int id,int isFinish){
      /*  String sql = "update "+UtilDB.DATABASE_TABLE+" set "+UtilDB.USER_CONTENT+" = '"+content+"',"
                +UtilDB.USER_YEAR+" = '"+userYear+"',"+UtilDB.USER_TIME+"='"+userTime+"' where "+UtilDB.USER_ID+" = "+id;
        sqLiteDatabase.execSQL(sql);
        return true;*/
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String  ids=String.valueOf(id).toString();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBService.IsFinish,isFinish);
        String sql=DBService.USER_ID+"=?";
        String[] strings=new String[]{ids};
        return db.update("todo_data",contentValues,sql,strings)>0;
    }
    /***
     * 添加数据
     */
    public boolean insertDate(Todo_Data note_data){

        //方式1  SQL语句的方式
      /*  String stu_sql="insert into "+UtilDB.DATABASE_TABLE+"("+UtilDB.USER_CONTENT+","+UtilDB.USER_YEAR+","+UtilDB.USER_TIME+") values('"+userContent+"','"+userYear+"','"+userTime+"')";
        sqLiteDatabase.execSQL(stu_sql);
        return true;*/
        //方式2
        sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBService.ToDoContent,note_data.getTodoContent());
         contentValues.put(DBService.IsFinish,note_data.getIsFinish());
        contentValues.put(DBService.USER_NAME, LoginActivity.user.getText().toString());
        return sqLiteDatabase.insert("todo_data",null,contentValues)>0;
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
        return db.delete("todo_data",sql,contentValuesArray)>0;
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
    public List<Map<String, Object>>query() {
        List<Map<String, Object>> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String str="";
        if (db.isOpen()) {
            String username = LoginActivity.user.getText().toString();
            String query = "SELECT * FROM todo_data WHERE username = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username});
            while (cursor.moveToNext()) {
                String user_temp = cursor.getString(cursor.getColumnIndex("username"));
                if (user_temp.equals(LoginActivity.user.getText().toString())) {
                    Todo_Data userInfo = new Todo_Data();
                    userInfo.setTodoContent(cursor.getString(cursor.getColumnIndex(DBService.ToDoContent)));
                    userInfo.setIsFinish(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBService.IsFinish))));
                    str+=userInfo.getTodoContent()+"\n";
                    String text=userInfo.getTodoContent() ;
                    int indexOfNewLine = text.indexOf('\n'); // 获取第一个换行符的索引位置
                    String beforeNewLine="";
                    if (indexOfNewLine != -1) { // 如果找到换行符
                        beforeNewLine = text.substring(0, indexOfNewLine); // 截取换行符之前的内容
                    } else {
                        beforeNewLine=userInfo.getTodoContent() ;
                    }

                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("title",beforeNewLine);
                    @SuppressLint("Recycle") TypedArray checked_icon =myContext.getResources().obtainTypedArray(R.array.checked_icon);
                    map.put("image", checked_icon.getResourceId(userInfo.getIsFinish(), 0));
                    map.put("status", userInfo.getIsFinish());
                    map.put("isFinish",userInfo.getIsFinish());
                    map.put("id",Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBService.USER_ID))));
                    list.add(map);
                }
            }
            str+="结果：\n";
            cursor.close();
        }
        return list;
    }

}
