package com.example.sampledbproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	Button btn_save;
	Button btn_get_info;
	EditText edt_txt_account_number;
	EditText edt_txt_account_name;
	TextView txt_account_number;
	TextView txt_account_name;

	private ConnectDB connect_db;
	public static final String TAG = "Evrim";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		connect_db 				= new ConnectDB(getApplicationContext());
		
		btn_save 				= (Button) findViewById(R.id.btn_save);
		btn_get_info 			= (Button) findViewById(R.id.btn_get_info);
		edt_txt_account_number 	= (EditText) findViewById(R.id.txt_account_number);
		edt_txt_account_name 	= (EditText) findViewById(R.id.txt_account_name);
		txt_account_number 		= (TextView) findViewById(R.id.account_number);
		txt_account_name 		= (TextView) findViewById(R.id.account_name);
		
		/*
		 * keep in order to be useful
		 * get database path
		 */
//		File dbFile = getDatabasePath("CARDS");
//		setToastMsg(dbFile.getAbsolutePath());
		
//		Log.d("DELETE", "DELETED");
//		SQLiteDatabase db = connect_db.getWritableDatabase();
//    	db.execSQL("DROP TABLE AccountTable");
    	
    	//exportDatabse();

		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					Cards card = new Cards(edt_txt_account_name.getText().toString(), edt_txt_account_number.getText().toString());
					long id = connect_db.createCardInfo(card);
					setToastMsg(Long.toString(id));
					Log.d("Card Count", "Card Count: " + connect_db.getAllCards().size());
				}
				finally{
					connect_db.closeDB();
				}
			}
		});

		btn_get_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Cards card = connect_db.getCardInfo(1);
				if(card != null) {
					Log.d("Card Info", "Card is not null Card ID = " + card.getCardId());
					txt_account_name.setText(card.getCardName());
					txt_account_number.setText(card.getCardAccount());
				}
				else{
					Log.d("Card Info", "Card is null !!");
				}
							
				// Start Card List Activity
				getCardListActivity();
			}
		});
		
		// Don't forget to close database connection
		connect_db.closeDB();
	}
	
	private void getCardListActivity() {
		Intent i = new Intent(this, CardList.class);
 		startActivityForResult(i, 1);
	}
	
	private void setToastMsg(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	/*
	 * export local database to sd card
	 */
	public void exportDatabse() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            
            if (sd.canWrite()) {
        		//File dbFile = getDatabasePath("CARDS");
        		//setToastMsg(dbFile.getAbsolutePath());
            	// dynamic path can be instead of hard coded
                String currentDBPath = "//data//com.example.sampledbproject//databases//CARDS";
                // save to Download folder
                String backupDBPath = "//Download//13_46.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                	setToastMsg("exist");
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                else{
                	setToastMsg("not exist :(");
                }
            }
            else{
            	setToastMsg("cant write :(");
            }
        } 
        catch (Exception e) {
        	setToastMsg("exception :(");
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
