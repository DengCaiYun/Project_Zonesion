package com.zonesion.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.zonesion.tool.ChangeColorIconWithTextView;
import com.zonesion.tool.R;
import com.zonesion.ui.AboutFragment;
import com.zonesion.ui.PersonalFragment;
import com.zonesion.ui.TemperatureFragment;
import com.zonesion.ui.TestFragment;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {
	private ViewPager mViewPager;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private TemperatureFragment temperatureFragment;

	private List<ChangeColorIconWithTextView> mFragmentIndicator = new ArrayList<ChangeColorIconWithTextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        if(width < height){   //手机，竖屏
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{    //平板，横屏
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
		setContentView(R.layout.main);

		setOverflowShowingAlways(); // 显示ActionBar导航栏的Overflow按钮
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true); // 使用程序图标作为home icon

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		// 初始化fragment及tab
		initDatas();

		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOffscreenPageLimit(3);
	}

	private void initDatas() {

		/*
		 * 实例化fragment
		 */
		temperatureFragment = new TemperatureFragment();
		TestFragment testFragment = new TestFragment();
		AboutFragment aboutFragment = new AboutFragment();
		PersonalFragment personalFragment = new PersonalFragment();

		mFragments.add(temperatureFragment);
		mFragments.add(testFragment);
		mFragments.add(aboutFragment);
		mFragments.add(personalFragment);

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				System.out.println("调用了getItem方法,加载第" + arg0 + "个fragment");
				return mFragments.get(arg0);
			}
		};

		// 初始化Fragment指示器，即ChangeColorIconWithTextView
		initFragmentIndicator();

	}

	private void initFragmentIndicator() {
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

		mFragmentIndicator.add(one);
		mFragmentIndicator.add(two);
		mFragmentIndicator.add(three);
		mFragmentIndicator.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

		// 设置透明度alpha
		one.setIconAlpha(1.0f);
	}

	@Override
	/*
	 * 页面跳转完后得到调用，arg0是当前页面的position。
	 */
	public void onPageSelected(int arg0) {
		System.out.println("选中页面");
		mFragmentIndicator.get(arg0).setIconAlpha(1.0f);
		// 其他的tab的透明度就设置为0
		resetOtherTabs(arg0);

	}

	@Override
	/*
	 * 参数说明：arg0 :当前页面。arg1:当前页面偏移的百分比。arg2:当前页面偏移的像素位置。 (non-Javadoc)
	 * 方法的作用：当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法会一直得到调用。
	 */
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		System.out.println("滑动页面");
		/*
		 * 实现滑动页面时，ChangeColorIconWithTextView 的透明度随之改变
		 */
		//
		// if (positionOffset > 0) {
		// ChangeColorIconWithTextView left = mFragmentIndicator.get(position);
		// ChangeColorIconWithTextView right = mFragmentIndicator
		// .get(position + 1);
		//
		// left.setIconAlpha(1 - positionOffset);
		// right.setIconAlpha(positionOffset);
		// }

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {

		int position = v.getId();
		// 只要点击了某个tab，其他的tab的透明度就设置为0
		resetOtherTabs(position);

		switch (position) {
		case R.id.id_indicator_one:
			mFragmentIndicator.get(0).setIconAlpha(1.0f);
			// 设置要显示的Fragment视图
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mFragmentIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mFragmentIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			mFragmentIndicator.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;

		}

	}

	/**
	 * 重置其他的Tab的透明度设置为0,当前tab的透明度为1
	 */
	private void resetOtherTabs(int position) {
		// mFragmentIndicator.get(position).setIconAlpha(1.0f);
		for (int i = 0; i < mFragmentIndicator.size(); i++) {
			if (i != position) {
				mFragmentIndicator.get(i).setIconAlpha(0);
			}
		}
	}

	@Override
	/*
	 * 用来显示ActionBar
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// 自定义的menu
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	/*
	 * 某个Action被单击后会调动此方法
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_search:
			Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_scan:
			Toast.makeText(this, "扫一扫", Toast.LENGTH_SHORT).show();
			break;
		case android.R.id.home:
			Toast.makeText(this, "返回至主界面", Toast.LENGTH_SHORT).show();
			resetOtherTabs(0);
			mFragmentIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		}
		return true;
	}

	@Override
	/*
	 * overflow被展开的时候就会回调这个方法，
	 * 通过返回反射的方法将MenuBuilder的setOptionalIconsVisible变量设置为true,
	 * 使得overflow中的Action按钮对应的图标显示出来
	 */
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	/*
	 * 使用反射的方式将sHasPermanentMenuKey的值设置成false,即使是在有Menu键的手机上，也能让overflow按钮显示出来
	 */
	private void setOverflowShowingAlways() {
		try {
			// 设备是否有物理menu键
			// true if a permanent menu key is present, false otherwise.
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
//		TemperatureFragment.wRTConnect.disconnect();
//		TemperatureFragment.wRTConnect.freeZCloud();
		temperatureFragment.getWSNRTConnect().disconnect();
		temperatureFragment.getWSNRTConnect().freeZCloud();
		super.onDestroy();
	}

}
