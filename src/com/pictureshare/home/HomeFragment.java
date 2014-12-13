package com.pictureshare.home;


import java.util.ArrayList;
import java.util.List;

import com.pictureshare.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HomeFragment extends Fragment {

	private Context mContext;
	private ListView mListViewLeft, mListViewRright;
	private List<Float> mListLeft = new ArrayList<Float>();
	private List<Float> mListRight = new ArrayList<Float>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.main_home, null);
		mListViewLeft = (ListView) view.findViewById(R.id.home_mylistview_left);
		mListViewRright = (ListView) view.findViewById(R.id.home_mylistview_right);
		return view;
	}
	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setData();
	}

	public void setData(){
		mListLeft.add(200.0f);
		mListLeft.add(250.0f);
		mListLeft.add(320.0f);
		mListRight.add(350.0f);
		mListRight.add(300.0f);
		mListRight.add(420.0f);
		HomeAdapter listViewLeft = new HomeAdapter(mContext, mListLeft);
		HomeAdapter listViewRight = new HomeAdapter(mContext, mListRight);
		mListViewLeft.setAdapter(listViewLeft);
		mListViewRright.setAdapter(listViewRight);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
