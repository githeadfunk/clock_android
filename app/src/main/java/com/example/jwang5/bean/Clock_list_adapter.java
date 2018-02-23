package com.example.jwang5.bean;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.jwang5.clock_android.ClockListService;
import com.example.jwang5.clock_android.ObjectSerializer;
import com.example.jwang5.clock_android.R;

import java.io.IOException;
import java.util.ArrayList;

public class Clock_list_adapter extends ArrayAdapter<clock_bean> {

	public Clock_list_adapter(Context context, ArrayList<clock_bean> clocks) {
		super(context, 0, clocks);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		clock_bean clock = getItem(position);
		Log.w("123", "getView: " + position + "  is active: " + clock.isActive() );
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.clock_row, parent, false);
		}

		// Lookup view for data population
		TextView time = (TextView) convertView.findViewById(R.id.time);
		time.setText(clock.getTime());
		CheckBox cb = (CheckBox) convertView.findViewById(R.id.active);
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ClockListService cls = ClockListService.getInstance(getContext());
				cls.toggleActive(position, isChecked);
			}
		});
		cb.setChecked(clock.isActive());

		TextView tv = (TextView) convertView.findViewById(R.id.Sunday);
		if (clock.getRepeat()[0]) tv.setTextColor(Color.parseColor("#C70039"));
		else tv.setTextColor(Color.parseColor("#000000"));

		tv = (TextView) convertView.findViewById(R.id.Monday);
		if (clock.getRepeat()[1]) tv.setTextColor(Color.parseColor("#C70039"));
		else tv.setTextColor(Color.parseColor("#000000"));

		tv = (TextView) convertView.findViewById(R.id.Tuesday);
		if (clock.getRepeat()[2]) tv.setTextColor(Color.parseColor("#C70039"));
		else tv.setTextColor(Color.parseColor("#000000"));

		tv = (TextView) convertView.findViewById(R.id.Wednesday);
		if (clock.getRepeat()[3]) tv.setTextColor(Color.parseColor("#C70039"));
		else tv.setTextColor(Color.parseColor("#000000"));

		tv = (TextView) convertView.findViewById(R.id.Thursday);
		if (clock.getRepeat()[4]) tv.setTextColor(Color.parseColor("#C70039"));
		else tv.setTextColor(Color.parseColor("#000000"));

		tv = (TextView) convertView.findViewById(R.id.Friday);
		if (clock.getRepeat()[5]) tv.setTextColor(Color.parseColor("#C70039"));
		else tv.setTextColor(Color.parseColor("#000000"));

		tv = (TextView) convertView.findViewById(R.id.Saturday);
		if (clock.getRepeat()[6]) tv.setTextColor(Color.parseColor("#C70039"));
		else tv.setTextColor(Color.parseColor("#000000"));

		return convertView;
	}



}
