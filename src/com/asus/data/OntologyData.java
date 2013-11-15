package com.asus.data;

import java.util.ArrayList;
import java.util.List;

import com.asus.activity.SentenceMakingActivity;
import com.asus.util.RandomUtil;

import android.R.integer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OntologyData {

	private SQLiteDatabase database;
	private DBHelper dbHelper;
	String[] columns={""};
	String selection;
	String groupBy;
	String[] selectArgs={""};

	public static OntologyData instance;

	public static OntologyData getInstance() {
		if (instance == null) {
			instance = new OntologyData();
		}else{
			instance.dbHelper=SentenceMakingActivity.dbHelper;
			instance.database=instance.dbHelper.getReadableDatabase();
		}
		return instance;
	}

	private OntologyData() {
		// TODO Auto-generated constructor stub
		this.dbHelper = SentenceMakingActivity.dbHelper;
		this.database = dbHelper.getReadableDatabase();
		
		//test for db
//		String[] columns = { dbHelper.C_NOUN };
//		String[] selectArgs = { "»Ä·È·È" };
//		Cursor cursor = database.query(dbHelper.TABLE_NAME, columns,dbHelper.C_ADJ + "=?", selectArgs, dbHelper.C_NOUN, null, null,null);
		String sql="SELECT "+dbHelper.C_PHOTO_ID+" FROM "+dbHelper.TABLE_NAME
				+" Where "+dbHelper.C_ADJ+"=?"
				+ "GROUP BY "+dbHelper.C_PHOTO_ID
				+" ORDER BY RANDOM() LIMIT 6";
		selectArgs[0]="ÄÐÄÐªº";
		Cursor cursor=database.rawQuery(sql, selectArgs);
		while (cursor.moveToNext()) {
			Log.w(getClass().getSimpleName(), Integer.toString(cursor.getInt(0)));
		}
		cursor.close();

	}
	
	private String[] cursorStringToArray(Cursor cursor){
		List<String> list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		cursor.close();
		return list.toArray(new String[list.size()]);
	}
	
	private int[] cursorIntToArray(Cursor cursor){
		List<Integer> list = new ArrayList<Integer>();
		while (cursor.moveToNext()) {
			list.add(cursor.getInt(0));
		}
		cursor.close();
		
		int[] ints = new int[list.size()];
		for(int i=0, len = list.size(); i < len; i++){
		   ints[i] = list.get(i);
		   Log.w(getClass().getSimpleName(), Integer.toString(ints[i]));
		}
		return ints;
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
			cursor.close();
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
			cursor.close();
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
				+" ORDER BY RANDOM() LIMIT 1";
		Cursor cursor=database.rawQuery(sql, null);
		cursor.moveToNext();
		return cursor.getString(0);
		// return "¦Wµü¶W½d³ò";
	}

	public String getOneRandomAdj() {
		String sql="SELECT "+dbHelper.C_ADJ+" FROM "+dbHelper.TABLE_NAME
				+" ORDER BY RANDOM() LIMIT 1";
		Cursor cursor=database.rawQuery(sql, null);
		cursor.moveToNext();
		return cursor.getString(0);
		// return "§Î®eµüµü¶W½d³ò";
	}
	
	public int getOnePhotoIdOfOneNoun(String noun){
		columns[0] = dbHelper.C_PHOTO_ID;
		selection = dbHelper.C_NOUN+"=?";
		selectArgs[0]=noun;
		groupBy= dbHelper.C_NOUN;
		Cursor cursor=database.query(dbHelper.TABLE_NAME,columns,selection,selectArgs,groupBy,null,null,null);
		if(cursor.getCount()>0){
			cursor.moveToNext();
			Log.w(getClass().getSimpleName(), "photo id found for one noun");
			return cursor.getInt(0);
		}else{
			Log.w(getClass().getSimpleName(), "Not found photo id for one noun");
			return 0;
		}
	}
	
	public int[] getPhotoIdsOfOneAdj(String adj){
		
		String sql="SELECT "+dbHelper.C_PHOTO_ID+" FROM "+dbHelper.TABLE_NAME
				+" Where "+dbHelper.C_ADJ+"=?"
				+ "GROUP BY "+dbHelper.C_PHOTO_ID
				+" ORDER BY RANDOM() LIMIT 6";
		selectArgs[0]=adj;
		Cursor cursor=database.rawQuery(sql, selectArgs);
		
		if(cursor.getCount()>0){
			Log.w(getClass().getSimpleName(), "photo ids found for one adj");
			return cursorIntToArray(cursor);
		}else{
			Log.w(getClass().getSimpleName(), "Not found photo ids for one adj");
			return new int[0];
		}
	}
	
}
