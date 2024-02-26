package com.example.administrator.test2.ActivityView.FragmentView;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.test2.R;

import com.example.administrator.test2.Unit.FileHelper;

import static android.content.ContentValues.TAG;

public class NoteTxtActivity extends Fragment {

    private Button  saveTxt,loadBook;
    private EditText editBook;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_note_book, container, false);
        init(view);
        saveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "example.txt";
                String fileContent = "这是要写入文件的内容。";

                boolean fileCreated = FileHelper.createTextFile(getContext(), fileName, editBook.getText().toString());
                if (fileCreated) {
                    Toast.makeText(getContext(),"保存成功",Toast.LENGTH_LONG).show();

                    // 文件创建成功
                } else {
                    // 文件创建失败
                    Toast.makeText(getContext(),"保存失败",Toast.LENGTH_LONG).show();

                }
             }
        });
        loadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "example.txt";
                String fileContent = FileHelper.readTextFile(getContext(), fileName);

                if (fileContent != null) {
                    editBook.setText(fileContent);
                    Toast.makeText(getContext(),"文件读取成功",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(),"文件读取失败",Toast.LENGTH_LONG).show();
                    // 文件读取失败
                }
            }
        });
        return view;
    }
    private  void init(View view){
        saveTxt=view.findViewById(R.id.saveBook);
        editBook=view.findViewById(R.id.editBook);
        loadBook=view.findViewById(R.id.loaddBook);
    }



    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: 获取到外部存储权限");
               // operateExternal();
            } else {
                // 没有弹框，直接就拒绝了
                Log.d(TAG, "onRequestPermissionsResult: 用户拒绝外部存储权限");
            }
        }
    }



}
