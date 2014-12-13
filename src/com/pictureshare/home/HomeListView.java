package com.pictureshare.home;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pictureshare.R;

public class HomeListView extends LinearLayout {
	
	private Context mContext;
	private View view;
	private ListView mListViewLeft, mListViewRright;
	private List<Float> mListLeft = new ArrayList<Float>();
	private List<Float> mListRight = new ArrayList<Float>();
	
	public HomeListView(Context mContext, AttributeSet attrs) {
		super(mContext, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		view = LayoutInflater.from(mContext).inflate(R.layout.home_scroll_listview, null);
		mListViewLeft = (ListView) view.findViewById(R.id.home_mylistview_left);
		mListViewRright = (ListView) view.findViewById(R.id.home_mylistview_right);
		setData();
	}

	public void setData(){
		mListLeft.add(100.0f);
		mListLeft.add(150.0f);
		mListLeft.add(120.0f);
		mListRight.add(150.0f);
		mListRight.add(100.0f);
		mListRight.add(120.0f);
		HomeAdapter listViewLeft = new HomeAdapter(mContext, mListLeft);
		HomeAdapter listViewRight = new HomeAdapter(mContext, mListRight);
		mListViewLeft.setAdapter(listViewLeft);
		mListViewRright.setAdapter(listViewRight);
	}
	
}
