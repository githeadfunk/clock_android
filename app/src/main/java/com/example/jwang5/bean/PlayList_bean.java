package com.example.jwang5.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jwang5 on 3/29/2018.
 */

public class PlayList_bean implements Serializable {

	private int id;
	private String name = "This is not my name!";
	private ArrayList<String> songs;

	public String getName() {
		return name;
	}

	public ArrayList<String> getSongs() {
		return songs;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSongs(ArrayList<String> songs) {
		this.songs = songs;
	}

	public int getId() {

		return id;

	}
}
