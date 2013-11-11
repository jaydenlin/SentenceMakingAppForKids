package com.asus.activity;

import java.util.ArrayList;
import java.util.Random;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.OntologyData;
import com.asus.data.WikiData;
import com.asus.dialogue.DialogueDispather;
import com.asus.dialogue.Question;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import de.svenjacobs.loremipsum.LoremIpsum;

public class SentenceMakingActivity extends Activity {
	
	////////////////////////
	///Debug
	////////////////////////
	protected static final String TAG = SentenceMakingActivity.class.getSimpleName();
	
	////////////////////////
	///Member
	////////////////////////
	private LoremIpsum ipsum;
	private static Random random;
	private OntologyData ontologyData;
	private Question question;
	private Animation sentenceAnimation;
	private DiscussArrayAdapter adapter;
	
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
		});
		
		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.bubble_listitem_view);
		chatListView.setAdapter(adapter);
//		chatListView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
//			
//			@Override
//			public void onChildViewRemoved(View parent, View child) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onChildViewAdded(View parent, View child) {
//				chatListView.post(new Runnable() {
//					@Override
//					public void run() {
//						if(chatListView.getCount()>=1){
//							Log.w("",Integer.toString(chatListView.getCount()));
//							chatListView.setSelection(chatListView.getCount()-1);
//							//chatListView.setStackFromBottom(true);
//							//chatListView.setTranscriptMode(mode)
//						}
//					}
//				});
//				
//			}
//		});
//		
		
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
		ipsum = new LoremIpsum();
		ontologyData=OntologyData.getInstance();
		question=Question.getInstance();
		
		sentenceAnimation.start();
		addRandomQuestion();
		
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
	}

	///////////////////////
	///Methods
	///////////////////////		
	private void addRandomQuestion() {
		
		if(getRandomInteger(0, 1) == 0 ? true : false){
			question.questionPhrase = ontologyData.getOneRandomNoun();
			question.isAskingAdj =true;
			adapter.add(new OneComment(true, "______ªº"+question.questionPhrase));
		}else{
			question.questionPhrase = ontologyData.getOneRandomAdj();
			question.isAskingAdj =false;
			adapter.add(new OneComment(true, question.questionPhrase+"______"));
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