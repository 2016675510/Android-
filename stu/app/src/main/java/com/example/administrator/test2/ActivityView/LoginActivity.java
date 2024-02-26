package com.example.administrator.test2.ActivityView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test2.DBComponent.TimeService;
import com.example.administrator.test2.R;

import com.example.administrator.test2.DBComponent.Entity.User_Data;
import com.example.administrator.test2.DBComponent.UserDao;
import com.example.administrator.test2.DBComponent.DBService;
import com.example.administrator.test2.Unit.ToastUtil;

import static android.support.constraint.Constraints.TAG;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button login,register;
    private Spinner spinner;
    public static EditText user,psd;
    private  DBService dbService;
    private UserDao userDao;
    private String[] stringArray = {"安徽省","河北省","河南省","湖北省","湖南省"};
    private RadioButton man,woman;
    private CheckBox saveUser,autoLogin;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    TextView textView;
    TimeService timeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        init();
        checkReadSd();
        //加载用户
        initUser();
        //开启服务
//        Intent timeServiceIntent=new Intent(LoginActivity.this,TimeService.class);
//        startService(timeServiceIntent);

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(autoLogin.isChecked()){
                    saveUser.setChecked(true);
                    sharedPreferences.edit().putBoolean("autoLogin",true).commit();
                }else {
                    sharedPreferences.edit().putBoolean("autoLogin",false).commit();
                }
            }
        });
    }


    private  void initUser(){

        //自动保存密码
        if(sharedPreferences.getBoolean("saveInfo",true)){
            user.setText(sharedPreferences.getString("user",""));
            psd.setText(sharedPreferences.getString("password",""));
        }
        //自动登录
        if(user.getText()!=null&&psd.getText()!=null&&sharedPreferences.getBoolean("autoLogin",true)){
            autoLogin.setChecked(true);
            saveUser.setChecked(true);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            textView.setVisibility(TextView.VISIBLE);
            progressBar.setMax(100);
            // 创建一个 Handler 对象并使用 postDelayed 方法延迟执行跳转逻辑
            final Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                int progress = 0;
                @Override
                public void run() {
                    // 更新进度条的进度
                    progressBar.setProgress(progress);

                    if (progress*10 >= 1500) {
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        textView.setVisibility(TextView.INVISIBLE);

                        // 这里放置延迟后执行的操作，自动登录跳转操作
                        performAutoLogin();
                    }else{
                        progress += 100;
                        handler.postDelayed(this, 100);
                    }
                }
            }, 1500);
        }
    }
    private void performAutoLogin() {
        Intent intent = new Intent(LoginActivity.this, FragmentControlActivity.class);
        startActivity(intent);
     }


    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] permissions1 = {Manifest.permission.READ_CONTACTS};


    private  void init(){
        login=findViewById(R.id.login);
        spinner=findViewById(R.id.spi);
        register=findViewById(R.id.register);
        user=findViewById(R.id.user);
        psd=findViewById(R.id.password);
        saveUser=findViewById(R.id.saveUser);
        autoLogin=findViewById(R.id.autoLogin);
        progressBar=findViewById(R.id.progressBar);
        dbService=new DBService(this);
         textView=findViewById(R.id.textViewProgress);
        userDao =new UserDao(LoginActivity.this);   //数据库


        //创建shareapreference对象存储数据
        sharedPreferences=this.getSharedPreferences("userMessage",MODE_PRIVATE);
        //注册监听事件
        login.setOnClickListener(this);
        register.setOnClickListener(this);
//        //设置数组适配器
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, stringArray);
//        spinner.setAdapter(adapter);
    }
    @Override
    public  void  onClick(View v){
        switch (v.getId())
        {
            case R.id.login:
                LoginFunction();
                break;
            case R.id.register:
                RegisterFunction();

        }
    }



    //登陆方法
    private  void LoginFunction(){
        String userStr=user.getText().toString();
        String  psdStr=psd.getText().toString();

        if(userDao.query_user(user.getText().toString(),psd.getText().toString())==1)
        {
            String user_sp="";
            //如果选中记住密码
                if(saveUser.isChecked()){
                //存入信息
                sharedPreferences.edit().putBoolean("saveInfo",true).commit();   //记录保存标记
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("user",user.getText().toString());
                editor.putString("password",psd.getText().toString());
                editor.commit();
                //取出信息
                  user_sp=sharedPreferences.getString("user","");
                String pass_sp=sharedPreferences.getString("password","");
                Toast.makeText(this, "记住"+user_sp+"用户账号成功！ ", Toast.LENGTH_LONG).show();
            }else{
                sharedPreferences.edit().putBoolean("saveInfo",false).commit();   //记录保存标记
                if (autoLogin.isChecked())
                autoLogin.setChecked(false);
            }
            if (!autoLogin.isChecked())
                sharedPreferences.edit().putBoolean("autoLogin",false).commit();
            Intent intent = new Intent(LoginActivity.this, FragmentControlActivity.class);
            startActivity(intent);

            //  Toast.makeText(this, " login successfully!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "username or password is  incorrect! ", Toast.LENGTH_LONG).show();

         }
    }

    private void RegisterFunction(){
        String password=psd.getText().toString();
        String username=user.getText().toString();
        if(password.trim().length()<8){
            Toast.makeText(LoginActivity.this, "请输入至少8位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(user.getText().toString().trim().length()!=0||psd.getText().toString().trim().length()!=0) {
            if(userDao.query_user(user.getText().toString())==0) {
                User_Data userData_ = new User_Data();
                userData_.setUsername(user.getText().toString());
                userData_.setPassword(psd.getText().toString());
                if(userDao.insert_user(userData_)==-1){
                    Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LoginActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 判断是否需要获取sd卡写权限
     */
    private void checkReadSd() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, permissions, 1);
                // 直接跳转到权限设置界面
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions1[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, permissions1, 1);
                // 直接跳转到权限设置界面
            }
        }


        //判断是否有权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},201);
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("sys:onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("sys:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("sys:onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("sys:onDestroy");
    }
}
