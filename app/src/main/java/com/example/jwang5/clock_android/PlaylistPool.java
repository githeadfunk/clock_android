package com.example.jwang5.clock_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jwang5.bean.PlayList_bean;
import com.example.jwang5.bean.Pool_list_adapter;

import java.util.ArrayList;

/**
 * Created by jwang5 on 3/23/2018.
 */

public class PlaylistPool extends AppCompatActivity {

	private ListView listView;
	private PoolListService pls = PoolListService.getInstance(this);
	private ArrayList<PlayList_bean> palyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("123", "PlaylistPool onCreate: ");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pool);

		this.listView = findViewById(R.id.poolView);
		this.palyList = this.pls.getPoolList();
		if(this.palyList != null && this.palyList.size() > 0){

			//setting list view
			Pool_list_adapter adapter = new Pool_list_adapter(this, this.palyList);
			this.listView.setAdapter(adapter);

			final Context context = this;
			this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					Intent intent = new Intent(context, clockDetail.class);
					Bundle b = new Bundle();
					b.putInt("id", position + 1);
					intent.putExtras(b);
//					startActivity(intent);

				}
			});
		}




	}


	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pool_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.add_list:
				Bundle b = new Bundle();
				b.putInt("id", 0); //Your id
				Intent intent = new Intent(this, clockDetail.class);
				intent.putExtras(b);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
