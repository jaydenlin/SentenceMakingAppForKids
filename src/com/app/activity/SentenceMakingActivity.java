package com.app.activity;

import intentionidentifier.datastructure.Data;
import intentionidentifier.datastructure.Intention;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections4.MultiMap;

import nlgmodule.NLGConstant;
import nlgmodule.NaturalLanguageGenerator;

import com.app.bubbles.BubblesArrayAdapter;
import com.app.bubbles.OneComment;
import com.app.data.DBHelper;
import com.app.data.OntologyData;
import com.app.dialogue.InputDispatcher;
import com.app.dialogue.Question;
import com.app.exception.PhotoIdsNotFound;
import com.app.photos.OnePhoto;
import com.app.photos.PhotosArrayAdapter;
import com.app.remote.PreparedAnswersList;
import com.app.remote.RemoteCallback;
import com.app.remote.RemoteConnection;
import com.app.util.RandomUtil;
import com.app.activity.R;
import com.asus.atc.dialogservice.DMListener;
import com.asus.atc.dialogservice.DMResult;
import com.asus.atc.dialogservice.DialogServiceConnector;
import com.asus.ctc.speech.recognizer.DaVinciRecognizer.InitializedListener;

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
		wrapper = (LinearLayout) findViewById(R.id.wrapper);
		chatListView = (ListView) findViewById(R.id.chatListView);
		photoGridView = (GridView) findViewById(R.id.photoGridView);
		textBoard = (TextView) findViewById(R.id.textBoard);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/appfont.ttf");
		textBoard.setTypeface(face);
		answerButton = (ImageButton) findViewById(R.id.answerButton);
		answerButton.setOnClickListener(getAnswerButtonOnClickListener());
		textBoard.setOnClickListener(getTextBoardOnClickListener());
	}

	// /////////////////////
	// /Listener
	// /////////////////////
	private DMListener getDMListener() {
		return new DMListener() {
			public void onResult(final DMResult dmResult) {
				wrapper.getBackground().setAlpha(255);
				answerButton.setBackgroundResource(R.drawable.btn_normal);
				if (dmResult != null) {
					String text = dmResult.getText().trim();
					adapter.add(new OneComment(false, text));
					InputDispatcher.getInstance(question, text, adapter,
							photosArrayAdapter).start();

				}
			}

			public void onConnected() {
				try {
					Thread.sleep(2000);
					dialogServiceConnector.setSpeechDomain("google");
					dialogServiceConnector
							.responseToUser("歡迎來到造句遊戲，可愛的小朋友，可以開始玩造句囉");
					setAdapters(dialogServiceConnector);
					addRandomQuestion();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
	}
	
	private View.OnClickListener getTextBoardOnClickListener() {
		
		return new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				remote();
			}
			
			
		};
	};
	
	
	private View.OnClickListener getAnswerButtonOnClickListener() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogServiceConnector.startCSR();
				// googleTTStart();
				answerButton.setBackgroundResource(R.drawable.btn_pressed);
				wrapper.getBackground().setAlpha(100);
			}
		};
	}

	private void setAdapters(DialogServiceConnector dialogServiceConnector) {
		adapter = new BubblesArrayAdapter(getApplicationContext(),
				R.layout.bubble_listitem_view, dialogServiceConnector);
		chatListView.setAdapter(adapter);

		photosArrayAdapter = new PhotosArrayAdapter(getApplicationContext(),
				R.layout.photo_griditem_view);
		photoGridView.setAdapter(photosArrayAdapter);
	}

	private void setDatabase() {
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

		PreparedAnswersList.getInstance().add("你好嗎");
		remote();
		// ////
		// Google TTS
		// ///
		// init();

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
	
	}

	

	// /////////////////////
	// /Methods
	// /////////////////////
	private void addRandomQuestion() {

		// randomly ask adj or noun
		if (RandomUtil.getRandomInteger(0, 1) == 0 ? true : false) {
			// question.questionPhrase = ontologyData.getOneRandomNoun();
			question.questionPhrase = "蘋果";
			question.isAskingAdj = true;
			adapter.add(new OneComment(true, "試試看這個句子。  ______的"
					+ question.questionPhrase));
			// textBoard.setText(question.questionPhrase);
			try {
				photosArrayAdapter.add(new OnePhoto(ontologyData
						.getOnePhotoIdOfOneNoun(question.questionPhrase), ""));
			} catch (PhotoIdsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PreparedAnswersList.getInstance().clear();
			PreparedAnswersList.getInstance().add("紅通通的蘋果");
			PreparedAnswersList.getInstance().add("好吃的蘋果");
			PreparedAnswersList.getInstance().add("好吃的");
			PreparedAnswersList.getInstance().add("老虎");
			PreparedAnswersList.getInstance().add("肯德基");

		} else {
			// question.questionPhrase = ontologyData.getOneRandomAdj();
			question.questionPhrase = "溫馴的";
			question.isAskingAdj = false;
			adapter.add(new OneComment(true, "試試看這個句子。  "
					+ question.questionPhrase + "______"));
			// textBoard.setText(question.questionPhrase);

			int[] photoIdArray;
			try {
				photoIdArray = ontologyData
						.getPhotoIdsOfOneAdj(question.questionPhrase);
				for (int i = 0; i < photoIdArray.length; i++) {
					photosArrayAdapter.add(new OnePhoto(photoIdArray[i], ""));
				}
			} catch (PhotoIdsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PreparedAnswersList.getInstance().clear();
			PreparedAnswersList.getInstance().add("溫馴的小貓");
			PreparedAnswersList.getInstance().add("溫馴的老虎");
			PreparedAnswersList.getInstance().add("溫馴的老鷹");
			PreparedAnswersList.getInstance().add("你好笨");
			PreparedAnswersList.getInstance().add("我好餓噢");
			PreparedAnswersList.getInstance().add("鹹酥雞");

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
				InputDispatcher.getInstance(question, text.get(0), adapter,
						photosArrayAdapter).start();
			}
			break;
		}

		}
	}

	private void init() {

		setAdapters(dialogServiceConnector);
		adapter.add(new OneComment(true, "歡迎來到造句遊戲!"));
		addRandomQuestion();
	}

	private void googleTTStart() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

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

	private void remote() {
		new RemoteConnection("8080").execute(new RemoteCallback() {

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onConnect() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnswerSelected(String selectedAnswer) {
				dialogServiceConnector.responseToUser("你說[[" + selectedAnswer
						+ "]]?");
				InputDispatcher.getInstance(question, selectedAnswer, adapter,
						photosArrayAdapter).start();
			}

			@Override
			public void onDisconnect() {
				// TODO Auto-generated method stub

			}
		});
	}
}
