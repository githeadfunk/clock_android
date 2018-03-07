package com.example.jwang5.clock_android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jwang5.bean.Clock_list_adapter;
import com.example.jwang5.bean.MusicCtrl;
import com.example.jwang5.bean.clock_bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class Home extends AppCompatActivity {

	private PendingIntent pendingIntent;
	private ArrayList<clock_bean> clockList;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("123", "home onCreate: ");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		this.listView = findViewById(R.id.mainListView);

		ClockListService cls = ClockListService.getInstance(this);
		this.clockList = cls.getClockList();
		if(this.clockList != null && this.clockList.size() > 0){

			//setting list view
			Clock_list_adapter adapter = new Clock_list_adapter(this, this.clockList);
			this.listView.setAdapter(adapter);

			final Context context = this;
			this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					Intent intent = new Intent(context, clockDetail.class);
					Bundle b = new Bundle();
					b.putInt("id", position + 1);
					intent.putExtras(b);
					startActivity(intent);

				}
			});
		}

		this.alarmGoesoff();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.add:
				Bundle b = new Bundle();
				b.putInt("id", 0); //Your id
				Intent intent = new Intent(this, clockDetail.class);
				intent.putExtras(b);
				startActivity(intent);
				return true;
			case R.id.delete:
				deleteAll();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void deleteAll(){
		SharedPreferences myref = getSharedPreferences("myAppName", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = myref.edit();
		editor.putString("clockList", "");
		editor.commit();
		this.listView.setAdapter(null);
	}

	public void alarmGoesoff() {
    AlarmGoOffService alarmService = new AlarmGoOffService(this);

		if(this.clockList.size() > 0){
			for(int i = 0; i < this.clockList.size(); i++){
        clock_bean clock = this.clockList.get(i);
				if(clock.isActive()) alarmService.goOff(clock);
				else alarmService.cancelAlarm(clock);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		MusicCtrl mc = MusicCtrl.getInstance(this);
		mc.stopMusic();
	}

}