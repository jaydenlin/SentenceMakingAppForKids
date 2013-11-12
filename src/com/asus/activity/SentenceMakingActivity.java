package com.asus.activity;

import java.util.ArrayList;
import java.util.Random;

import com.asus.atc.dialogservice.DMListener;
import com.asus.atc.dialogservice.DMResult;
import com.asus.atc.dialogservice.DialogServiceConnector;
import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.OntologyData;
import com.asus.dialogue.DBHelper;
import com.asus.dialogue.DialogueDispather;
import com.asus.dialogue.Question;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.asus.atc.dialogservice.*;

import core.ckip.datastructure.SPO;
import core.ckip.datastructure.Sentence;

public class SentenceMakingActivity extends Activity {
        
        ////////////////////////
        ///Debug
        ////////////////////////
        protected static final String TAG = SentenceMakingActivity.class.getSimpleName();
        
        ////////////////////////
        ///Member
        ////////////////////////
        private static Random random;
        private OntologyData ontologyData;
        private Question question;
        private Animation sentenceAnimation;
        private DiscussArrayAdapter adapter;
        private DialogServiceConnector dialogServiceConnector;
        
        protected static final int RESULT_SPEECH = 1;
        
        ///////////////////////
        ///UI
        ///////////////////////
        private ListView chatListView;
        private ImageButton answerButton;
        ImageView sentencePhoto;
        private void initView(){
                chatListView = (ListView) findViewById(R.id.chatListView);
                answerButton = (ImageButton) findViewById(R.id.answerButton);
                sentencePhoto = (ImageView) findViewById(R.id.sentence_photo); 
                setUI();
        }
        
        private void setUI(){
                
                answerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                                 Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                     intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
//                        try {
//                            startActivityForResult(intent, RESULT_SPEECH);
//                        } catch (ActivityNotFoundException a) {
//                            Toast t = Toast.makeText(getApplicationContext(),
//                                    "Opps! Your device doesn't support Speech to Text",
//                                    Toast.LENGTH_SHORT);
//                            t.show();
//                        }
                                //dialogServiceConnector.bindService();
                                dialogServiceConnector.startCSR();
                        }
                });
                
                
                sentenceAnimation = AnimationUtils.loadAnimation(this, R.layout.quiz_animation);
                sentencePhoto.setAnimation(sentenceAnimation);
                
        }
        ///////////////////////
        ///LifeCycle
        ///////////////////////        
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.sentence_making_activity);
                initView();
                
                random = new Random();
                ontologyData=OntologyData.getInstance();
                question=Question.getInstance();
                
                sentenceAnimation.start();
                
                //this.deleteDatabase("ontology.db");
                
                ////db start
                DBHelper dbHelper = new DBHelper(this);
                //SQLiteDatabase db = dbHelper.getWritableDatabase();
//        
//                ContentValues values = new ContentValues();

                //db.insertOrThrow(dbHelper.TABLE_NAME, null, values);
                //db end
                dialogServiceConnector = new DialogServiceConnector(this);
                final DMListener dmListener= new DMListener(){
                        public void onResult(final DMResult dmResult) {
                        if (dmResult != null) {
                            String text = dmResult.getText().trim();
                            adapter.add(new OneComment(false, text));
                        DialogueDispather.getInstance(question, text, adapter).start();
                            
                        } else {
                        }
                }

                public void onConnected() {
                        try {
                                        Thread.sleep(2000);
                                        dialogServiceConnector.responseToUser("歡迎來到造句遊戲，可愛的小朋友，可以開始玩造句囉");
                                        adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.bubble_listitem_view,dialogServiceConnector);
                                        chatListView.setAdapter(adapter);
                                addRandomQuestion();
                                } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                        
                }
                        
                        
                };
                dialogServiceConnector.setSpeechListener(dmListener);
                
                
                
                //DMListener dmListener= new DMListener();
                
                
                
        }
        
         @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                switch (requestCode) {
                        case RESULT_SPEECH: {
                            if (resultCode == RESULT_OK && null != data) {
        
                                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                                //add answers to the screen
                                adapter.add(new OneComment(false, text.get(0)));
                                DialogueDispather.getInstance(question, text.get(0), adapter).start();
                                //judge(text.get(0));
                            }
                            break;
                        }

                }
         }
         
        @Override
        protected void onResume() {
                // TODO Auto-generated method stub
                super.onResume();
                dialogServiceConnector.bindService();
        }
        
        protected void onDestroy() {
                super.onDestroy();
                dialogServiceConnector.releaseService();
        }
        
        ///////////////////////
        ///Methods
        ///////////////////////                
        private void addRandomQuestion() {
                
                //randomly ask adj or noun
                if(getRandomInteger(0, 1) == 0 ? true : false){
                        question.questionPhrase = ontologyData.getOneRandomNoun();
                        question.isAskingAdj =true;
                        adapter.add(new OneComment(true, "試試看這個句子。  ______的"+question.questionPhrase));
                }else{
                        question.questionPhrase = ontologyData.getOneRandomAdj();
                        question.isAskingAdj =false;
                        adapter.add(new OneComment(true, "試試看這個句子。  "+question.questionPhrase+"______"));
                }
        }
        
        
        private static int getRandomInteger(int aStart, int aEnd) {
                if (aStart > aEnd) {
                        throw new IllegalArgumentException("Start cannot exceed End.");
                }
                long range = (long) aEnd - (long) aStart + 1;
                long fraction = (long) (range * random.nextDouble());
                int randomNumber = (int) (fraction + aStart);
                return randomNumber;
        }
        
}
