package com.app.dialogue;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.app.bubbles.BubblesArrayAdapter;
import com.app.bubbles.OneComment;
import com.app.data.DBHelper;
import com.app.engine.JudgeEngine;
import com.app.activity.R;

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
                    adapter.add(new OneComment(true,"好啦~我相信你。以後你用"+judgeEngine.getWrongAnswer()+question.questionPhrase+"造句，我也算你對啦!"));
                    ContentValues values = new ContentValues();
                    values.put(dbHelper.C_NOUN, question.questionPhrase);
                    values.put(dbHelper.C_ADJ, judgeEngine.getWrongAnswer());
                    values.put(dbHelper.C_PHOTO_ID, R.drawable.apple);
                    database.insertOrThrow(dbHelper.TABLE_NAME, null, values);
                    
            }else{
                    adapter.add(new OneComment(true,"好啦~我相信你。以後你用"+question.questionPhrase+judgeEngine.getWrongAnswer()+"造句，我也算你對啦!"));
                    ContentValues values = new ContentValues();
                    values.put(dbHelper.C_NOUN, judgeEngine.getWrongAnswer());
                    values.put(dbHelper.C_ADJ, question.questionPhrase);
                    values.put(dbHelper.C_PHOTO_ID, R.drawable.apple);
                    database.insertOrThrow(dbHelper.TABLE_NAME, null, values);
            }
    }


}