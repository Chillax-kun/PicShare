package com.pictureshare.picshare;

import java.util.List;

import com.pictureshare.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PicShareAdapter extends BaseAdapter {

	private List<String> mList = null;
	private Context mContext = null;
	private LayoutInflater inflater;
	
	public PicShareAdapter(Context mContext, List<String> mList){
		this.mContext = mContext;
		this.mList = mList;
		this.inflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return mList !=null ? mList.size() : 0;
		return 10;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		arg1 =inflater.inflate(R.layout.picshare_listview_item, null);
		return arg1;
	}

}
