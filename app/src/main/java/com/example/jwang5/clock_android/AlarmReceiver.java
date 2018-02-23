package com.example.jwang5.clock_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jwang5.bean.MusicCtrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

	private MediaPlayer mediaPlayer;
	@Override
	public void onReceive(Context context, Intent intent) {

		Date currentTime = Calendar.getInstance().getTime();
		Log.w("11456", "alarm goes off" + currentTime);

		Bundle extras = intent.getExtras();
		String musicUri = extras.getString("musicUri");

		MusicCtrl mc = MusicCtrl.getInstance(context);
		mc.playMusic(context, musicUri);

	}

}