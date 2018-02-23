package com.example.jwang5.bean;


import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by jwang5 on 2/15/2018.
 */

public class clock_bean implements Serializable {

	private String time;
	private Boolean[] repeat;
	private boolean active;
	private int id;
	private boolean vibrate;
	private String musciURL;

	public clock_bean(String time, Boolean[] repeat, boolean active, int id, boolean vibrate, String musciURL) {
		this.time = time;
		this.repeat = repeat;
		this.active = active;
		this.id = id;
		this.vibrate = vibrate;
		this.musciURL = musciURL;
	}

	public String getTime() {
			return time;
	}

	public Boolean[] getRepeat() {
			return repeat;
	}

	public boolean isActive() {
			return active;
	}

	public int getId() {
			return id;
	}

	public boolean isVibrate() {
			return vibrate;
	}

	public String getMusciURL() {
			return musciURL;
	}

	public void setTime(String time) {
			this.time = time;
	}

	public void setRepeat(Boolean[] repeat) {
			this.repeat = repeat;
	}

	public void setActive(boolean active) {
			this.active = active;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

	public void setMusciURL(String musciURL) {
        this.musciURL = musciURL;
    }

	@Override
	public String toString() {
		return "clock_bean{" +
						"time='" + time + '\'' +
						", repeat=" + Arrays.toString(repeat) +
						", active=" + active +
						", id=" + id +
						", vibrate=" + vibrate +
						", musciURL=" + musciURL +
						'}';
	}
}
