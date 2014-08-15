package com.messi.geeknote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.messi.geeknote.util.LogUtil;

public class MainFragment extends Fragment {

	private View view;
	
	private Bundle bundle;
	public static boolean isRefresh;
	private static MainFragment mMainFragment;
	
	public static MainFragment getInstance(Bundle bundle){
		if(mMainFragment == null){
			mMainFragment = new MainFragment();
			mMainFragment.bundle = bundle;
		}
		return mMainFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.DefalutLog("MainFragment-onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtil.DefalutLog("MainFragment-onCreateView");
		view = inflater.inflate(R.layout.activity_main, null);
		init();
		return view;
	}
	
	private void init() {
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.DefalutLog("MainFragment-onDestroy");
	}
	
}
