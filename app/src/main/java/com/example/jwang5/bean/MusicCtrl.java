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
	private int originalVolume = -1;
	private AudioManager audioManager;
	private Context context;

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
	  this.context = context;
		stopMusic();
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    mMediaPlayer.setLooping(true);


    audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    this.originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    int vol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * volume / 100;
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol,0);
		try{

			mMediaPlayer.setDataSource(context, Uri.parse(musicUri));
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			Log.w("123", "playing Music" + "originalVolume: " + this.originalVolume + ", vloume: " + volume + ", Vol: " + vol
							+ ", music: " + musicUri);
		}catch(IOException e ){

		}
	}

	public void stopMusic() {
		if(mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		if(audioManager != null && this.originalVolume != -1){
      audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, this.originalVolume, 0);
      audioManager = null;
    }
	}
}
