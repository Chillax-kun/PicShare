package com.pictureshare.picshare;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pictureshare.R;
import com.pictureshare.home.HomeAdapter;

public class PicShareFragment extends Fragment {
	
	private Context mContext;
	private ListView mListView;
	private List<String> mList = new ArrayList<String>();
	
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
		View view=inflater.inflate(R.layout.main_picshare, null);
		mListView = (ListView) view.findViewById(R.id.main_picshare_listview_id);
		return view;
	}
	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		PicShareAdapter listViewAdapter = new PicShareAdapter(mContext, mList);
		mListView.setAdapter(listViewAdapter);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
