package com.asus.photos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asus.activity.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PhotosArrayAdapter extends ArrayAdapter<OnePhoto>{
	
	private List<OnePhoto> photos=new ArrayList<OnePhoto>();
	private ImageView hintPhoto;
	private TextView hintText;
	private GridView gridView;
	
	
	public PhotosArrayAdapter(Context context,int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void add(OnePhoto object) {
		// TODO Auto-generated method stub
		super.add(object);
		photos.add(object);
		if(gridView!=null){
			setColumns(getCount());
		}
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
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
		photos.clear();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//inflate the convertview
		if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.photo_griditem_view, parent, false);
            
		}
		//set column
		gridView=(GridView)parent;
		setColumns(getCount());
		
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
	
	private void setColumns(int count){
		if(count>=3){
			gridView.setNumColumns(3);
			Log.w(getClass().getSimpleName(), "getView called : set column"+count);
		}else{
			gridView.setNumColumns(count);
			Log.w(getClass().getSimpleName(), "getView called : set column"+count);
		}
	}
	

}
