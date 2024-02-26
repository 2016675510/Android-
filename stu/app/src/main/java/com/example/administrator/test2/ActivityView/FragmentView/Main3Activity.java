package com.example.administrator.test2.ActivityView.FragmentView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.test2.ActivityView.FragmentControlActivity;
import com.example.administrator.test2.EX3.MyAdapter3;
import com.example.administrator.test2.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Main3Activity extends Fragment {
    private AutoCompleteTextView AutoText;
    private ArrayAdapter arrayAdapter;
    private String[] data = new String[]{"平凡的世界", "活着", "许三观卖血记", "朝花夕拾"};
    private Button getAutoText,getSpinnerText;

    private MyAdapter3 myAdapter;
    private List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>(); //存放显示在listview上的键值对
    private ListView listView;
    private Spinner spinner;
    private String[] stringArray = {"路遥","余华","老舍","史铁生","莫言"};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main3, container, false);
        init(view);
        getAutoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),AutoText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        //接收Activity数据
        Bundle argument=getArguments();
        String str=argument.getString("message");

        //按钮获取作者信息，并弹框跳转至百度搜索相关信息
        getSpinnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("作者信息");//对话框标题
                builder.setMessage("可能会跳转到网页");//提示文本
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str=spinner.getSelectedItem().toString();
                        Uri webpage=null;
                        try{
                            webpage  =Uri.parse("http://www.baidu.com/s?&ie=utf-8&oe=UTF-8&wd=" + URLEncoder.encode(str,"UTF-8"));
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        startActivity(intent);
                    }
                }); //确定按钮，点击事件和取消按钮一样
                builder.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                FragmentControlActivity.toastUtil.ShowToast("这是一个toast");
                String str=datas.get(position).get("bookName").toString();
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("书籍信息网");//对话框标题
                builder.setMessage("跳转到《"+str+"》网页");//提示文本
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str=datas.get(position).get("bookName").toString();
                        Uri webpage=null;
                        try{
                            webpage  =Uri.parse("http://www.baidu.com/s?&ie=utf-8&oe=UTF-8&wd=" + URLEncoder.encode(str,"UTF-8"));
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        startActivity(intent);
                    }
                }); //确定按钮，点击事件和取消按钮一样
                builder.show();
            }
        });
        return view;
    }




    private  void init(View view){
        listView=view.findViewById(R.id.ex3_list_view);
        AutoText =view.findViewById(R.id.autoText);
        getAutoText=view.findViewById(R.id.getAutoText);
        getSpinnerText=view.findViewById(R.id.getSpinnerText);
        spinner=view.findViewById(R.id.writer_spinner);
//        AutoCompleteTextView 适配器
        arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item,data );
        AutoText.setAdapter(arrayAdapter);
        //spinner数组适配器
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, stringArray);
        spinner.setAdapter(adapter);
        //listView适配器
        myAdapter=new MyAdapter3(datas,getContext());
        listView.setAdapter(myAdapter);
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> map1=new HashMap<String, Object>();
        Map<String,Object> map2=new HashMap<String, Object>();
        Map<String,Object> map3=new HashMap<String, Object>();
  
        map1.put("img",R.drawable.yuhua);
        map1.put("bookName","活着");
        map1.put("brief","《活着》是中国当代作家余华创作的长篇虚构小说，首次发表于《收获》1992年第6期。" );
         map1.put("writer","余华");
        map1.put("button","人民出版社");
        datas.add(map1);

        map2.put("img",R.drawable.shities);
        map2.put("bookName","务虚笔记");
        map2.put("brief","《务虚笔记》是2010年中国工人出版社出版的图书，史铁生的首部长篇小说，发表于1996年《收获》杂志上，同时也是他半自传式的作品。" );
        map2.put("writer","史铁生");
        map2.put("button","工业出版社");
        datas.add(map2);

        map3.put("img",R.drawable.laoshe);
        map3.put("bookName","骆驼样子");
        map3.put("brief"," 《骆驼祥子》是作家老舍所著的长篇小说，于1936年，在《宇宙风》杂志25-48期上连载，1939年由上海人间书屋出版单行本。" );
        map3.put("writer","老舍");
        map3.put("button","工业出版社");
        datas.add(map3);

    }

}
