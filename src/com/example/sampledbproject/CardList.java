package com.example.sampledbproject;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class CardList extends Activity {
	
	private ConnectDB connect_db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_list);
		
		Context context = getApplicationContext();
		
		if(context != null){
			Log.d("EVRIM", "context not null");
			connect_db = new ConnectDB(context);
		}
		else {
			Log.d("EVRIM", "context is null");
		}

		//connect_db = new ConnectDB(getApplicationContext());
		
		ListView list_view = (ListView) findViewById(R.id.list_view);
		
		/*
		 * list all cards
		 */		
		List<Cards> card_list = connect_db.getAllCards();
		
		// 1. pass context and data to the custom adapter
        MyAdapter adapter = new MyAdapter(getApplicationContext(), card_list);
 
        // 2. setListAdapter
        list_view.setAdapter(adapter);
	}
	
	private void setToastMsg(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
