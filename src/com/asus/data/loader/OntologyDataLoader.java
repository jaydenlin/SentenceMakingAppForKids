package com.asus.data.loader;

import com.asus.data.DBHelper;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OntologyDataLoader extends CursorLoader{
	
	
	private Context context;
	public OntologyDataLoader(Context context) {
		super(context);
		this.context=context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Cursor loadInBackground() {
		// TODO Auto-generated method stub
		DBHelper dbHelper = new DBHelper(context);
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		String[] columns= {dbHelper.C_NOUN}; 
		String[] selectArgs={"¬õ¥V¥V"};
		Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
//		database.close(); don't close!
		return cursor;
	}
	

}
