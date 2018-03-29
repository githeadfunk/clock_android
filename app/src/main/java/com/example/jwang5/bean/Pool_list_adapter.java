package com.example.jwang5.bean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.jwang5.clock_android.ClockListService;
import com.example.jwang5.clock_android.R;

import java.util.ArrayList;

/**
 * Created by jwang5 on 3/29/2018.
 */

public class Pool_list_adapter extends ArrayAdapter<PlayList_bean> {

	public Pool_list_adapter(Context context, ArrayList<PlayList_bean> playLists) {
		super(context, 0, playLists);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		PlayList_bean playList = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.playlist_row, parent, false);
		}

		// Lookup view for data population
		TextView name = (TextView) convertView.findViewById(R.id.play_list_name);
		name.setText(playList.getName());

		return convertView;
	}
}
