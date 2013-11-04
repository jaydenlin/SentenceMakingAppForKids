package com.asus.activity;

import java.util.ArrayList;
import java.util.Random;

import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;

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
	
	protected static final String TAG = SentenceMakingActivity.class.getSimpleName();
	private DiscussArrayAdapter adapter;
	private ListView listView;
	private LoremIpsum ipsum;
	private ImageButton answerButton;
	private static Random random;
	protected static final int RESULT_SPEECH = 1;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sentence_making_activity);
		random = new Random();
		ipsum = new LoremIpsum();
		
		listView = (ListView) findViewById(R.id.chatListView);
		
		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.bubble_listitem_view);
		listView.setAdapter(adapter);
		
		answerButton = (ImageButton) findViewById(R.id.answerButton);
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
		
		addItems();
		
		ImageView sentencePhoto = (ImageView) findViewById(R.id.sentence_photo); 
		Animation animation = AnimationUtils.loadAnimation(this, R.layout.quiz_animation);
		sentencePhoto.setAnimation(animation);
		animation.start();
		
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
	
	private void addItems() {
		adapter.add(new OneComment(true, "____的蘋果"));

//		for (int i = 0; i < 6; i++) {
//			boolean left = getRandomInteger(0, 1) == 0 ? true : false;
//			int word = getRandomInteger(1, 10);
//			int start = getRandomInteger(1, 40);
//			String words = ipsum.getWords(word, start);
//
//			adapter.add(new OneComment(left, words));
//		}
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
		String[] keywords= {"紅的","紅色","圓形","美麗","圓的","美麗的","美的","大的","好吃的","碩大","黃的","綠的","難吃","小的","香的"};
		for(int i=0;i<keywords.length;i++){	
			if (answer.indexOf(keywords[i]) != -1){ 
	            //System.out.println(str);
				adapter.add(new OneComment(true, answer+"! 造詞造得不錯哦!好棒!"));
				isFail = false;
				break;
			}else{
				
			}
		}
		
		if(isFail){
			adapter.add(new OneComment(true, answer+"? 再想想看有沒有更好的詞"));
		}
	}

}