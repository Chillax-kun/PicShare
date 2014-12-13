package com.pictureshare;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pictureshare.utils.GetDataThread;
import com.pictureshare.utils.IsNetWork;
import com.pictureshare.utils.PictureShareURL;

public class StartActivity extends Activity {

	private ArrayList<String> imgUrl = new ArrayList<String>();
	private ArrayList<String> imgTitle = new ArrayList<String>();
	private LinearLayout startLinear;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.start_activity);
		
		startLinear = (LinearLayout) this.findViewById(R.id.start_activity_linear);
		
//		mHandler.postDelayed(new Runnable(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//				if(IsNetWork.isNetWorking(getBaseContext())){
//					String URL = PictureShareURL.PICSHARENAVIGATION;
//					GetDataThread myThread = new GetDataThread(URL, mHandler);
//					myThread.start();
//				}else{
//					Intent intent = new Intent(StartActivity.this,MainActivity.class);
//					startActivity(intent);
//					finish();
//				}
//				
//			}
//		}, 3000);
		
		if(IsNetWork.isNetWorking(this)){
			String URL = PictureShareURL.PICSHARENAVIGATION;
			GetDataThread myThread = new GetDataThread(URL, mHandler);
			myThread.start();
		}else{
			Toast.makeText(this, "网络已断开,请重新连接", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(StartActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		
	}

	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case -1:
				Toast.makeText(getBaseContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;
			case 1:
				if(msg.obj==null){
					return;
				}
				Toast.makeText(getBaseContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				
				Log.e(MainActivity.E, msg.obj.toString());
				try {
					JSONArray jsonArray = new JSONArray(msg.obj.toString());
					for(int i = 0;i<jsonArray.length();i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String title = jsonObject.getString("title");
						String img = jsonObject.getString("img");
						
						imgUrl.add(img);
						imgTitle.add(title);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message mMessage = new Message();
					mMessage.obj = "网络连接错误";
					mMessage.what = -1;
					mHandler.sendMessage(mMessage);
				}
				
				if(imgUrl.size() > 0){
					Intent intent = new Intent(StartActivity.this,StartGallery.class);
					intent.putStringArrayListExtra("img", imgUrl);
					intent.putStringArrayListExtra("title", imgTitle);
					
					if(startLinear!=null){
						int w = startLinear.getWidth();
						int h = startLinear.getHeight();
						if(w!=0 && h!=0){
							intent.putExtra("w", w);
							intent.putExtra("h", h);
							Log.e(MainActivity.E, "w="+w+",h="+h);
						}
					}
					
					startActivity(intent);
					finish();
//					为什么这里不用onBackPressed()呢,
//					因为这个方法是捕捉返回键的,功能跟finish()是一样的,这里考虑没有返回键的手机,所以finish()
				
				} else {
					Intent intent = new Intent(StartActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
				
				break;

			default:
				break;
			}
		}
		
	};

}
