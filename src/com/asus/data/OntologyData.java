package com.asus.data;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.asus.activity.SentenceMakingActivity;
import com.asus.exception.MatchedWordsNotFound;
import com.asus.exception.PhotoIdsNotFound;
import com.asus.util.RandomUtil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OntologyData {

	private SQLiteDatabase database;
	private DBHelper dbHelper;
	String[] columns = { "" };
	String selection;
	String groupBy;
	String[] selectArgs = { "" };

	public static OntologyData instance;

	public static OntologyData getInstance() {
		if (instance == null) {
			instance = new OntologyData();
		} else {
			instance.dbHelper = SentenceMakingActivity.dbHelper;
			instance.database = instance.dbHelper.getReadableDatabase();
		}
		return instance;
	}

	private OntologyData() {
		// TODO Auto-generated constructor stub
		this.dbHelper = SentenceMakingActivity.dbHelper;
		this.database = dbHelper.getReadableDatabase();
	}

	private String[] cursorStringToArray(Cursor cursor) {
		List<String> list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		cursor.close();
		return list.toArray(new String[list.size()]);
	}

	private int[] cursorIntToArray(Cursor cursor) {
		List<Integer> list = new ArrayList<Integer>();
		while (cursor.moveToNext()) {
			list.add(cursor.getInt(0));
		}
		cursor.close();
		// List to Array
		int[] ints = new int[list.size()];
		for (int i = 0, len = list.size(); i < len; i++) {
			ints[i] = list.get(i);
		}
		return ints;
	}

	// ////////////////////////////////////////
	// //Find Answer
	// ///////////////////////////////////////
	public String[] getMatchedAdjArrayForOneNoun(String noun)
			throws MatchedWordsNotFound {
		columns[0] = dbHelper.C_ADJ;
		selection = dbHelper.C_NOUN + "=?";
		selectArgs[0] = noun;
		groupBy = dbHelper.C_ADJ;
		Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, selection,
				selectArgs, groupBy, null, null, null);
		if (cursor.getCount() > 0) {
			return cursorStringToArray(cursor);
		} else {
			cursor.close();
			throw new MatchedWordsNotFound(
					"Can't get Matched Adj Array For One Noun ");
		}
	}

	public String[] getMatchedNounArrayForOneAdj(String adj)
			throws MatchedWordsNotFound {
		columns[0] = dbHelper.C_NOUN;
		selection = dbHelper.C_ADJ + "=?";
		selectArgs[0] = adj;
		groupBy = dbHelper.C_NOUN;
		Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, selection,
				selectArgs, groupBy, null, null, null);
		if (cursor.getCount() > 0) {
			return cursorStringToArray(cursor);
		} else {
			cursor.close();
			throw new MatchedWordsNotFound(
					"Can't get Matched Noun Array For One Adj ");
		}
	}

	// ////////////////////////////////////////
	// //Find teach
	// ///////////////////////////////////////
	public String getOneRandomAdjForOneNoun(String noun)
			throws MatchedWordsNotFound {

		String[] adjArray = getMatchedAdjArrayForOneNoun(noun);
		return adjArray[RandomUtil.getRandomInteger(0, adjArray.length - 1)];

	}

	public String getOneRandomNounForOneAdj(String adj)
			throws MatchedWordsNotFound {

		String[] nounArray = getMatchedNounArrayForOneAdj(adj);
		return nounArray[RandomUtil.getRandomInteger(0, nounArray.length - 1)];
	}

	public String getOneRandomNoun() {
		String sql = "SELECT " + dbHelper.C_NOUN + " FROM "
				+ dbHelper.TABLE_NAME + " ORDER BY RANDOM() LIMIT 1";
		Cursor cursor = database.rawQuery(sql, null);
		cursor.moveToNext();
		return cursor.getString(0);
	}

	public String getOneRandomAdj() {
		String sql = "SELECT " + dbHelper.C_ADJ + " FROM "
				+ dbHelper.TABLE_NAME + " ORDER BY RANDOM() LIMIT 1";
		Cursor cursor = database.rawQuery(sql, null);
		cursor.moveToNext();
		return cursor.getString(0);
	}

	public int getOnePhotoIdOfOneNoun(String noun) throws PhotoIdsNotFound {
		columns[0] = dbHelper.C_PHOTO_ID;
		selection = dbHelper.C_NOUN + "=?";
		selectArgs[0] = noun;
		groupBy = dbHelper.C_NOUN;
		Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, selection,
				selectArgs, groupBy, null, null, null);
		if (cursor.getCount() > 0) {
			Log.w(getClass().getSimpleName(), "photo id found for one noun:"
					+ noun);
			cursor.moveToNext();
			return cursor.getInt(0);
		} else {
			Log.w(getClass().getSimpleName(),
					"Not found photo id for one noun:" + noun);
			throw new PhotoIdsNotFound("Not found photo id for one noun:"
					+ noun);
		}
	}

	public int[] getPhotoIdsOfOneAdj(String adj) throws PhotoIdsNotFound {

		String sql = "SELECT " + dbHelper.C_PHOTO_ID + " FROM "
				+ dbHelper.TABLE_NAME + " Where " + dbHelper.C_ADJ + "=?"
				+ "GROUP BY " + dbHelper.C_PHOTO_ID
				+ " ORDER BY RANDOM() LIMIT 6";
		selectArgs[0] = adj;
		Cursor cursor = database.rawQuery(sql, selectArgs);

		if (cursor.getCount() > 0) {
			Log.w(getClass().getSimpleName(), "photo ids found for one adj:"
					+ adj);
			return cursorIntToArray(cursor);
		} else {
			Log.w(getClass().getSimpleName(),
					"Not found photo ids for one adj:" + adj);
			throw new PhotoIdsNotFound("Not found photo id for one adj:" + adj);
		}
	}

}
