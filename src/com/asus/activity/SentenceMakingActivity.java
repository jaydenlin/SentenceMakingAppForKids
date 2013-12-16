package com.asus.activity;

import intentionidentifier.datastructure.Data;
import intentionidentifier.datastructure.Intention;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;

import nlgmodule.NLGConstant;
import nlgmodule.NaturalLanguageGenerator;

import com.asus.asyctask.CSRTimeoutCounter;
import com.asus.atc.dialogservice.DMListener;
import com.asus.atc.dialogservice.DMResult;
import com.asus.atc.dialogservice.DialogServiceConnector;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.ctc.speech.recognizer.DaVinciRecognizer.InitializedListener;
import com.asus.data.DBHelper;
import com.asus.data.OntologyData;
import com.asus.dialogue.InputDispatcher;
import com.asus.dialogue.Question;
import com.asus.exception.PhotoIdsNotFound;
import com.asus.photos.OnePhoto;
import com.asus.photos.PhotosArrayAdapter;
import com.asus.remote.RemoteConnection;
import com.asus.util.RandomUtil;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SentenceMakingActivity extends Activity {

	// //////////////////////
	// /Debug
	// //////////////////////
	protected static final String TAG = SentenceMakingActivity.class
			.getSimpleName();

	// //////////////////////
	// /Member
	// //////////////////////
	public static DBHelper dbHelper;
	private OntologyData ontologyData;
	private Question question;
	private LinearLayout wrapper;
	private BubblesArrayAdapter adapter;
	private PhotosArrayAdapter photosArrayAdapter;
	private DialogServiceConnector dialogServiceConnector;
	protected static final int RESULT_SPEECH = 1;

	// /////////////////////
	// /UI
	// /////////////////////
	private ListView chatListView;
	private GridView photoGridView;
	private ImageButton answerButton;
	private TextView textBoard;

	private void initView() {
		wrapper=(LinearLayout)findViewById(R.id.wrapper);
		chatListView = (ListView) findViewById(R.id.chatListView);
		photoGridView = (GridView) findViewById(R.id.photoGridView);
		textBoard=(TextView)findViewById(R.id.textBoard);
		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf");
		textBoard.setTypeface(face);
		answerButton = (ImageButton) findViewById(R.id.answerButton);
		answerButton.setOnClickListener(getAnswerButtonOnClickListener());
	}
	
	// /////////////////////
	// /Listener
	// /////////////////////
	private DMListener getDMListener(){
		return new DMListener() {
			public void onResult(final DMResult dmResult) {
				wrapper.getBackground().setAlpha(255);
				answerButton.setBackgroundResource(R.drawable.btn_normal);
				if (dmResult != null) {
					String text = dmResult.getText().trim();
					adapter.add(new OneComment(false, text));
					InputDispatcher.getInstance(question, text, adapter,photosArrayAdapter).start();
//					Intention intention = dmResult.getIntention();
//					String intentionStr = intention.getIntention();
//					Data data = (Data) intention.getCoreData();
//					if(data==null){
//						Toast.makeText(SentenceMakingActivity.this, "intention :null",  Toast.LENGTH_SHORT).show();
//					}else{
//						String key = data.getKey();
//						Toast.makeText(SentenceMakingActivity.this, "intention :"+intention.getIntention(),  Toast.LENGTH_SHORT).show();
//					}

				} 
			}

			public void onConnected() {
				try {
					Thread.sleep(2000);
					dialogServiceConnector.responseToUser("歡迎來到造句遊戲，可愛的小朋友，可以開始玩造句囉");
					setAdapters(dialogServiceConnector);
					addRandomQuestion();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
	}
	
	private View.OnClickListener getAnswerButtonOnClickListener(){
		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogServiceConnector.startCSR();
				//googleTTStart();
				answerButton.setBackgroundResource(R.drawable.btn_pressed);
				wrapper.getBackground().setAlpha(100);
				
				
				
				//new CSRTimeoutCounter().execute(dialogServiceConnector);
				
//				answerButton.setBackgroundResource(R.drawable.btn_frame_list);
//				final AnimationDrawable answerButtonAnimation=(AnimationDrawable)answerButton.getBackground();
//				if(isAnswering){
//					answerButton.post(new Runnable() {
//						
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							answerButtonAnimation.start();
//						}
//					});
//				}
			}
		};
	}
	
	
	private void setAdapters(DialogServiceConnector dialogServiceConnector){
		adapter = new BubblesArrayAdapter(getApplicationContext(),R.layout.bubble_listitem_view,dialogServiceConnector);
		chatListView.setAdapter(adapter);
		
		photosArrayAdapter = new PhotosArrayAdapter(getApplicationContext(), R.layout.photo_griditem_view);
		photoGridView.setAdapter(photosArrayAdapter);
	}
	
	private void setDatabase(){
		this.deleteDatabase("ontology.db");
		dbHelper = new DBHelper(this);
	}
	
	// /////////////////////
	// /LifeCycle
	// /////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.sentence_making_activity);
		initView();
		setDatabase();

		question = Question.getInstance();
		ontologyData = OntologyData.getInstance();
		dialogServiceConnector = new DialogServiceConnector(this);
		dialogServiceConnector.setSpeechListener(getDMListener());
		
		new RemoteConnection().connect("");
		//////
		//Google TTS
		/////
		//init();
		
		
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
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub 
		super.onStart();
		//exportDB();
		//NaturalLanguageGenerator nlg = NaturalLanguageGenerator.getNLG(NLGConstant.);
	}
	
	private void exportDB(){
		File sd = Environment.getExternalStorageDirectory();
	      	File data = Environment.getDataDirectory();
	       FileChannel source=null;
	       FileChannel destination=null;
	       String currentDBPath = "/data/"+ "com.asus.activity" +"/databases/"+"ontology.db";
	       String backupDBPath = "ontology.db";
	       File currentDB = new File(data, currentDBPath);
	       File backupDB = new File(sd, backupDBPath);
	       try {
	            source = new FileInputStream(currentDB).getChannel();
	            destination = new FileOutputStream(backupDB).getChannel();
	            destination.transferFrom(source, 0, source.size());
	            source.close();
	            destination.close();
	            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
	            Log.w(getClass().getSimpleName(), "DB exported");
	        } catch(IOException e) {
	        	e.printStackTrace();
	        	Toast.makeText(this, "DB Exported Failed!!", Toast.LENGTH_LONG).show();
	        	Log.w(getClass().getSimpleName(), "DB exported Fail");
	        }
	}
	// /////////////////////
	// /Methods
	// /////////////////////
	private void addRandomQuestion() {

		// randomly ask adj or noun
		if (RandomUtil.getRandomInteger(0, 1) == 0 ? true : false) {
			//question.questionPhrase = ontologyData.getOneRandomNoun();
			question.questionPhrase = "荔枝";
			question.isAskingAdj = true;
			adapter.add(new OneComment(true, "試試看這個句子。  ______的"
					+ question.questionPhrase));
			//textBoard.setText(question.questionPhrase);
			try {
				photosArrayAdapter.add(new OnePhoto(ontologyData.getOnePhotoIdOfOneNoun(question.questionPhrase), ""));
			} catch (PhotoIdsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//question.questionPhrase = ontologyData.getOneRandomAdj();
			question.questionPhrase = "溫馴的";
			question.isAskingAdj = false;
			adapter.add(new OneComment(true, "試試看這個句子。  "
					+ question.questionPhrase + "______"));
			//textBoard.setText(question.questionPhrase);
			
			int[] photoIdArray;
			try {
				photoIdArray = ontologyData.getPhotoIdsOfOneAdj(question.questionPhrase);
				for(int i=0;i<photoIdArray.length;i++){
					photosArrayAdapter.add(new OnePhoto(photoIdArray[i], ""));
				}
			} catch (PhotoIdsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        switch (requestCode) {
        case RESULT_SPEECH: {
            if (resultCode == RESULT_OK && null != data) {
            	wrapper.getBackground().setAlpha(255);
				answerButton.setBackgroundResource(R.drawable.btn_normal);
                ArrayList<String> text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
 
                adapter.add(new OneComment(false, text.get(0)));
				InputDispatcher.getInstance(question, text.get(0), adapter,photosArrayAdapter).start();
            }
            break;
        }
 
        }
    }
	
	private void init(){
		
		setAdapters(dialogServiceConnector);
		adapter.add(new OneComment(true, "歡迎來到造句遊戲!"));
		addRandomQuestion();
	}
	
	private void googleTTStart(){
		 Intent intent = new Intent(
                 RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

         intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

         try {
             startActivityForResult(intent, RESULT_SPEECH);
         } catch (ActivityNotFoundException a) {
             Toast t = Toast.makeText(getApplicationContext(),
                     "Opps! Your device doesn't support Speech to Text",
                     Toast.LENGTH_SHORT);
             t.show();
         }
	}
}
