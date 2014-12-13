package com.pictureshare.utils;

import android.os.Handler;
import android.os.Message;

public class GetDataThread extends Thread {
	private String URL = "-1";
	private String result = "-1";
	private Handler mHandler = null;
	
	public GetDataThread(String URL,Handler mHandler){
		this.URL = URL;
		this.mHandler = mHandler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		result = InternetConn.pushURL(URL);
		if(result.equals("-1")){
			
			Message msg = new Message();
			msg.obj = "网络连接错误";
			msg.what = -1;
			mHandler.sendMessage(msg);
		}else{
			
			Message msg = new Message();
			msg.obj = result;
			msg.what = 1;
			mHandler.sendMessage(msg);
		}
	}
}
