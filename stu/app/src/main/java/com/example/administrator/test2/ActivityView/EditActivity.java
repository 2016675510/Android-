package com.example.administrator.test2.ActivityView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test2.DBComponent.Entity.Note_Data;
import com.example.administrator.test2.DBComponent.NoteDao;
import com.example.administrator.test2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @BindView(R.id.timeYear)
    TextView timeYear;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.notepad_content)
    EditText noteContent;
    @BindView(R.id.noteTitle)
    EditText noteTitle;
    @BindView(R.id.num)
    TextView content_num;
    NoteDao noteDao;
    int ids;
    Note_Data note_data=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);

        time.setText(NoteDao.showTime());
        timeYear.setText(NoteDao.showTimeYear());
        noteDao=new NoteDao(EditActivity.this);
        //初始化
        Intent intent=this.getIntent();
        ids= intent.getIntExtra("ids",0);
        if(ids!=0){
            note_data=noteDao.query(ids);
            timeYear.setText(note_data.getUserTimeYear());
            time.setText(note_data.getUserTime());
            noteTitle.setText(note_data.getUserTitle());
            noteContent.setText(note_data.getUserContent());
         }
        content_num.setText(String.valueOf(noteContent.length())+"字");
        noteContent.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable s) {
                 content_num.setText(String.valueOf(noteContent.length())+"字");
             }
         });
    }
    @OnClick({R.id.delete,R.id.note_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_add:
                Note_Data note = new Note_Data();
                note.setUserTimeYear(timeYear.getText().toString());
                note.setUserTime(time.getText().toString());
                note.setUserTitle(noteTitle.getText().toString());
                note.setUserContent(noteContent.getText().toString());
                //ids不等于0时代表从界面上传过来的，进行修改操作
                if (ids!=0){
                    noteDao.updateData(ids,note.getUserContent(),note.getUserTimeYear(),note.getUserTime(),note.getUserTitle());
                    FragmentControlActivity.toastUtil.ShowToast("修改成功");
                    refresh();
                }else {

                    if (noteDao.insertDate(note)) {
                        FragmentControlActivity.toastUtil.ShowToast("保存成功");
                        refresh();
                    } else {
                        FragmentControlActivity.toastUtil.ShowToast("保存失败");
                    }
                }
                break;
            case R.id.delete:
                noteContent.setText("");
                noteTitle.setText("");
                FragmentControlActivity.toastUtil.ShowToast("清空内容");
                break;
        }
    }
    private  void refresh(){
        finish();
        Intent intent=new Intent(EditActivity.this,FragmentControlActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();//视图销毁时必须解绑
    }
}
