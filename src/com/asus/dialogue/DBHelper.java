package com.asus.dialogue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_NAME="nouns_adjs";
	public static final String DB_NAME="ontology.db";
	public static final String C_ID=BaseColumns._ID;
	public static final String C_NOUN="noun";
	public static final String C_ADJ="adj";
	public static  final String C_PHOTO_ID="photoid";
	
	public DBHelper(Context context) {
		super(context,DB_NAME , null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="create table "+TABLE_NAME+"(" 
				+ C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ C_NOUN + " text,"
				+ C_ADJ + " text,"
				+ C_PHOTO_ID + " int"
				+")";
		Log.w("dbhelper", sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
