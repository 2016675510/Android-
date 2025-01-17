package com.example.administrator.test2.Unit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test2.ActivityView.FragmentView.ToDoActivity;
import com.example.administrator.test2.DBComponent.Entity.Todo_Data;
import com.example.administrator.test2.R;

import java.util.List;
import java.util.Map;

public class ListViewAdapterContent extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListViewAdapterContent(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Zujian {
        public ImageView image;
        public TextView title;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Zujian zujian;
        if (convertView == null) {
            zujian = new Zujian();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.contect_list_item, null);
            zujian.image = (ImageView) convertView.findViewById(R.id.image);
            zujian.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        //绑定数据
        zujian.image.setImageResource((int) data.get(position).get("image"));
        zujian.title.setText((String) data.get(position).get("title"));
        int status = (int) data.get(position).get("status");
        if (status == 1) {
            zujian.title.setTextColor(Color.parseColor("#c4c4c4"));
            zujian.title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            zujian.title.getPaint().setAntiAlias(true);
        } else if (status == 0) {
            zujian.title.setTextColor(Color.parseColor("#454545"));
            zujian.title.getPaint().setFlags(0);
            zujian.title.getPaint().setAntiAlias(true);
        }
        final int p=position;

        zujian.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag= (int) data.get(p).get("status");
                int id= (int) data.get(p).get("id");
                if(flag==0) {
                    zujian.image.setImageResource( R.drawable.ic_ischeck);
                    zujian.title.setTextColor(Color.parseColor("#c4c4c4"));
                    zujian.title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    zujian.title.getPaint().setAntiAlias(true);
                    ToDoActivity.todoDao.updateData(id,1);
                }else{
                    zujian.image.setImageResource( R.drawable.ic_isnotcheck);
                    zujian.title.setTextColor(Color.parseColor("#454545"));
                    zujian.title.getPaint().setFlags(0);
                    zujian.title.getPaint().setAntiAlias(true);
                    ToDoActivity.todoDao.updateData(id,0);
                }
            }
        });
        return convertView;
    }

}

