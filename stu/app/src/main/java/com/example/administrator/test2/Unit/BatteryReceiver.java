package com.example.administrator.test2.Unit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

import com.example.administrator.test2.DBComponent.TimeService;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPercentage = batteryLevel * 100.0f / batteryScale;
        if (batteryPercentage <= 100) {
            Toast.makeText(context, "电量低，请及时充电！", Toast.LENGTH_SHORT).show();
        }
    }
}