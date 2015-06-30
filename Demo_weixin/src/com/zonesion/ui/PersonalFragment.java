package com.zonesion.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zonesion.tool.R;

public class PersonalFragment extends Fragment{
	// 构造方法
	public  PersonalFragment() {
		System.out.println("实例化了PersonalFragment");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 生成fragment视图的布局
		View v = inflater.inflate(R.layout.personal_fragment, container, false);
		// 将生成的视图返回给托管activity
		return v;

	}

}
