package com.example.administrator.test2.Unit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.test2.R;

import java.util.List;
import java.util.Map;

import com.example.administrator.test2.EX3.MyAdapter3;

public class ListAdaspter extends BaseAdapter {
    private List<Map<String ,Object>> datas;
    private  List imagelist;
    private Context context;
    private LayoutInflater myInfalater;

    public ListAdaspter(List<Map<String, Object>> datas, Context context) {
        this.datas = datas;
        this.context = context;
        myInfalater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ListAdaspter.ViewHolder3 holderder;
        if (convertView==null){
            convertView= myInfalater.from(context).inflate(R.layout.list_adapter_layout,null);
            holderder=new ListAdaspter.ViewHolder3();
            holderder.list_time=(TextView) convertView.findViewById(R.id.time_note_list);
            holderder.list_title=(TextView) convertView.findViewById(R.id.title_note_list);
            holderder.list_content=(TextView) convertView.findViewById(R.id.content_note_list);
          /*  holderder.delete_note=(Button) convertView.findViewById(R.id.delete_note_list);
            holderder.delete_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除操作

                }
            });*/
            convertView.setTag(holderder);
        }else {
            holderder=(ListAdaspter.ViewHolder3) convertView.getTag();
        }
        if (datas != null && position >= 0 && position < datas.size()) {
            Object titleObject = datas.get(position).get("title");
            Object timeObject = datas.get(position).get("time");
            Object contentObject = datas.get(position).get("content");
//            Object deleteObject = datas.get(position).get("delete");
            if (titleObject != null) {
                holderder.list_title.setText(titleObject.toString());
            } else {
                // 处理 titleObject 为 null 的情况
            }
            if (timeObject!=null){
                holderder.list_time.setText(timeObject.toString());
            }else{}
            if (contentObject!=null){
                holderder.list_content.setText(contentObject.toString());
            }else{}
           /* if (deleteObject!=null){
                holderder.delete_note.setText(deleteObject.toString());
            }else{}*/
        }


        return convertView;
    }

    static    class ViewHolder3{
        TextView list_title,list_content,list_time;
//        Button delete_note;
    }
}
