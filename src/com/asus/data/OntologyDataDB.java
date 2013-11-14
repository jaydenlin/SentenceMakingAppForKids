package com.asus.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class OntologyDataDB {
	
	
	public static OntologyDataDB instance;
	public static OntologyDataDB getInstance(DBHelper dbHelper){
		if(instance==null){
			instance=new OntologyDataDB(dbHelper);
		}
		return instance;
	}
	
	private OntologyDataDB(DBHelper dbHelper) {
		// TODO Auto-generated constructor stub
		SQLiteDatabase database=dbHelper.getReadableDatabase();
		String[] columns= {dbHelper.C_NOUN}; 
		String[] selectArgs={"»Ä·È·È"};
		//Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
		              //database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
						//database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
		Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, dbHelper.C_ADJ+"=?", selectArgs, dbHelper.C_NOUN, null, null, null);
		while(cursor.moveToNext()){
			Log.w(getClass().getSimpleName(), cursor.getString(0));
		}
		cursor.close();
		
	}
	
}
