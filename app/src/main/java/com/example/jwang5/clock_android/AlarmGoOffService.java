package com.example.jwang5.clock_android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.jwang5.bean.clock_bean;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by wyh on 3/6/2018.
 */

public class AlarmGoOffService {

  private Context context;


  public AlarmGoOffService(Context context){
      this.context = context;
  }

  public void goOff(clock_bean clock){

    PendingIntent pendingIntent;
    Intent alarmIntent;
    Calendar calendar;
    AlarmManager manager = (AlarmManager) this.context.getSystemService(this.context.ALARM_SERVICE);

    alarmIntent = new Intent(this.context, AlarmReceiver.class);
    alarmIntent.putExtra("musicUri", clock.getMusciURL());
    alarmIntent.putExtra("volume", clock.getVolume());

		Log.w("!@3", "setting alarm " + "vloume: " + clock.getVolume() + ", music: " + clock.getMusciURL() );
		myAlert alert = new myAlert("setting alarm " + "vloume: " + clock.getVolume() + ", alarm id: " + clock.getId(), context);
		alert.onCreateDialog();

    pendingIntent = PendingIntent.getBroadcast(this.context, clock.getId(), alarmIntent, 0);
    calendar = Calendar.getInstance();

    String weekDay;
    String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
    weekDay = dayFormat.format(calendar.getTime());
    int todayIndex = Arrays.asList(weekDays).indexOf(weekDay);

    SimpleDateFormat sdf = new SimpleDateFormat("k:mm");
    String currentTime = sdf.format(calendar.getTime());
    Boolean laterToday = false;

    if(clock.getRepeat()[todayIndex] == true){
      laterToday = !(Integer.parseInt(currentTime.split(":")[0]) > Integer.parseInt(clock.getTime().split(":")[0]) ||
        (Integer.parseInt(currentTime.split(":")[0]) == Integer.parseInt(clock.getTime().split(":")[0]) &&
          Integer.parseInt(currentTime.split(":")[1]) >= Integer.parseInt(clock.getTime().split(":")[1])
        ));
      //alarm in next week the same weekday
      if(!laterToday){
        calendar = setCalendar(7, clock);
      }
    }

    for(int j = 0; j < todayIndex; j++){
      if(clock.getRepeat()[j] == true){
        calendar = Calendar.getInstance();
        int count = 0;
        while (calendar.get(Calendar.DAY_OF_WEEK) != (j+1)) {
          calendar.add(Calendar.DATE, 1);
          count ++;
        }
        calendar = setCalendar(count, clock);
        break;
      }
    }


    for(int j = todayIndex + 1; j < 7; j++){
      if(clock.getRepeat()[j] == true && j > todayIndex){
        calendar = Calendar.getInstance();
        int count = 0;
        while (calendar.get(Calendar.DAY_OF_WEEK) != (j+1)) {
          calendar.add(Calendar.DATE, 1);
          count ++;
        }
        calendar = setCalendar(count, clock);
        break;
      }
    }

    if(clock.getRepeat()[todayIndex] == true){
      if(laterToday) calendar = setCalendar(0, clock);
    }

//    try {
//      manager.cancel(pendingIntent);
//    } catch (Exception e) {
//      Log.w("asdf", "cancel errro" + e );
//    }

		Log.w("123", "setting alarm at " +  calendar.getTime());
		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
  }

  public void cancelAlarm(clock_bean clock){

    PendingIntent pendingIntent;
    Intent alarmIntent;
    AlarmManager manager = (AlarmManager) this.context.getSystemService(this.context.ALARM_SERVICE);

		alarmIntent = new Intent(this.context, AlarmReceiver.class);
		alarmIntent.putExtra("musicUri", clock.getMusciURL());
		alarmIntent.putExtra("volume", clock.getVolume());

		Log.w("123", "cancelAlarm: id" + clock.getId()  );

		pendingIntent = PendingIntent.getBroadcast(this.context, clock.getId(), alarmIntent, 0);

    try {
      manager.cancel(pendingIntent);
    } catch (Exception e) {
			myAlert alert = new myAlert(e.toString(), context);
			alert.onCreateDialog();
    }
  }

  private String getYearMonthDay(Calendar calendar){

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

  private Calendar setCalendar(int days, clock_bean clock){
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, days);
    String alarm_date = getYearMonthDay(calendar);
    calendar.set(Calendar.YEAR, Integer.parseInt(alarm_date.split(":")[0]));
    calendar.set(Calendar.MONTH, Integer.parseInt(alarm_date.split(":")[1]) - 1);
    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(alarm_date.split(":")[2]));
    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clock.getTime().split(":")[0]));
    calendar.set(Calendar.MINUTE, Integer.parseInt(clock.getTime().split(":")[1]));
    calendar.set(Calendar.SECOND, 0);
    return  calendar;
  }


}
