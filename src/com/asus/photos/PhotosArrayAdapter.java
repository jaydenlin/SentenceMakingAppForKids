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
import android.widget.ImageView;
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
		}
		//get item data
		OnePhoto onePhoto=getItem(position);
		//get view
		hintPhoto = (ImageView)convertView.findViewById(R.id.hint_photo);
		hintText =(TextView)convertView.findViewById(R.id.hint_text);
		//setting
		hintPhoto.setBackgroundResource(onePhoto.photoId);
		hintText.setText(onePhoto.phototext);
		
		return convertView;
	}
	
	

	

}
