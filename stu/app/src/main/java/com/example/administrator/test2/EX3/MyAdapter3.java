package com.example.administrator.test2.EX3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test2.R;

import java.util.List;
import java.util.Map;

public class MyAdapter3 extends BaseAdapter {
    private List <Map<String ,Object>> datas;
    private  List imagelist;
    private Context context;
    private LayoutInflater myInfalater;

    public MyAdapter3(List<Map<String, Object>> datas, Context context) {
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
        final ViewHolder3 holderder;
        if (convertView==null){
            convertView= myInfalater.from(context).inflate(R.layout.listview_layout3,null);
            holderder=new ViewHolder3();
            holderder.bookName=(TextView) convertView.findViewById(R.id.bookName);
            holderder.writer=(TextView) convertView.findViewById(R.id.writer);
            holderder.brief=(TextView) convertView.findViewById(R.id.brief);
            holderder.imageView=(ImageView) convertView.findViewById(R.id.imageView);
            holderder.button=(Button) convertView.findViewById(R.id.button);
            holderder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holderder.bookName.getText().toString().equals("活着")){
                        Uri webpage=Uri.parse("http://www.xinhuapub.com/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                             context.startActivity(intent);
                    }
                 }
            });
            convertView.setTag(holderder);
        }else {
            holderder=(ViewHolder3) convertView.getTag();
        }
        holderder.imageView.setImageResource((Integer) datas.get(position).get("img"));
        holderder.bookName.setText(datas.get(position).get("bookName").toString());
        holderder.brief.setText(datas.get(position).get("brief").toString());
        holderder.writer.setText(datas.get(position).get("writer").toString());
        holderder.button.setText(datas.get(position).get("button").toString());
        return convertView;
    }

    static    class ViewHolder3{
        TextView bookName,brief,writer;
        ImageView imageView;
        Button button;
    }
}
