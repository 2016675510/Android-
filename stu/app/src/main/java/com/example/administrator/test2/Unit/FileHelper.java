package com.example.administrator.test2.Unit;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {

    public static boolean createTextFile(Context context, String fileName, String content) {
        FileOutputStream fos = null;
        try {
            // 获取应用的内部存储目录
            File file = new File(context.getFilesDir(), fileName);

            // 创建文件输出流，将内容写入文件
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        }
    }
    public static String readTextFile(Context context, String fileName) {
        FileInputStream fis = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            // 获取文件路径
            File file = new File(context.getFilesDir(), fileName);

            // 创建文件输入流和缓冲读取器
            fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            // 逐行读取文件内容
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }
    }
}
