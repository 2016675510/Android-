package com.example.administrator.test2.ActivityView.FragmentView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.test2.ActivityView.FragmentControlActivity;
import com.example.administrator.test2.EX3.Contact;
import com.example.administrator.test2.EX3.SmsInfo;
import com.example.administrator.test2.EX3.Sms_Xml;
import com.example.administrator.test2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.test2.Unit.MyAdapter;

import org.xmlpull.v1.XmlSerializer;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;

public class Main2Activity extends Fragment {

    private String[] data = { "软工21101",
            "软工21102","软工21103","软工21104",
             };
    private CheckBox checkboxSports;
    private CheckBox checkboxMusic;
    private CheckBox checkboxReading;

    private RadioGroup radioGroupGender;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private MyAdapter myAdapter;
    private List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>(); //存放显示在listview上的键值对
    private ListView listView;
    List<Contact> contactsList = new ArrayList<>();
    Button getInfo,backUp;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);
        InitData(view);
        //接收Activity数据
        Bundle argument=getArguments();
        String str=argument.getString("message");
        Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
        listView= view.findViewById(R.id.listview_hobbies);
        myAdapter=new MyAdapter(datas,getContext());
        listView.setAdapter(myAdapter);
        // 处理checkbox选择事件
        checkboxSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 处理选择状态改变
                if (isChecked) {
                    Toast.makeText(getContext(),"this is 运动",Toast.LENGTH_LONG).show();
                    // checkbox被选中
                } else {
                    // checkbox被取消选中
                }
            }
        });
        checkboxMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 处理选择状态改变
                if (isChecked) {
                    Toast.makeText(getContext(),"this is 音乐",Toast.LENGTH_LONG).show();

                    // checkbox被选中
                } else {
                    // checkbox被取消选中
                }
            }
        });
        checkboxReading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 处理选择状态改变
                if (isChecked) {
                    Toast.makeText(getContext(),"this is 阅读",Toast.LENGTH_LONG).show();
                    // checkbox被选中
                } else {
                    // checkbox被取消选中
                }
            }
        });

        // 处理radiobutton选择事件
        radioGroupGender.setOnCheckedChangeListener  ( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 处理选择状态改变
                if (checkedId == R.id.radio_male) {
                    Toast.makeText(getContext(),"this is male",Toast.LENGTH_LONG).show();
                    // 男性被选中
                } else if (checkedId == R.id.radio_female) {
                    // 女性被选中
                    Toast.makeText(getContext(),"this is female",Toast.LENGTH_LONG).show();
                }
            }
        });
        backUp=view.findViewById(R.id.backUP);
        backUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSMSCheck();
            }
        });
        getInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsList= getAllContacts();
                datas.clear();
                for (Contact contact  :contactsList){
                    FragmentControlActivity.toastUtil.ShowToast(":"+contact.getName()+contact.getNumber());
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("name", contact.getName());
                    map.put("phone",contact.getNumber());
                    datas.add(map);
                }
                myAdapter=new MyAdapter(datas,getContext());
                listView.setAdapter(myAdapter);
            }
        });
        return view;
    }

    private  void InitData(View view){
        // 初始化控件
        checkboxSports = view.findViewById(R.id.checkbox_sports);
        checkboxMusic = view.findViewById(R.id.checkbox_music);
        checkboxReading = view.findViewById(R.id.checkbox_reading);
        radioGroupGender = view.findViewById(R.id.radio_group_gender);
        radioMale = view.findViewById(R.id.radio_male);
        radioFemale = view.findViewById(R.id.radio_female);
        listView = view.findViewById(R.id.listview_hobbies);
        getInfo=view.findViewById(R.id.getInfo);

        //初始化列表
        contactsList= getAllContacts();
        datas.clear();
        for (Contact contact  :contactsList){
            FragmentControlActivity.toastUtil.ShowToast(":"+contact.getName()+contact.getNumber());
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("name", contact.getName());
            map.put("phone",contact.getNumber());
            datas.add(map);
        }
        myAdapter=new MyAdapter(datas,getContext());
        listView.setAdapter(myAdapter);
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactsList = new ArrayList<>();
        ContentResolver contentResolver =getContext().getContentResolver();
        try{
            ContentResolver cr = getContext().getContentResolver();
            // 取得电话本中开始一项的光标,主要就是查询"contacts"表
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    // 获取联系人电话号码
                    if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor phoneCursor = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{contactId},
                                null
                        );
                        if (phoneCursor != null) {
                            while (phoneCursor.moveToNext()) {
                                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                // 处理电话号码
                                Contact contact=new Contact(name,phoneNumber);
                                contactsList.add(contact);
                            }
                            phoneCursor.close();
                        }
                    }
                    // 处理联系人姓名及其他信息
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        }

        catch(Exception e){
        }
        return contactsList;
    }

    private void getSMSCheck() {
        Log.d(TAG, "getSMSCheck() " );
        //判断是否有权限
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(,new String[]{Manifest.permission.READ_SMS},201);
        }else{
            backSMS();
        }
    }

    private void backSMS() {
        Log.d(TAG, "start backSMS() " );
        //content://sms查询所有短信的uri
        Uri uri= Uri.parse("content://sms/");
        //获取ContentResolver对象
        ContentResolver contentResolver=getContext().getContentResolver();
        Log.d(TAG, "contentResolver= "+contentResolver );
        //通过ContentResolver对象查询系统短信
        Cursor cursor=contentResolver.query(uri,new String[]{"address","date",
                "type","body"},null,null,null);

        Log.d(TAG, "cursor="+cursor);
        List<SmsInfo> list=new ArrayList<SmsInfo>();
        while (cursor.moveToNext()){
            String address=cursor.getString(0);
            Log.d(TAG, "address="+address);
            long date=cursor.getLong(1);
            int type=cursor.getInt(2);
            String body=cursor.getString(3);
            SmsInfo smsInfo=new SmsInfo(address,date,type,body);
            list.add(smsInfo);
        }
        cursor.close();
        beifen_sms(list,getContext());
    }

    public void beifen_sms(List<SmsInfo> list, Context context){
        try{
            Log.d("backupSMS", "start beifen_sms");
            XmlSerializer serial= Xml.newSerializer();
            FileOutputStream fi_out=getContext().openFileOutput("mes.xml",MODE_PRIVATE);
            //初始化序列号器，指定xml数据写入到哪个文件以及编码
            serial.setOutput(fi_out,"utf-8");
            serial.startDocument("utf-8",true);
            //根节点
            serial.startTag(null,"smss");
            for (SmsInfo info : list){
                //构建父节点
                serial.startTag(null,"sms");
                serial.attribute(null,"id",info.getId()+"");
                //body部分
                serial.startTag(null,"body");
                serial.text(info.getBody());
                serial.endTag(null,"body");
                //address部分
                serial.startTag(null,"address");
                serial.text(info.getAddress());
                serial.endTag(null,"address");
                //type部分
                serial.startTag(null,"type");
                serial.text(info.getType()+"");
                serial.endTag(null,"type");
                //date部分
                serial.startTag(null,"date");
                serial.text(info.getDate()+"");
                serial.endTag(null,"date");
                //父节点结束
                serial.endTag(null,"sms");
            }
            serial.endTag(null,"smss");
            serial.endDocument();
            fi_out.close();
            FragmentControlActivity.toastUtil.ShowToast("短信备份成功");
//            Toast.makeText(context,"短信备份成功",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            FragmentControlActivity.toastUtil.ShowToast("短信备份失败");
//            Toast.makeText(context,"短信备份失败",Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==201){
            backSMS();
        }else{
            return;
        }
    }
}
