package com.example.administrator.test2.EX3;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class Sms_Xml {
    //将短信保存在sd卡下的mes.xml文件下
    public static void beifen_sms(List<SmsInfo> list, Context context,File file){
        try{
            Log.d("backupSMS", "start beifen_sms");
            XmlSerializer serial= Xml.newSerializer();
            FileOutputStream fi_out=new FileOutputStream(file);
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
            Toast.makeText(context,"短信备份成功",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context,"短信备份失败",Toast.LENGTH_SHORT).show();
        }
    }
}

