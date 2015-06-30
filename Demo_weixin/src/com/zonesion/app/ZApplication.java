package com.zonesion.app;

import java.util.ArrayList;

import com.zhiyun360.wsn.droid.WSNHistory;
import com.zhiyun360.wsn.droid.WSNRTConnect;
import com.zhiyun360.wsn.droid.WSNRTConnectListener;
import com.zonesion.tool.UserConfig;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

public class ZApplication extends Application {
	/** 用户数据处理工具类实例 */
	private UserConfig mUserConfig;
	public  WSNRTConnect wRTConnect;
	public  WSNHistory wHistory;
	
	public WSNRTConnect getWSNRConnect() {
		if (wRTConnect == null) {
			wRTConnect = new WSNRTConnect();
		}
		return wRTConnect;
	}
	
	public WSNHistory getWSNHistory(){
		if(wHistory == null){
			wHistory = new WSNHistory();
		}
		return wHistory;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mUserConfig = new UserConfig(getApplicationContext());
		wRTConnect = getWSNRConnect();
		wRTConnect.setRTConnectListener(new WSNRTConnectListener() {
			
			@Override
			public void onMessageArrive(String mac, byte[] dat) {
				for (IOnSensorDataListener li : mIOnSensorDataListeners) {
					li.onSensorData(mac, dat);
				}
			}
			
			@Override
			public void onConnectLost(Throwable arg0) {
				Toast.makeText(ZApplication.this, "断开连接", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onConnect() {
				// TODO Auto-generated method stub
				Toast.makeText(ZApplication.this, "连接网关成功", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/** 传感器数据监听器数组 */
	public ArrayList<IOnSensorDataListener> mIOnSensorDataListeners = new ArrayList<IOnSensorDataListener>();

	/**
	 * 注册传感器数据监听器
	 */
	public void registerOnSensorDataListener(IOnSensorDataListener li) {
		mIOnSensorDataListeners.add(li);
	}

	/**
	 * 取消注册传感器数据监听器
	 */
	public void unregisterOnSensorDataListener(IOnSensorDataListener li) {
		mIOnSensorDataListeners.remove(li);
	}

	/**
	 * 获取用户配置工具类实例
	 * 
	 * @return 用户配置工具类实例
	 */
	public UserConfig getUserConfig() {
		return mUserConfig;
	}
	
	@SuppressLint("DefaultLocale")
	public String getSensorData(String mac) {
		mac = mac.toUpperCase();	
		return mUserConfig.getString(mac);
	}
}
