package com.example.administrator.test2.ActivityView.FragmentView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.administrator.test2.ActivityView.EditActivity;
import com.example.administrator.test2.ActivityView.FragmentControlActivity;
import com.example.administrator.test2.DBComponent.Entity.Note_Data;
import com.example.administrator.test2.DBComponent.NoteDao;
import com.example.administrator.test2.EX3.MyAdapter3;
import com.example.administrator.test2.R;
import com.example.administrator.test2.Unit.ListAdaspter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ListNoteActivity extends Fragment {

     ListView listViewBook;
    private Unbinder unbinder;
    @BindView(R.id.addNote)
    ImageButton addnot;
    ListAdapter listAdapter;
    List<Note_Data> list_data=new ArrayList<>();
    public static List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>(); //存放显示在listview上的键值对

    public  String id_str="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  @Nullable ViewGroup container,   @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listbook, container, false);
        listViewBook=(view).findViewById(R.id.listViewBook);
        unbinder = ButterKnife.bind(this, view);
        list_data.clear();
        datas.clear();
        list_data= FragmentControlActivity.noteDao.query();
//        dataConverte(list_data);
        listAdapter=new ListAdaspter(datas,getContext());
        listViewBook.setAdapter(listAdapter);
        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ids=Integer.parseInt(datas.get(position).get("id").toString());
                Intent  intent =new Intent(getContext(),EditActivity.class);
                intent.putExtra("ids",ids);
                startActivity(intent);
             }
        });
        //删除待办
        listViewBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String ids=datas.get(position).get("id").toString();
                FragmentControlActivity.noteDao.deleteData(ids);
                id_str=ids;
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("删除");//对话框标题
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            FragmentControlActivity.noteDao.deleteData(id_str);
                            FragmentControlActivity.toastUtil.ShowToast("删除成功");
                            datas.clear();
                            FragmentControlActivity.noteDao.query();
                            listAdapter=new ListAdaspter(datas,getContext());
                            listViewBook.setAdapter(listAdapter);
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

  private void dataConverte( List<Note_Data> list_data){
      for (Note_Data note_data : list_data) {
          Map<String,Object> map=new HashMap<String, Object>();
            map.put("content",note_data.getUserContent());
            map.put("title",note_data.getUserTitle());
            map.put("time",note_data.getUserTime());
            map.put("delete","删除");
            datas.add(map);
          }
  }
    @OnClick(R.id.addNote)
    public void onViewClicked() {
        Intent intent=new Intent(getActivity(), EditActivity.class);
        startActivity(intent);
     }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//视图销毁时必须解绑
    }
}
