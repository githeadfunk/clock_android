package com.example.jwang5.clock_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import com.example.jwang5.bean.MusicCtrl;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		WakeLocker.acquire(context);
		Date currentTime = Calendar.getInstance().getTime();
		Log.w("11456", "alarm goes off" + currentTime);

		Bundle extras = intent.getExtras();
		String musicUri = extras.getString("musicUri");
		int volume = extras.getInt("volume");

		MusicCtrl mc = MusicCtrl.getInstance(context);
		mc.playMusic(context, musicUri, volume);

		context.startActivity(new Intent(context, Home.class));

	}

}