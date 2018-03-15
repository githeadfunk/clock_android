package com.example.jwang5.clock_android;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jwang5.bean.Clock_list_adapter;
import com.example.jwang5.bean.MusicCtrl;
import com.example.jwang5.bean.clock_bean;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

	private ArrayList<clock_bean> clockList;
	private ListView listView;
	private ClockListService cls = ClockListService.getInstance(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("123", "home onCreate: ");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		final Window win= getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		this.listView = findViewById(R.id.mainListView);

		this.clockList = this.cls.getClockList();
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
		Boolean fired = false;
		Bundle b = getIntent().getExtras();
		if(b != null){
			fired = b.getBoolean("fire");
			Log.w("fire", "fired" + fired );
		}

		if(fired == true){
			MusicCtrl mc = MusicCtrl.getInstance(this);
			mc.playMusic(this, b.getString("musicUri"), b.getInt("volume"));
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
		this.cls.deleteAll();
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