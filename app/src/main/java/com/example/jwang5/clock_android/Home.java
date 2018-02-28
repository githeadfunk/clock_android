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
		Log.w("123", "setting alarm");
		AlarmManager manager = (AlarmManager) getSystemService(this.ALARM_SERVICE);


		if(this.clockList.size() > 0){
			for(int i = 0; i < this.clockList.size(); i++){
				if(this.clockList.get(i).isActive()){
					clock_bean clock = this.clockList.get(i);
					Intent alarmIntent = new Intent(this, AlarmReceiver.class);
					alarmIntent.putExtra("musicUri", clock.getMusciURL());
					pendingIntent = PendingIntent.getBroadcast(this, clock.getId(), alarmIntent, 0);
					Calendar calendar = Calendar.getInstance();


					String weekDay;
					String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
					SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
					weekDay = dayFormat.format(calendar.getTime());
					int todayIndex = Arrays.asList(weekDays).indexOf(weekDay);
					Log.w("todayIndex", todayIndex + "" );
					Log.w("weekDay", weekDay );

					SimpleDateFormat sdf = new SimpleDateFormat("k:mm");
					String currentTime = sdf.format(calendar.getTime());
					Log.w("currentTime", currentTime );
					Log.w("THe current clock", clock.toString()  );
					for(int j = 0; j < todayIndex; j++){
						if(clock.getRepeat()[j] == true){

							while (calendar.get(Calendar.DAY_OF_WEEK) != (j+1)) {
								calendar.add(Calendar.DATE, 1);
							}

							String alarm_date = getYearMonthDay(calendar);

							calendar.set(Calendar.YEAR, Integer.parseInt(alarm_date.split(":")[0]));
							calendar.set(Calendar.MONTH, Integer.parseInt(alarm_date.split(":")[1]) - 1);
							calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(alarm_date.split(":")[2]));
							calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clock.getTime().split(":")[0]));
							calendar.set(Calendar.MINUTE, Integer.parseInt(clock.getTime().split(":")[1]));
							calendar.set(Calendar.SECOND, 0);
							break;
						}
					}
					for(int j = todayIndex; j < 7; j++){
						if(clock.getRepeat()[j] == true && j == todayIndex){
							if(Integer.parseInt(currentTime.split(":")[0]) <= Integer.parseInt(clock.getTime().split(":")[0])){
								if(Integer.parseInt(currentTime.split(":")[1]) < Integer.parseInt(clock.getTime().split(":")[1])) {

									calendar = Calendar.getInstance();
									String alarm_date = getYearMonthDay(calendar);

									calendar.set(Calendar.YEAR, Integer.parseInt(alarm_date.split(":")[0]));
									calendar.set(Calendar.MONTH, Integer.parseInt(alarm_date.split(":")[1]) - 1);
									calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(alarm_date.split(":")[2]));
									calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clock.getTime().split(":")[0]));
									calendar.set(Calendar.MINUTE, Integer.parseInt(clock.getTime().split(":")[1]));
									calendar.set(Calendar.SECOND, 0);
									break;
								}
							}
						}
						else if(clock.getRepeat()[j] == true && j > todayIndex){

							calendar = Calendar.getInstance();

							while (calendar.get(Calendar.DAY_OF_WEEK) != (j+1)) {
								calendar.add(Calendar.DATE, 1);
							}
							String alarm_date = getYearMonthDay(calendar);

							calendar.set(Calendar.YEAR, Integer.parseInt(alarm_date.split(":")[0]));
							calendar.set(Calendar.MONTH, Integer.parseInt(alarm_date.split(":")[1]) - 1);
							calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(alarm_date.split(":")[2]));
							calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clock.getTime().split(":")[0]));
							calendar.set(Calendar.MINUTE, Integer.parseInt(clock.getTime().split(":")[1]));
							calendar.set(Calendar.SECOND, 0);
							break;
						}
					}


					try {
						manager.cancel(pendingIntent);
					} catch (Exception e) {
						Log.w("asdf", "cancel errro" + e );
					}

					manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
				}
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		MusicCtrl mc = MusicCtrl.getInstance(this);
		mc.stopMusic();
	}

	public String getYearMonthDay(Calendar calendar){

		SimpleDateFormat yearFormat = new SimpleDateFormat("y");
		String year = yearFormat.format(calendar.getTime());
		SimpleDateFormat monthFormat = new SimpleDateFormat("M");
		String month = monthFormat.format(calendar.getTime());
		SimpleDateFormat dayFormat = new SimpleDateFormat("d");
		String day = dayFormat.format(calendar.getTime());

		String result = year + ":" + month + ":" + day;
		Log.w("result", result );
		return result;
	}


}