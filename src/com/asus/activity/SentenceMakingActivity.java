package com.asus.activity;

import java.util.ArrayList;
import java.util.Random;

import com.asus.atc.dialogservice.DMListener;
import com.asus.atc.dialogservice.DMResult;
import com.asus.atc.dialogservice.DialogServiceConnector;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.DBHelper;
import com.asus.data.OntologyData;
import com.asus.dialogue.InputDispatcher;
import com.asus.dialogue.Question;
import com.asus.photos.OnePhoto;
import com.asus.photos.PhotosArrayAdapter;
import com.asus.util.RandomUtil;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class SentenceMakingActivity extends Activity{

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

	private void initView() {
		chatListView = (ListView) findViewById(R.id.chatListView);
		photoGridView = (GridView) findViewById(R.id.photoGridView);
		answerButton = (ImageButton) findViewById(R.id.answerButton);
		setUI();
	}

	private void setUI() {

		answerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogServiceConnector.startCSR();
			}
		});

//		sentenceAnimation = AnimationUtils.loadAnimation(this,
//				R.layout.quiz_animation);
//		sentencePhoto.setAnimation(sentenceAnimation);

	}

	// /////////////////////
	// /LifeCycle
	// /////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.sentence_making_activity);
		initView();
		
		question = Question.getInstance();
		
		
		Log.w(getClass().getSimpleName(), "onCreate");
		this.deleteDatabase("ontology.db");
		dbHelper = new DBHelper(this);
		ontologyData = OntologyData.getInstance();
		
		
		dialogServiceConnector = new DialogServiceConnector(this);
		final DMListener dmListener = new DMListener() {
			public void onResult(final DMResult dmResult) {
				if (dmResult != null) {
					String text = dmResult.getText().trim();
					adapter.add(new OneComment(false, text));
					InputDispatcher.getInstance(question, text, adapter,photosArrayAdapter).start();
				} else {
				}
			}

			public void onConnected() {
				try {
					Thread.sleep(2000);
					dialogServiceConnector
							.responseToUser("歡迎來到造句遊戲，可愛的小朋友，可以開始玩造句囉");
					adapter = new BubblesArrayAdapter(getApplicationContext(),
							R.layout.bubble_listitem_view,
							dialogServiceConnector);
					chatListView.setAdapter(adapter);
					
					photosArrayAdapter = new PhotosArrayAdapter(getApplicationContext(), R.layout.photo_griditem_view);
					photoGridView.setAdapter(photosArrayAdapter);
					
					addRandomQuestion();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		dialogServiceConnector.setSpeechListener(dmListener);
		
		/////////////
		//TEMP
		////////////
		
		
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
		
		//OntologyDataDB ontologyDataDB = OntologyDataDB.getInstance(dbHelper);

	}

	// /////////////////////
	// /Methods
	// /////////////////////
	private void addRandomQuestion() {

		// randomly ask adj or noun
		if (RandomUtil.getRandomInteger(0, 1) == 0 ? true : false) {
			question.questionPhrase = ontologyData.getOneRandomNoun();
			question.isAskingAdj = true;
			adapter.add(new OneComment(true, "試試看這個句子。  ______的"
					+ question.questionPhrase));
			photosArrayAdapter.add(new OnePhoto(ontologyData.getOnePhotoIdOfOneNoun(question.questionPhrase), ""));
		} else {
			question.questionPhrase = ontologyData.getOneRandomAdj();
			question.isAskingAdj = false;
			adapter.add(new OneComment(true, "試試看這個句子。  "
					+ question.questionPhrase + "______"));
			
			int[] photoIdArray=ontologyData.getPhotoIdsOfOneAdj(question.questionPhrase);
			for(int i=0;i<photoIdArray.length;i++){
				photosArrayAdapter.add(new OnePhoto(photoIdArray[i], ""));
			}
		}
	}


}
