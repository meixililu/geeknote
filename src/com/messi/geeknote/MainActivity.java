package com.messi.geeknote;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.baidu.mobstat.StatService;
import com.iflytek.cloud.speech.SpeechUser;
import com.messi.geeknote.adapter.MainPageAdapter;
import com.messi.geeknote.adapter.MenuListItemAdapter;
import com.messi.geeknote.views.PagerSlidingTabStrip;


public class MainActivity extends BaseActivity implements OnClickListener {
	
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
	private ViewPager viewPager;
	private PagerSlidingTabStrip indicator;
	private MainPageAdapter mAdapter;
	
	private String[] mPlanetTitles;
	private long exitTime = 0;
	private Bundle bundle;
	private boolean isRespondWX;
	public static int currentIndex = 0;
	public static MainActivity mMainActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.content_frame);
			mMainActivity = this;
			initDatas();
			initViews();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initDatas(){
		setMiddleVolume();
		bundle = getIntent().getExtras();
		SpeechUser.getUser().login(this, null, null, "appid=" + getString(R.string.app_id), null);
	}
	
	private void initViews(){
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
		viewPager = (ViewPager) findViewById(R.id.pager);
		indicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
		mAdapter = new MainPageAdapter(this.getSupportFragmentManager(),bundle,this);
		viewPager.setAdapter(mAdapter);
		viewPager.setOffscreenPageLimit(3);
		indicator.setViewPager(viewPager);
		MenuListItemAdapter adapter = new MenuListItemAdapter(this,mPlanetTitles,mDrawerLayout,mDrawerList);
		mDrawerList.setAdapter(adapter);
        // Set the list's click listener
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
            	mActionBar.setTitle(MainActivity.this.getResources().getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
            	mActionBar.setTitle(MainActivity.this.getResources().getString(R.string.title_more));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,0,0,this.getResources().getString(R.string.menu_share)).setIcon(R.drawable.icon_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:  
			menu();
			StatService.onEvent(this, "1.6_homemenu", "主页左上角菜单", 1);
			break;
		case 0:  
//			toShareActivity();
			StatService.onEvent(this, "1.8_menu_to_share_activity", "去自定义分享页面", 1);
			break;
//		case 1:  
//			toSettingActivity();
//			StatService.onEvent(this, "1.8_menu_to_settings", "语速调节", 1);
//			break;
		}
       return true;
	}
	
	private void menu(){
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
        	mDrawerLayout.closeDrawer(mDrawerList);
        } else {
        	mDrawerLayout.openDrawer(mDrawerList);
        }
	}

	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        menu.findItem(R.drawable.menu_d).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	
	@Override
	public void onClick(View v) {
//		if (v.getId() == R.id.top_menu_btn) {
//			
//		}
	}
	
//	private void toShareActivity(){
//		Intent intent = new Intent(this, ImgShareActivity.class); 
//		startActivity(intent); 
//	}
//	
//	private void toSettingActivity(){
//		Intent intent = new Intent(this, SettingActivity.class); 
//		startActivity(intent); 
//	}
	
	/**
	 * 设置为最大声
	 */
	private void setMiddleVolume(){
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);  
		int max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		int middle = max/2;
		if(currentVolume < middle){
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, middle, 0);  
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			 menu();
			 StatService.onEvent(this, "1.6_xitongmenu", "系统菜单按钮", 1);
			 return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), this.getResources().getString(R.string.exit_program), 0).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}

}
