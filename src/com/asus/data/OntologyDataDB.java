package com.asus.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class OntologyDataDB {
	
	public static final String TABLE_NAME="nouns_adjs";
	public static final String DB_NAME="ontology.db";
	public static final String C_ID=BaseColumns._ID;
	public static final String C_NOUN="noun";
	public static final String C_ADJ="adj";
	public static  final String C_PHOTO_ID="photoid";
	String path="/data/data/com.asus.activity/databases/ontology.db";
	public OntologyDataDB() {
		// TODO Auto-generated constructor stub
		SQLiteDatabase database= SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
		String[] columns= {this.C_NOUN}; 
		String[] selectArgs={"¬õ¥V¥V"};
		Cursor cursor = database.query(this.TABLE_NAME, columns, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			Log.w(getClass().getSimpleName(), cursor.getString(0));
		}
		cursor.close();
		
	}
	
	class OpenDB extends Thread{
		
		public OpenDB() {
			super("OpenDB");
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
