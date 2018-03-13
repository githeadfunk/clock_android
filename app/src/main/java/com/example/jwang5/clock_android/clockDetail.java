package com.example.jwang5.clock_android;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jwang5.bean.MusicCtrl;
import com.example.jwang5.bean.clock_bean;
import java.io.IOException;
import java.util.ArrayList;

public class clockDetail extends AppCompatActivity {

	private MediaPlayer mediaPlayer;
	private Vibrator vibrator;

	private Boolean[] repeat = {false, false, false, false, false, false, false};
	private Boolean vibrateOn = false;
	private String musicUri;
	private String time;
	private Boolean active = true;
	private int id = 0;
	private int volume;
	private ArrayList<clock_bean> clockList;
	private clock_bean currentClock;
	private ClockListService cls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clock_detail);
		Bundle b = getIntent().getExtras();
		if(b != null) this.id = b.getInt("id");

		this.cls = ClockListService.getInstance(this);
		this.clockList = cls.getClockList();

		if(this.id == 0){
			this.currentClock = new clock_bean();
		}
		else{
			for(int i = 0; i < this.clockList.size(); i++){
				if(this.clockList.get(i).getId() == this.id) {
					this.currentClock = clockList.get(i);
					break;
				}
			}
		}
		setViewValues(this.currentClock);
	}

	public void setViewValues(clock_bean clock){
		this.time = clock.getTime();
		TextView timeV = (TextView)findViewById(R.id.slectedTime);
		timeV.setText(clock.getTime());

		this.repeat = clock.getRepeat();
		String[] weekDay = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		for(int i = 0; i < weekDay.length; i++){
			TextView tv = (TextView) findViewById(getResources().getIdentifier(weekDay[i], "id", getPackageName()));
			if (this.repeat[i]) tv.setTextColor(Color.parseColor("#C70039"));
			else tv.setTextColor(Color.parseColor("#000000"));
		}

		this.vibrateOn = clock.isVibrate();
		CheckBox checkBox = (CheckBox) findViewById(R.id.VibrateCheckBox);
		checkBox.setChecked(this.vibrateOn);

		this.musicUri = clock.getMusciURL();
		this.id = clock.getId();
		this.active = clock.isActive();
		this.volume = clock.getVolume();

		SeekBar volumeBar=(SeekBar) findViewById(R.id.volumeBar);
		volumeBar.setMax(100);
		volumeBar.setProgress(this.volume);

//		volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//			int progressChanged = 0;
//
//			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
//				progressChanged = progress;
//			}
//
//			public void onStartTrackingTouch(SeekBar seekBar) {
//			}
//
//			public void onStopTrackingTouch(SeekBar seekBar) {
//			}
//		});

	}

	public void chooseTime(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "TimePicker");
	}

	public void onRepeatClick(View v) {
		String pageNumber = v.getTag().toString();
		String[] weekDay = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		int index = Integer.parseInt(pageNumber);
		TextView tv = (TextView) findViewById(getResources().getIdentifier(weekDay[index], "id", getPackageName()));
		this.repeat[index] = !this.repeat[index];
		if (this.repeat[index]) tv.setTextColor(Color.parseColor("#C70039"));
		else tv.setTextColor(Color.parseColor("#000000"));
	}

	public void onVibrateClicked(View v){
		boolean checked = ((CheckBox) v).isChecked();
		this.vibrateOn = checked;
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		if(this.vibrateOn){
			vibrator.vibrate(500);
		}
	}


	public void saveClock(View v){

		TextView tv = (TextView) findViewById(R.id.slectedTime);
		this.time = tv.getText().toString();

		SeekBar volumeBar = (SeekBar) findViewById(R.id.volumeBar);
		this.volume = volumeBar.getProgress();

		boolean isValid = runValidation();
		if(!isValid) return;

		if(this.clockList == null){
			this.clockList = new ArrayList<clock_bean>();
			this.currentClock = new clock_bean(this.time, this.repeat, this.active,1, this.vibrateOn, this.musicUri, this.volume);
			this.clockList.add(this.currentClock);
		}
		else if(this.id == 0){
			int newId = this.clockList.size() + 1;
			this.currentClock = new clock_bean(this.time, this.repeat, this.active,newId, this.vibrateOn, this.musicUri, this.volume);
			this.clockList.add(this.currentClock);
		}
		else{
			this.currentClock = new clock_bean(this.time, this.repeat, this.active, this.id, this.vibrateOn, this.musicUri, this.volume);
			for(int i = 0; i < this.clockList.size(); i++){
				if(this.clockList.get(i).getId() == this.id){
					this.clockList.set(i, this.currentClock);
					break;
				}
			}
		}
		Log.w("Current clock is ", this.currentClock.toString());
		this.cls = ClockListService.getInstance(this);
		this.cls.setClockList(this.clockList);
		startActivity(new Intent(this, Home.class));
	}

	public boolean runValidation(){
		if(this.time.isEmpty()) {
			new myAlert("Please set time", this).onCreateDialog();
			return false;
		}
		int count = 0;
		for(int i = 0; i < 7; i++){
			if(this.repeat[i].equals(false)){
				count++;
			}
		}
		if(count == 7){
			new myAlert("Please set repeat weekday", this).onCreateDialog();
			return false;
		}
		if(this.musicUri == null || this.musicUri.isEmpty()){
			new myAlert("Please set ringtone", this).onCreateDialog();
			return false;
		}
		if(this.volume == 0){
			new myAlert("Please set volume", this).onCreateDialog();
			return false;
		}
		return true;
	}

	public void cancelClock(View v){
		startActivity(new Intent(this, Home.class));
	}

	public void openBrowser(View v) {
		Log.w("open", "openBrowser: " );

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		intent.setType("audio/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try{
			startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"),1);
		}catch(Exception ex){

		}
	}

	public void onActivityResult(int requestCode, int resultCode,Intent resultData) {
		Uri currentUri = null;
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == 1){
				if(resultData != null){
					currentUri = resultData.getData();
					this.musicUri = currentUri.toString();
					this.getContentResolver().takePersistableUriPermission(currentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				}
			}
		}
	}

	public void testAlarm(View v){
		if(this.musicUri == null || this.musicUri.isEmpty()){
			new myAlert("Please set ringtone", this).onCreateDialog();
			return;
		}
		if(this.volume == 0){
			new myAlert("Please set volume", this).onCreateDialog();
			return;
		}
		MusicCtrl mc = MusicCtrl.getInstance(this);
		mc.playMusic(this, this.musicUri, this.volume);
	}

	@Override
	protected void onStop() {
		super.onStop();
		MusicCtrl mc = MusicCtrl.getInstance(this);
		mc.stopMusic();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(this.mediaPlayer != null){
			this.mediaPlayer.release();
			this.mediaPlayer = null;
		}
	}
}