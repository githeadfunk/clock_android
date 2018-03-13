package com.example.jwang5.bean;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.example.jwang5.clock_android.myAlert;

import java.io.IOException;

public class MusicCtrl {
	private static MusicCtrl sInstance;
	private Context mContext;
	private MediaPlayer mMediaPlayer;
	public MusicCtrl(Context context) {
		mContext = context;
	}

	public static MusicCtrl getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new MusicCtrl(context);
		}
		return sInstance;
	}

	public void playMusic(Context context, String musicUri, int volume) {
		stopMusic();
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		float log1=(float)(Math.log(100-volume + 1)/Math.log(101));
		mMediaPlayer.setVolume(1-log1, 1-log1);
		try{
			mMediaPlayer.setDataSource(context, Uri.parse(musicUri));
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		}catch(IOException e ){
			myAlert alert = new myAlert(e.toString(), context);
			alert.onCreateDialog();
		}
	}

	public void stopMusic() {
		if(mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
