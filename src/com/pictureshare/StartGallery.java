package com.pictureshare;

import java.util.ArrayList;
import java.util.List;

import com.pictureshare.utils.GalleryViewPager;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class StartGallery extends Activity {

	private GalleryViewPager imgGallery;
	private List<String> imgURL;
	private List<String> imgTitle;
	private List<ImageView> galleryImageView;
	private int currentItem = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.start_gallery_activity);
		
		Intent mIntent =  getIntent();
		imgURL = mIntent.getStringArrayListExtra("img");
		imgTitle = mIntent.getStringArrayListExtra("title");
		
		int w = mIntent.getIntExtra("w", -1);
		int h = mIntent.getIntExtra("h", -1);
		
		
		galleryImageView = new ArrayList<ImageView>();
		
		
		if(imgURL!=null){
			for(int i = 0;i<imgURL.size();i++){
				ImageView imageView = new ImageView(this);
				imageView.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));
				imageView.setAdjustViewBounds(true);
				if(w!=-1 && h!=-1){
					Picasso.with(getBaseContext()).load(imgURL.get(i)).resize(w, h).into(imageView);
				}else{
					Picasso.with(getBaseContext()).load(imgURL.get(i)).into(imageView);
				}
				galleryImageView.add(imageView);
			}
			
			mHandler.postDelayed(mRunnable, 2000);
			
		}
		
		imgGallery = (GalleryViewPager) findViewById(R.id.start_gallery_id);
		imgGallery.setAdapter(new MyAdapter());
		imgGallery.setOffscreenPageLimit(3);
		imgGallery.setClickable(false);
		
		imgGallery.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private Runnable mRunnable = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			currentItem = (currentItem + 1) % galleryImageView.size();
			if(currentItem != 0){
				mHandler.obtainMessage().sendToTarget();
				mHandler.postDelayed(mRunnable, 2000);
			}else{
				Intent intent = new Intent(StartGallery.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	};
	
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			imgGallery.setCurrentItem(currentItem);
		}
	};

	
	class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return galleryImageView != null ? galleryImageView.size() : 0;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(galleryImageView.get(arg1));
			return galleryImageView.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
	}
	
	
}
