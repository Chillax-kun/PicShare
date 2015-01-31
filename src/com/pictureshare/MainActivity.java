package com.pictureshare;

import java.util.ArrayList;
import java.util.List;

import com.pictureshare.home.HomeFragment;
import com.pictureshare.picshare.PicShareFragment;
import com.pictureshare.popupmenu.PopupMenu;
import com.pictureshare.search.SearchFragment;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private View mView;
	private FragmentManager mFragmentManager;
	private List<Fragment> mList;
	private TextView mTextView;
	private ImageView img;
	private LinearLayout menuBtn;
	private PopupMenu mPopupMenu;
	
	private int offset = 0;
	private int currIndex = 4;
	private int bmpW;
	public static final String E = "picShare"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		setContentView(R.layout.main);
		if(mView==null){
			mView = LayoutInflater.from(getBaseContext()).inflate(R.layout.main_activity, null);
		}
		setContentView(mView);

		mViewPager = (ViewPager) mView.findViewById(R.id.main_viewpager);
		mTextView = (TextView) mView.findViewById(R.id.main_top_title);
		menuBtn = (LinearLayout) mView.findViewById(R.id.main_top_menu);
		
		InitImageView();
		
		mList = new ArrayList<Fragment>();
		mList.add(new HomeFragment());
		mList.add(new SearchFragment());
		mList.add(new PicShareFragment());
		
		mFragmentManager = getSupportFragmentManager();
		mFragmentManager.popBackStack();
		
		mViewPager.setAdapter(new MyFragmentAdapter(mFragmentManager));
		mViewPager.setOnPageChangeListener(new MyPagerChanger());
		mViewPager.setOffscreenPageLimit(1);
		
		menuBtn.setOnClickListener(click);
	}
	
	private void InitImageView() {
		img = (ImageView) findViewById(R.id.main_navigation_img);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.main_top_line_select)
				.getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 3 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		img.setImageMatrix(matrix);
	}
	
	OnClickListener click = new OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			createOrShowMenu();
		}
	};
	
	private void createOrShowMenu(){
		if(mPopupMenu == null){
			mPopupMenu = new PopupMenu(getBaseContext());
			mPopupMenu.showMenu(R.layout.main_popupmenu);
			mPopupMenu.showAtLocation(menuBtn, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		}else{
			if(mPopupMenu.isShowing()){
				Log.e("Kun", "dismiss");
				mPopupMenu.dismiss();
			}else{
				mPopupMenu.showAtLocation(menuBtn, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
				Log.e("Kun", "show");
			}
		}
	}
	
	private class MyFragmentAdapter extends FragmentStatePagerAdapter {

		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return mList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

	}
	
	private class MyPagerChanger implements OnPageChangeListener{
				
		@Override
		public void onPageScrollStateChanged(int position) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			
			int one = offset * 2 + bmpW;

			Animation animation = null;

			animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);
			currIndex = position;
			animation.setFillAfter(true);
			animation.setDuration(100);
			img.startAnimation(animation);
			
			if(position==0){
				mTextView.setText("图片");
			} else if(position == 1){
				mTextView.setText("搜索");
			} else if(position == 2){
				mTextView.setText("图说");
			}
		}
	};

	private long waitTime = 2000;  
	private long touchTime = 0;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
			
			if(mPopupMenu != null){
				if(mPopupMenu.isShowing()) {
					mPopupMenu.dismiss();
					return true;
				}
			}
			
			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(getBaseContext(), "再按一次退出美图分享", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		Log.e("Kun", "onMenuOpened");
		createOrShowMenu();
	    return false;
	}
}
