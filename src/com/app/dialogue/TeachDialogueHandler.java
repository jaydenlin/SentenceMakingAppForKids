package com.app.dialogue;

import com.app.asyctask.AsyncTaskResponse;
import com.app.bubbles.BubblesArrayAdapter;
import com.app.bubbles.OneComment;
import com.app.engine.JudgeEngine;
import com.app.exception.TeachStringResponseNotFound;

public class TeachDialogueHandler extends DialogueHandler{
    
    private String wrongAnswer;
    
    public TeachDialogueHandler(BubblesArrayAdapter adapter) {
            super(adapter);
            // TODO Auto-generated constructor stub
    }

    
    @Override
    public void update(JudgeEngine engine) {
            // TODO Auto-generated method stub
            wrongAnswer=engine.getWrongAnswer();
            try{
                    adapter.add(new OneComment(true, engine.onTeachResponse()));
                    
            }catch(TeachStringResponseNotFound e){
                    //search wiki data
                    engine.searchWikiData(engine.getWrongAnswer(), new AsyncTaskResponse<String>() {
                            @Override
                            public void processFinish(String output) {
                                    // TODO Auto-generated method stub
                                    if(!output.trim().equals("")){
                                            adapter.add(new OneComment(true, wrongAnswer+"的意思應該是..."+output));
                                    }
                            }
                    });
            }
            adapter.add(new OneComment(true, engine.getCurrentQuestion()));
            //notifyDoneCallback.doNextHandler(engine);
    }

}
