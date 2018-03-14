package com.example.jwang5.clock_android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jwang5.bean.clock_bean;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jwang5 on 2/23/2018.
 */

public class ClockListService {

	private SharedPreferences myref;
	private Context context;
	private static ClockListService instance;
	private String clockListStr;
	private ArrayList<clock_bean> clockList;

	public ClockListService(Context context) {
		this.context = context;
	}

	public static ClockListService getInstance(Context context) {
		if (instance == null) {
			instance = new ClockListService(context);
		}
		return instance;
	}

	public void setClockList(ArrayList<clock_bean> cl){
		this.clockList = cl;
		SharedPreferences.Editor editor = this.myref.edit();
		try{
			String clockListStr = ObjectSerializer.serialize(this.clockList);
			editor.putString("clockList", clockListStr);
			editor.commit();
		}catch(IOException e){
		}
	}

	public ArrayList<clock_bean> getClockList(){
		this.clockList = new ArrayList<clock_bean>();
		this.myref = this.context.getSharedPreferences("myAppName", Context.MODE_PRIVATE);
		this.clockListStr = myref.getString("clockList","");
		if(!this.clockListStr.isEmpty()) {
			try {
				this.clockList = (ArrayList<clock_bean>) ObjectSerializer.deserialize(this.clockListStr);
			} catch (IOException e) {

			}
		}
		return this.clockList;
	}

	public void toggleActive(int pos, boolean isActive){

		this.clockList = getClockList();
		if(this.clockList.get(pos).isActive() != isActive){

			this.clockList.get(pos).setActive(isActive);
			setClockList(this.clockList);

			AlarmGoOffService alarmService = new AlarmGoOffService(this.context);
      if(isActive) alarmService.goOff(this.clockList.get(pos));
      else alarmService.cancelAlarm(this.clockList.get(pos));
		}
	}

	public void deleteAll(){
		//cancel all alarms
		AlarmGoOffService alarmService = new AlarmGoOffService(this.context);
		this.clockList = getClockList();
		for(int i = 0; i < this.clockList.size(); i++){
			alarmService.cancelAlarm(this.clockList.get(i));
		}
		//delete all alarms
		myref = this.context.getSharedPreferences("myAppName", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = myref.edit();
		editor.putString("clockList", "");
		editor.commit();

	}

}
