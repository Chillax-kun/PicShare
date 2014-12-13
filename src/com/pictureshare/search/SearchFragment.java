package com.pictureshare.search;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.pictureshare.MainActivity;
import com.pictureshare.R;
import com.pictureshare.utils.GetDataThread;
import com.pictureshare.utils.IsNetWork;
import com.pictureshare.utils.PictureShareURL;

public class SearchFragment extends Fragment {
	
	private TableRow searchBarTxt;
	private EditText searchBarEdit;
	private TextView changeTxt;
	private int[] searchWordId = new int[]{R.id.searchfragment_hotword_1,R.id.searchfragment_hotword_2,R.id.searchfragment_hotword_3,
			R.id.searchfragment_hotword_4,R.id.searchfragment_hotword_5};
	private TextView[] searchWord = new TextView[searchWordId.length];
	
	private static int num = -1;
	private List<String> wrodsList = new ArrayList<String>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.main_search, null);
		
		searchBarTxt = (TableRow) view.findViewById(R.id.searchfragment_searchbar_txt);
		searchBarEdit = (EditText) view.findViewById(R.id.searchfragment_searchbar_edit);
		changeTxt = (TextView) view.findViewById(R.id.searchfragment_change_txt);
		
		for(int i = 0;i<searchWord.length;i++){
			searchWord[i] = (TextView) view.findViewById(searchWordId[i]);
			searchWord[i].setOnClickListener(clickListener);
		}
		
		searchBarTxt.setOnClickListener(searchBarClickListener);
		searchBarEdit.setOnFocusChangeListener(changeListener);
		changeTxt.setOnClickListener(clickListener);
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(IsNetWork.isNetWorking(getActivity())){
			changeTxt.setClickable(false);
			String URL = PictureShareURL.PICSHAREHOTWRODS;
			Log.e(MainActivity.E, URL);
			GetDataThread myThread = new GetDataThread(URL, mHandler);
			myThread.start();
		}else{
			Toast.makeText(getActivity(), "网络已断开,请重新连接", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 搜索栏点击事件
	 */
	OnClickListener searchBarClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.searchfragment_searchbar_txt:
				searchBarTxt.setVisibility(View.INVISIBLE);
				searchBarEdit.setVisibility(View.VISIBLE);
				if(searchBarEdit.requestFocus()){
					searchBarEdit.setFocusable(true);
					searchBarEdit.setFocusableInTouchMode(true);
				}
				break;

			default:
				break;
			}
		}
	};
	
	/**
	 * 控件点击事件
	 */
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			searchBarEdit.clearFocus();

			switch (view.getId()) {
			case R.id.searchfragment_change_txt:
//				Toast.makeText(getActivity(), "换一换", 0).show();
				
				if(num != 2 && wrodsList.size()>0){
					num = 2;
					for(int i = 0;i<searchWord.length;i++){
						searchWord[i].setText(wrodsList.get(4+i));
					}
				}else{
					if(IsNetWork.isNetWorking(getActivity())){
						changeTxt.setClickable(false);
						String URL = PictureShareURL.PICSHAREHOTWRODS;
						GetDataThread myThread = new GetDataThread(URL, mHandler);
						myThread.start();
					}else{
						Toast.makeText(getActivity(), "网络已断开,请重新连接", Toast.LENGTH_SHORT).show();
					}
				}
				break;
				
			case R.id.searchfragment_hotword_1:
			case R.id.searchfragment_hotword_2:
			case R.id.searchfragment_hotword_3:
			case R.id.searchfragment_hotword_4:
			case R.id.searchfragment_hotword_5:
				if(view instanceof TextView){
					String word = ((TextView)view).getText().toString();
					Toast.makeText(getActivity(), word, 0).show();
				}
				break;

			default:
				break;
			}
		}
	};
	
	/**
	 * 搜索栏状态切换
	 */
	OnFocusChangeListener changeListener = new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View view, boolean status) {
			// TODO Auto-generated method stub
			if(status){
				searchBarTxt.setVisibility(View.INVISIBLE);
				searchBarEdit.setVisibility(View.VISIBLE);
			}else{
				searchBarTxt.setVisibility(View.VISIBLE);
				searchBarEdit.setVisibility(View.GONE);
			}
		}
	};
	
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			changeTxt.setClickable(true);
			
			switch (msg.what) {
			case -1:
				Log.e(MainActivity.E, msg.obj.toString());
				Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;
			case 1:
				
				Log.e(MainActivity.E, msg.obj.toString());
				try {
					JSONArray jsonArray = new JSONArray(msg.obj.toString());
					for(int i = 0;i<jsonArray.length();i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String word = jsonObject.getString("word");
						wrodsList.add(word);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.obj = "网络连接错误";
					msg.what = -1;
					mHandler.sendMessage(msg);
				}
				
				Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				num = 1;
				for(int i = 0;i<searchWord.length;i++){
					searchWord[i].setText(wrodsList.get(i));
				}
				break;

			default:
				break;
			}
		}
	};
			 
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
	}
	
	

}
