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
import com.asus.data.OntologyDataDB;
import com.asus.data.loader.OntologyDataLoader;
import com.asus.dialogue.InputDispatcher;
import com.asus.dialogue.Question;
import com.asus.util.RandomUtil;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class SentenceMakingActivity extends Activity implements LoaderCallbacks<Cursor>{

	// //////////////////////
	// /Debug
	// //////////////////////
	protected static final String TAG = SentenceMakingActivity.class
			.getSimpleName();

	// //////////////////////
	// /Member
	// //////////////////////
	private static Random random;
	private OntologyData ontologyData;
	private Question question;
	private Animation sentenceAnimation;
	private BubblesArrayAdapter adapter;
	private DialogServiceConnector dialogServiceConnector;

	protected static final int RESULT_SPEECH = 1;

	// /////////////////////
	// /UI
	// /////////////////////
	private ListView chatListView;
	private ImageButton answerButton;
	ImageView sentencePhoto;

	private void initView() {
		chatListView = (ListView) findViewById(R.id.chatListView);
		answerButton = (ImageButton) findViewById(R.id.answerButton);
		sentencePhoto = (ImageView) findViewById(R.id.sentence_photo);
		setUI();
	}

	private void setUI() {

		answerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Intent intent = new
				// Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				// intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				// "en-US");
				// try {
				// startActivityForResult(intent, RESULT_SPEECH);
				// } catch (ActivityNotFoundException a) {
				// Toast t = Toast.makeText(getApplicationContext(),
				// "Opps! Your device doesn't support Speech to Text",
				// Toast.LENGTH_SHORT);
				// t.show();
				// }
				// dialogServiceConnector.bindService();
				dialogServiceConnector.startCSR();
			}
		});

		sentenceAnimation = AnimationUtils.loadAnimation(this,
				R.layout.quiz_animation);
		sentencePhoto.setAnimation(sentenceAnimation);

	}

	// /////////////////////
	// /LifeCycle
	// /////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sentence_making_activity);
		initView();

		random = new Random();
		ontologyData = OntologyData.getInstance();
		question = Question.getInstance();

		sentenceAnimation.start();
		
		Log.w(getClass().getSimpleName(), "onCreate");
		
		dialogServiceConnector = new DialogServiceConnector(this);
		final DMListener dmListener = new DMListener() {
			public void onResult(final DMResult dmResult) {
				if (dmResult != null) {
					String text = dmResult.getText().trim();
					adapter.add(new OneComment(false, text));
					InputDispatcher.getInstance(question, text, adapter)
							.start();
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
		this.deleteDatabase("ontology.db");
		//getLoaderManager().initLoader(1, null, this);
		adapter = new BubblesArrayAdapter(getApplicationContext(),R.layout.bubble_listitem_view,dialogServiceConnector);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	protected void onDestroy() {
		super.onDestroy();
		//dialogServiceConnector.releaseService();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub 
		super.onStart();
		DBHelper dbHelper = new DBHelper(this);
		OntologyDataDB ontologyDataDB = OntologyDataDB.getInstance(dbHelper);
		//dialogServiceConnector.bindService();

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
		} else {
			question.questionPhrase = ontologyData.getOneRandomAdj();
			question.isAskingAdj = false;
			adapter.add(new OneComment(true, "試試看這個句子。  "
					+ question.questionPhrase + "______"));
		}
	}


	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new OntologyDataLoader(getApplicationContext());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		while(cursor.moveToNext()){
//	
//	adapter.add(new OneComment(true, cursor.getString(0)));
			Log.w(getClass().getSimpleName(), cursor.getString(0));
//	System.out.print(cursor.getString(0));
//	
		}
		cursor.close();
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

}
