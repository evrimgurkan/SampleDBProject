package com.example.sampledbproject;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ConnectDB extends SQLiteOpenHelper{

	// LogCat tag
    private static final String LOG = "NFC_CARDS";
    
	private static final String DB_NAME = "CARDS";
	private static final int version = 1;
	public static final String TABLE_ACCOUNT = "CardTable";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "AccountName";
	private static final String KEY_NUMBER = "AccountNumber";
	
	private static String sql_create_account_table = "CREATE TABLE " + TABLE_ACCOUNT +
			"(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_NUMBER + " TEXT" + ");";
	
	public ConnectDB(Context context) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql_create_account_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST " + TABLE_ACCOUNT);
		Log.e(LOG, "onUpgrade");
		// create new table
		onCreate(db);
	}
	
	public void createTable() {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL(sql_create_account_table);
	}
	
	// closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    
    public void deleteDB() {
    	Log.d(LOG, "deleteDB");
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_ACCOUNT, null, null);
    	//db.execSQL("DROP TABLE IF EXIST " + TABLE_ACCOUNT);
    }
	
	// ------------------------ Account table methods ----------------//
	/*
     * Creating card info
     */
    public long createCardInfo(Cards card) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, card.getCardName());
        values.put(KEY_NUMBER, card.getCardAccount());
        System.out.println(card.getCardName());
        // insert row
        long card_id = db.insertOrThrow(TABLE_ACCOUNT, null, values);
        
        return card_id;
    }
    
    /*
     * Get single card info
     */
    public Cards getCardInfo(long card_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        String selectQuery = "SELECT * FROM " + TABLE_ACCOUNT 
        		+ " WHERE " + KEY_ID + " = " + card_id;
 
        Log.e(LOG, selectQuery);
 
        Cursor c = db.rawQuery(selectQuery, null);
 
        if (c != null && c.moveToFirst()){
        	Cards card = new Cards();
            card.setCardId(c.getInt((c.getColumnIndex(KEY_ID))));
            card.setCardName((c.getString(c.getColumnIndex(KEY_NAME))));
            card.setCardAccount(c.getString(c.getColumnIndex(KEY_NUMBER)));
            return card;
        }
        
        return null;
    }
    
    
    /*
     * Getting all cards
     */
    public List<Cards> getAllCards(){
    	List<Cards> cards = new ArrayList<Cards>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNT;
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Cards crd = new Cards();
                crd.setCardId(c.getInt((c.getColumnIndex(KEY_ID))));
                crd.setCardName((c.getString(c.getColumnIndex(KEY_NAME))));
                crd.setCardAccount(c.getString(c.getColumnIndex(KEY_NUMBER)));
     
                // adding to cards list
                cards.add(crd);
            } while (c.moveToNext());
        }
     
        return cards;
    }
    
    /*
     * Deleting card
     */
    public void deleteCard(long card_id){
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNT, KEY_ID + " = ?",
                new String[] { String.valueOf(card_id) });
    }
	
}
