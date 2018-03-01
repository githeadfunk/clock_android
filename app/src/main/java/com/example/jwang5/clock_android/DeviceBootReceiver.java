package com.example.jwang5.clock_android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.jwang5.bean.clock_bean;

import java.util.ArrayList;
import java.util.Calendar;

public class DeviceBootReceiver extends BroadcastReceiver {

	private PendingIntent pendingIntent;
	private ArrayList<clock_bean> clockList;

	@Override
	public void onReceive(Context context, Intent intent) {
//		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//			Log.w("123213", "reboot" );
//			myAlert alert = new myAlert("reboot", context);
//			alert.onCreateDialog();
//
//			ClockListService cls = ClockListService.getInstance(context);
//			this.clockList = cls.getClockList();
//
//			Intent alarmIntent = new Intent(context, AlarmReceiver.class);
//			alarmIntent.putExtra("musicUri", this.clockList.get(0).getMusciURL());
//			pendingIntent = PendingIntent.getBroadcast(context, this.clockList.get(0).getId(), alarmIntent, 0);
//
//			AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//			Calendar calendar = Calendar.getInstance();
//			calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(this.clockList.get(0).getTime().split(":")[0]));
//			calendar.set(Calendar.MINUTE, Integer.parseInt(this.clockList.get(0).getTime().split(":")[1]));
//			calendar.set(Calendar.SECOND, 0);
//			manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//
//		}
	}
}