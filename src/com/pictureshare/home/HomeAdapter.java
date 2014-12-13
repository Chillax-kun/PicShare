package com.pictureshare.home;

import java.util.List;

import com.pictureshare.R;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeAdapter extends BaseAdapter {

	private List<Float> mList = null;
	private Context mContext = null;
	private LayoutInflater inflater;
	
	public HomeAdapter(Context mContext, List<Float> mList){
		this.mContext = mContext;
		this.mList = mList;
		this.inflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList !=null ? mList.size() : 0;
//		return 10;
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
		arg1 =inflater.inflate(R.layout.pic_listview_item, null);
		ImageView img = (ImageView) arg1.findViewById(R.id.pic_listview_item_img);
		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) img.getLayoutParams(); //取控件textView当前的布局参数  
		linearParams.height = mList.get(arg0).intValue();
		img.setLayoutParams(linearParams); 
		return arg1;
	}

}
