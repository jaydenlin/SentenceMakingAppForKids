package com.asus.data;

import com.asus.util.RandomUtil;

import android.R.integer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class OntologyDataDB {

	private SQLiteDatabase database;
	private DBHelper dbHelper;
	String[] columns;
	String selection;
	String groupBy;
	String[] selectArgs;

	public static OntologyDataDB instance;

	public static OntologyDataDB getInstance(DBHelper dbHelper) {
		if (instance == null) {
			instance = new OntologyDataDB(dbHelper);
		}
		return instance;
	}

	private OntologyDataDB(DBHelper dbHelper) {
		// TODO Auto-generated constructor stub
		this.database = dbHelper.getReadableDatabase();
		this.dbHelper = dbHelper;
		
		String[] columns = { dbHelper.C_NOUN };
		String[] selectArgs = { "»Ä·È·È" };
		
		// database.query(table, columns, selection, selectionArgs, groupBy,having, orderBy, limit)
		Cursor cursor = database.query(dbHelper.TABLE_NAME, columns,dbHelper.C_ADJ + "=?", selectArgs, dbHelper.C_NOUN, null, null,null);
		while (cursor.moveToNext()) {
			Log.w(getClass().getSimpleName(), cursor.getString(0));
		}
		cursor.close();

	}
	
	private String[] cursorStringToArray(Cursor cursor){
		String[] array={};
		int i=0;
		while (cursor.moveToNext()) {
			array[i]=cursor.getString(0);
			i++;
		}
		return array;
	}
	
	private int[] cursorIntToArray(Cursor cursor){
		int[] array={};
		int i=0;
		while (cursor.moveToNext()) {
			array[i]=cursor.getInt(0);
			i++;
		}
		return array;
	}

	// ////////////////////////////////////////
	// //Find Answer
	// ///////////////////////////////////////
	public String[] getMatchedAdjArrayForOneNoun(String noun) {
		columns[0] = dbHelper.C_ADJ;
		selection = dbHelper.C_NOUN+"=?";
		selectArgs[0]=noun;
		groupBy= dbHelper.C_ADJ;
		Cursor cursor=database.query(dbHelper.TABLE_NAME,columns,selection,selectArgs,groupBy,null,null,null);
		if (cursor.getCount()>0) {
			return cursorStringToArray(cursor);
		} else {
			return null;
		}
	}

	public String[] getMatchedNounArrayForOneAdj(String adj) {
		columns[0] = dbHelper.C_NOUN;
		selection = dbHelper.C_ADJ+"=?";
		selectArgs[0]=adj;
		groupBy= dbHelper.C_NOUN;
		Cursor cursor=database.query(dbHelper.TABLE_NAME,columns,selection,selectArgs,groupBy,null,null,null);
		if (cursor.getCount()>0) {
			return cursorStringToArray(cursor);
		} else {
			return null;
		}
	}

	// ////////////////////////////////////////
	// //Find teach
	// ///////////////////////////////////////
	public String getOneRandomAdjForOneNoun(String noun) {
		String[] adjArray=getMatchedAdjArrayForOneNoun(noun);
		if (adjArray!=null) {
			return adjArray[RandomUtil.getRandomInteger(0, adjArray.length - 1)];
		} else {
			Log.w(getClass().getSimpleName(),
					"Can't find random Adj for the noun");
			return "";
		}
		
	}

	public String getOneRandomNounForOneAdj(String adj) {
		String[] nounArray=getMatchedNounArrayForOneAdj(adj);
		if (nounArray!=null) {
			return nounArray[RandomUtil.getRandomInteger(0, nounArray.length - 1)];
		} else {
			Log.w(getClass().getSimpleName(),
					"Can't find random Noun for the adj");
			return "";
		}
		
	}

	public String getOneRandomNoun() {
		String sql="SELECT "+dbHelper.C_NOUN+" FROM "+dbHelper.TABLE_NAME
				+" WHERE "+dbHelper.C_ID+" >= (SELECT FLOOR( MAX("+dbHelper.C_ID+") * RAND()) FROM "+dbHelper.TABLE_NAME+" ) ORDER BY "
				+ dbHelper.C_ID+" LIMIT 1";
		Cursor cursor=database.rawQuery(sql, null);
		return cursor.getString(0);
		// return "¦Wµü¶W½d³ò";
	}

	public String getOneRandomAdj() {
		String sql="SELECT "+dbHelper.C_ADJ+" FROM "+dbHelper.TABLE_NAME
				+" WHERE "+dbHelper.C_ID+" >= (SELECT FLOOR( MAX("+dbHelper.C_ID+") * RAND()) FROM "+dbHelper.TABLE_NAME+" ) ORDER BY "
				+ dbHelper.C_ID+" LIMIT 1";
		Cursor cursor=database.rawQuery(sql, null);
		return cursor.getString(0);
		// return "§Î®eµüµü¶W½d³ò";
	}

}
