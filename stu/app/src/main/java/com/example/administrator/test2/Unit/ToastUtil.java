package com.example.administrator.test2.Unit;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private Context context;

    public ToastUtil(Context context) {
        this.context = context;
    }

    public void ShowToast(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.show();
    }
}
