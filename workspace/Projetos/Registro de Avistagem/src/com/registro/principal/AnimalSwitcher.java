package com.registro.principal;


import com.registro.principal.R;


import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;
import android.widget.Gallery.LayoutParams;

public class AnimalSwitcher extends Activity implements ViewFactory{
	private ImageSwitcher mISwitcher;  
	private Integer[] imagesIDs = {R.drawable.pic1, R.drawable.pic2};
	//private Gallery gallery;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		////mISwitcher.setId(R.id.ImageSwitcher01);
		

		mISwitcher = (ImageSwitcher)findViewById(R.id.ImageSwitcher01);
		mISwitcher.setFactory(this);
		// some animation when image changes
		mISwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left));
		mISwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right));         

		
		//gallery.setId(R.id.gallery1);
		Gallery gallery = (Gallery)findViewById(R.id.gallery1);
		
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener(){

			public void	onItemClick(final AdapterView<?> parent, final View v, final int position, final long id){
				mISwitcher.setImageResource(imagesIDs[position]);
				System.out.println(mISwitcher.getChildAt(position));
			}			
		});  
	}	



//	public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
//		try{
//			mISwitcher.setImageResource(imagesIDs[position]);
//			System.out.println(mISwitcher.getChildAt(position).toString());
//		}catch(Exception e){}
//	}


	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}


	public View makeView() {
		ImageView i = new ImageView(null);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.FILL_PARENT,ImageSwitcher.LayoutParams.FILL_PARENT));
		return i; 
	}


	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return imagesIDs.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView galleryview = new ImageView(mContext);
			galleryview.setImageResource(imagesIDs[position]);
			galleryview.setAdjustViewBounds(true);
			galleryview.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			galleryview.setPadding(5, 0, 5, 0);
			galleryview.setBackgroundResource(android.R.drawable.picture_frame);            
			return galleryview; 
		}
	}
}