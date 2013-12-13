package com.asus.dialogue;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.asus.activity.R;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.DBHelper;
import com.asus.engine.JudgeEngine;

public class ConfirmDialogueHandler extends DialogueHandler{
	
	SQLiteDatabase database;
	DBHelper dbHelper;
	public ConfirmDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		DBHelper dbHelper=DBHelper.currentInstance;
		database=DBHelper.currentInstance.getWritableDatabase();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		Question question=Question.getInstance();
		if(question.isAskingAdj){
			adapter.add(new OneComment(true,"�n��~�ڬ۫H�A�C�H��A��"+judgeEngine.getWrongAnswer()+question.questionPhrase+"�y�y�A�ڤ]��A���!"));
			ContentValues values = new ContentValues();
			values.put(dbHelper.C_NOUN, question.questionPhrase);
			values.put(dbHelper.C_ADJ, judgeEngine.getWrongAnswer());
			values.put(dbHelper.C_PHOTO_ID, R.drawable.apple);
			database.insertOrThrow(dbHelper.TABLE_NAME, null, values);
			
		}else{
			adapter.add(new OneComment(true,"�n��~�ڬ۫H�A�C�H��A��"+question.questionPhrase+judgeEngine.getWrongAnswer()+"�y�y�A�ڤ]��A���!"));
			ContentValues values = new ContentValues();
			values.put(dbHelper.C_NOUN, judgeEngine.getWrongAnswer());
			values.put(dbHelper.C_ADJ, question.questionPhrase);
			values.put(dbHelper.C_PHOTO_ID, R.drawable.apple);
			database.insertOrThrow(dbHelper.TABLE_NAME, null, values);
		}
	}


}
