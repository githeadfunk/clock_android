package com.example.jwang5.bean;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

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

	public void playMusic(Context context,String musicUri) {
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try{
			mMediaPlayer.setDataSource(context, Uri.parse(musicUri));
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		}catch(IOException e ){
		}
	}

	public void stopMusic() {
		if(mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
