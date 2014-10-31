//package com.registro.principal;
//
//import android.app.ActionBar.LayoutParams;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AnimationUtils;
//import android.widget.BaseAdapter;
//import android.widget.Gallery;
//import android.widget.ImageSwitcher;
//import android.widget.ImageView;
//import android.widget.ViewSwitcher.ViewFactory;
//
//public class BoraAnimal implements ViewFactory{
//
//	private ImageSwitcher mISwitcher;
//	private Gallery gallery;
//	private Integer[] imagesIDs = {R.drawable.pic1, R.drawable.pic2};
//	private Context context;
//
//	
//	public BoraAnimal(Context con){
//		super();
//		context = con;
//		mISwitcher = new ImageSwitcher(context);
//		mISwitcher.setFactory(this);
//		mISwitcher.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
//		mISwitcher.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right));
//	}
////	public BoraAnimal(Context context, Attribute attr){
////		super();
////		
////	}
//	
//	public View makeView() {
//		ImageView i = new ImageView(context);
//		i.setBackgroundColor(0xFF000000);
//		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
//		i.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.FILL_PARENT,ImageSwitcher.LayoutParams.FILL_PARENT));
//		return i;
//	}
//	
//	public class ImageAdapter extends BaseAdapter {
//		private Context mContext;
//
//		public ImageAdapter(Context c){
//			mContext = c;
//		}
//		
//		public int getCount() {
//			return imagesIDs.length;
//		}
//
//		public Object getItem(int position) {
//			return position;
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ImageView galleryView = new ImageView(mContext);
//			galleryView.setImageResource(imagesIDs[position]);
//			galleryView.setAdjustViewBounds(true);
//			galleryView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//			galleryView.setPadding(5, 0, 5, 0);
//			galleryView.setBackgroundResource(android.R.drawable.picture_frame);
//			return galleryView;
//		}
//		
//	}
//
//}
