package com.app.bubbles;

import java.util.ArrayList;
import java.util.List;

import com.app.activity.R;
import com.asus.atc.dialogservice.DialogServiceConnector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BubblesArrayAdapter extends ArrayAdapter<OneComment> {

        private TextView countryName;
        private List<OneComment> countries = new ArrayList<OneComment>();
        private LinearLayout wrapper;
        private DialogServiceConnector dialogServiceConnector;
        
        @Override
        public void add(OneComment object) {
                if(countries.size()>=3){
                        countries.remove(0);
                }
                countries.add(object);
                
                if(object.left&&object.comment.indexOf("的意思應該是")==-1){
                      dialogServiceConnector.responseToUser(object.comment);
                }
                super.add(object);
        }

        public BubblesArrayAdapter(Context context, int textViewResourceId,DialogServiceConnector dialogServiceConnector) {
                super(context, textViewResourceId);
                this.dialogServiceConnector = dialogServiceConnector;
        }
        
        @Override
        public int getCount() {
                return this.countries.size();
        }
        
        @Override
        public OneComment getItem(int index) {
                return this.countries.get(index);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View row = convertView;
                if (row == null) {
                        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        row = inflater.inflate(R.layout.bubble_listitem_view, parent, false);
                }
                wrapper = (LinearLayout) row.findViewById(R.id.wrapper);
                OneComment coment = getItem(position);
                countryName = (TextView) row.findViewById(R.id.comment);
                //set up properties
                Typeface face = Typeface.createFromAsset(getContext().getAssets(),"fonts/appfont.ttf");
                countryName.setTypeface(face);
                countryName.setText(coment.comment);
                countryName.setBackgroundResource(coment.left ? R.drawable.bubble_white : R.drawable.bubble_blue);
                wrapper.setGravity(coment.left ? Gravity.LEFT : Gravity.RIGHT);

                return row;
        }
        
        public Bitmap decodeToBitmap(byte[] decodedByte) {
                return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }

}