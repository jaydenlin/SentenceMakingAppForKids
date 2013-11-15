package com.asus.photos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asus.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PhotosArrayAdapter extends ArrayAdapter<OnePhoto>{
	
	private List<OnePhoto> photos=new ArrayList<OnePhoto>();
	private ImageView hintPhoto;
	private TextView hintText;
	
	
	public PhotosArrayAdapter(Context context,int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void add(OnePhoto object) {
		// TODO Auto-generated method stub
		super.add(object);
		photos.add(object);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return photos.size();
	}

	@Override
	public OnePhoto getItem(int position) {
		// TODO Auto-generated method stub
		return photos.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//inflate the convertview
		if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.photo_griditem_view, parent, false);
            //set column
    		setColumns(getCount(), (GridView)parent);
		}
		
		//get item data
		OnePhoto onePhoto=getItem(position);
		//get view
		hintPhoto = (ImageView)convertView.findViewById(R.id.hint_photo);
		hintText =(TextView)convertView.findViewById(R.id.hint_text);
		//setting
		hintPhoto.setBackgroundResource(onePhoto.photoId);
		hintText.setText(onePhoto.phototext);
		setWidthAndHeight(getCount(), hintPhoto, hintText);
		
		return convertView;
	}
	
	private void setColumns(int count,GridView gridView){
		if(count>=6){
			gridView.setNumColumns(3);
		}else{
			gridView.setNumColumns(count);
		}
	}
	
	private void setWidthAndHeight(int count,ImageView imageView,TextView textView){
		if(count==1){
			imageView.setLayoutParams(new LinearLayout.LayoutParams(R.dimen.hint_photo_height_big, R.dimen.hint_photo_width_big));
		}else if(count==2){
			imageView.setLayoutParams(new LinearLayout.LayoutParams(R.dimen.hint_photo_height_mid, R.dimen.hint_photo_width_mid));
		}else{
			imageView.setLayoutParams(new LinearLayout.LayoutParams(R.dimen.hint_photo_height_small, R.dimen.hint_photo_width_small));
		}
	}
	
	

	

}
