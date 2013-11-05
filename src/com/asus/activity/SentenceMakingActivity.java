package com.asus.activity;

import java.util.ArrayList;
import java.util.Random;

import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.dialogue.Question;
import com.asus.ontologyData.OntologyData;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import de.svenjacobs.loremipsum.LoremIpsum;
import android.speech.tts.TextToSpeech;

public class SentenceMakingActivity extends Activity {
	
	////////////////////////
	///Degug
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
		                judge(text.get(0));
		            }
		            break;
		        }

	        }
	 }
	 
	///////////////////////
	///Methods
	///////////////////////		
	private void addRandomQuestion() {
		
//		if(getRandomInteger(0, 1) == 0 ? true : false){
			question.questionPhrase = "蘋果";
			question.isAskingAdj =true;
			adapter.add(new OneComment(true, "______的"+question.questionPhrase));
//		}else{
//			question.questionPhrase = ontologyData.getOneRandomNoun();
//			question.isAskingAdj =false;
//			adapter.add(new OneComment(true, question.questionPhrase+"的______"));
//		}
//		for (int i = 0; i < 6; i++) {
//			boolean left = getRandomInteger(0, 1) == 0 ? true : false;
//			int word = getRandomInteger(1, 10);
//			int start = getRandomInteger(1, 40);
//			String words = ipsum.getWords(word, start);
//
//			adapter.add(new OneComment(left, words));
//		}
	}
	
	private void addDerivedQuestionFormAnwser(String matchedKeyword){
		if(question.isAskingAdj){
			question.isAskingAdj=false;
			question.questionPhrase = matchedKeyword;
			adapter.add(new OneComment(true, question.questionPhrase+"______"));
		}else{
			question.isAskingAdj=true;
			question.questionPhrase = matchedKeyword;
			adapter.add(new OneComment(true, "______的"+question.questionPhrase));
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
	
	private void judge(String answer) {
		
		boolean isFail = true;
		String[] keywords;
		if(question.isAskingAdj){
			keywords=ontologyData.getMatchedAdjArrayForOneNoun(question.questionPhrase);
		}else{
			keywords=ontologyData.getMatchedNounArrayForOneAdj(question.questionPhrase);
		}
		
		if(keywords==null){
			adapter.add(new OneComment(true, answer+"! 嗚嗚~這題我也還沒想到答案呢!我們換一題吧"));
			isFail=false;
			//addRandomQuestion();
		}
		
		//String[] keywords= {"紅的","紅色","圓形","美麗","圓的","美麗的","美的","大的","好吃的","碩大","黃的","綠的","難吃","小的","香的"};
		for(int i=0;i<keywords.length;i++){	
			if (answer.indexOf(keywords[i]) != -1){ 
				adapter.add(new OneComment(true, answer+"! 造詞造得不錯哦!好棒!"));
				addDerivedQuestionFormAnwser(keywords[i]);
				isFail = false;
				break;
			}else{
				
			}
		}
		
		if(isFail){
			adapter.add(new OneComment(true, answer+"? 再想想看有沒有更好的詞"));
			if(question.isAskingAdj){
				String demo= ontologyData.getOneRandomNounForOneAdj(answer);
				if(demo!=null){
					adapter.add(new OneComment(true, answer+"是用在..例如: "+answer+demo));
				};
			}else{
				String demo= ontologyData.getOneRandomAdjForOneNoun(answer);
				if(demo!=null){
					adapter.add(new OneComment(true, answer+"是用在..例如: "+demo+answer));
				};
			}
			
		}
	}

}