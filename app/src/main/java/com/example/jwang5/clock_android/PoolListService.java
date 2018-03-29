package com.example.jwang5.clock_android;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jwang5.bean.PlayList_bean;
import com.example.jwang5.bean.clock_bean;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jwang5 on 3/29/2018.
 */

public class PoolListService {

	private SharedPreferences myref;
	private Context context;
	private static PoolListService instance;
	private String playListStr;
	private ArrayList<PlayList_bean> playList;

	public PoolListService(Context context) {
		this.context = context;
	}

	public static PoolListService getInstance(Context context) {
		if (instance == null) {
			instance = new PoolListService(context);
		}
		return instance;
	}

	public void setPoolList(ArrayList<PlayList_bean> pl){
		this.playList = pl;
		SharedPreferences.Editor editor = this.myref.edit();
		try{
			this.playListStr = ObjectSerializer.serialize(this.playList);
			editor.putString("playList", this.playListStr);
			editor.commit();
		}catch(IOException e){
		}
	}

	public ArrayList<PlayList_bean> getPoolList(){
		this.playList = new ArrayList<PlayList_bean>();
		this.myref = this.context.getSharedPreferences("myAppName", Context.MODE_PRIVATE);
		this.playListStr = myref.getString("playList","");
		if(!this.playListStr.isEmpty()) {
			try {
				this.playList = (ArrayList<PlayList_bean>) ObjectSerializer.deserialize(this.playListStr);
			} catch (IOException e) {

			}
		}
		return this.playList;
	}


}
