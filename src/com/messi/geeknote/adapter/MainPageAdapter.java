package com.messi.geeknote.adapter;

import com.messi.geeknote.MainFragment;
import com.messi.geeknote.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MainPageAdapter extends FragmentPagerAdapter {

	public static String[] CONTENT;
	private Bundle bundle;
	
    public MainPageAdapter(FragmentManager fm,Bundle bundle,Context mContext) {
        super(fm);
        this.bundle = bundle;
        CONTENT = new String[] { mContext.getResources().getString(R.string.app_name),
        		mContext.getResources().getString(R.string.title_favorite) };
    }

    @Override
    public Fragment getItem(int position) {
        if( position == 0 ){
        	return MainFragment.getInstance(bundle);
        }else if( position == 1 ){
        	return MainFragment.getInstance(bundle);
        }
        return null;
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position].toUpperCase();
    }
}