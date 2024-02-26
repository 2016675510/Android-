package com.example.administrator.test2.Unit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test2.R;

import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private List <Map<String ,Object>> datas;
    private  List imagelist;
    private Context context;
    private LayoutInflater myInfalater;

    public MyAdapter(List<Map<String, Object>> datas, Context context) {
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
        final  ViewHolder holderder;
        if (convertView==null){
            convertView= myInfalater.from(context).inflate(R.layout.list_layout,null);
            holderder=new  ViewHolder();
            holderder.textView=(TextView) convertView.findViewById(R.id.textView);
            holderder.imageView=(TextView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holderder);
        }else {
            holderder=(ViewHolder) convertView.getTag();
        }
        if (datas != null && position >= 0 && position < datas.size()) {
            Object titleObject = datas.get(position).get("name");
            Object timeObject = datas.get(position).get("phone");
            if (titleObject != null) {
                holderder.imageView.setText(titleObject.toString());
            } else {
                // 处理 titleObject 为 null 的情况
            }
            if (timeObject!=null){
                holderder.textView.setText(timeObject.toString());
            }else{}


        }

         return convertView;
    }
  static    class ViewHolder{
        TextView textView;
      TextView imageView;
     }
}
