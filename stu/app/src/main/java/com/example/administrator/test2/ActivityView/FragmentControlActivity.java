package com.example.administrator.test2.ActivityView;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.administrator.test2.ActivityView.FragmentView.ListNoteActivity;
import com.example.administrator.test2.ActivityView.FragmentView.Main2Activity;
import com.example.administrator.test2.ActivityView.FragmentView.Main3Activity;
import com.example.administrator.test2.ActivityView.FragmentView.ToDoActivity;
import com.example.administrator.test2.DBComponent.NoteDao;
import com.example.administrator.test2.DBComponent.TodoDao;
import com.example.administrator.test2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.example.administrator.test2.Unit.BatteryReceiver;
import com.example.administrator.test2.Unit.ToastUtil;

public class FragmentControlActivity extends AppCompatActivity  implements View.OnClickListener {

    public static ToastUtil toastUtil;
    private Unbinder unbinder;
    @BindView(R.id.bt1)
    ImageButton button1;
    @BindView(R.id.bt2)
    ImageButton button2;
    @BindView(R.id.notebook1)
    ImageButton button3;
    public static NoteDao noteDao;
    private BatteryReceiver batteryReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main4);


        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        replaceFragment(new ListNoteActivity());
        toastUtil = new ToastUtil(this);
        noteDao =new NoteDao(FragmentControlActivity.this);   //数据库

//        batteryReceiver = new BatteryReceiver();
//        // 注册广播接收器来监听电量变化
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        registerReceiver(batteryReceiver, filter);
    }
    @Override
    public void onClick(View view) {   //点击事件
        switch (view.getId()) {
            case R.id.bt1:
                replaceFragment(new Main2Activity());
                break;
            case R.id.bt2:  replaceFragment(new ToDoActivity());
                break;
            case R.id.notebook1:  replaceFragment(new ListNoteActivity());
                break;
        }
    }
    private void replaceFragment(Fragment fragment) {
        //获取Fragment的管理器类 FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //获取 FragmentManager中用于 Fragment 替换之类的类 FragmentTransaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //使用Bundle和Fragment通信
        Bundle bundle=new Bundle();
        //bundle.putString("message","Use bundle  connect  Activity with  Fragment "+fragment.getClass().getSimpleName());
        fragment.setArguments(bundle);
        //创建一个替换Fragment的事件
        transaction.replace(R.id.framelayout_1,fragment);   // 替换的是framelayout_1中的fragment
        //将新的Fragment对象压入一个栈内，点击back会进行回退，而非退出app
       // transaction.addToBackStack(null);
        //提交事件
        transaction.commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();//视图销毁时必须解绑
    }
}
