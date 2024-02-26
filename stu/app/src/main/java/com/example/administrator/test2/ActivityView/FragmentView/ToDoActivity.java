package com.example.administrator.test2.ActivityView.FragmentView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.test2.ActivityView.FragmentControlActivity;
import com.example.administrator.test2.DBComponent.Entity.Todo_Data;
import com.example.administrator.test2.DBComponent.TodoDao;
import com.example.administrator.test2.R;
import com.example.administrator.test2.Unit.ListAdaspter;
import com.example.administrator.test2.Unit.ListViewAdapterContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoActivity extends Fragment {

     private EditText addContent;
    ListView listView;
    TextView info;
    ImageView addDdl;
    private ListViewAdapterContent adapter;
    private List<Map<String, Object>> datalist=new ArrayList<Map<String,Object>>();
    public static TodoDao todoDao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_to_do, container, false);

        todoDao =new TodoDao( getContext());   //数据库
        datalist = getData();
        listView=view.findViewById(R.id.toDoListView);
        adapter = new ListViewAdapterContent(getContext(), datalist);

        //初始化待办列表
        View addView = getLayoutInflater().inflate(R.layout.content_item_add, null);
        addContent = addView.findViewById(R.id.addcontent);
        addDdl=addView.findViewById(R.id.addDdl);

        listView.addFooterView(addView, null, false);
        listView.setAdapter(adapter);

        //添加待办
        addDdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo_Data todo_data=new Todo_Data();
                todo_data.setIsFinish(0);
                todo_data.setTodoContent(addContent.getText().toString());
                todoDao.insertDate(todo_data);
                datalist.clear();
                datalist=getData();
                adapter=new ListViewAdapterContent(getContext(),datalist);
                listView.setAdapter(adapter);
                addContent.setText("");
            }
        });
        //删除待办
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String ids=datalist.get(position).get("id").toString();
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("删除");//对话框标题
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            todoDao.deleteData(ids);
                            datalist.clear();
                            datalist=getData();
                            adapter=new ListViewAdapterContent(getContext(),datalist);
                            listView.setAdapter(adapter);
                        }catch (Exception e){
                            FragmentControlActivity.toastUtil.ShowToast("闪退");
                        }

                    }
                }); //确定按钮，点击事件和取消按钮一样
                builder.show();
                return true;

            }
        });


        return view;

    }
    public List<Map<String, Object>> getData() {
        return todoDao.query();
    }
    public  static Context getContexts(){
        return  getContexts();
    }
    //刷新listview
    public void refresh() {
        //刷新list
        datalist.clear();
//        datalist.addAll(getData());
        adapter.notifyDataSetChanged();
    }

    public  static void refresh(int flag,int id){

    }
}
