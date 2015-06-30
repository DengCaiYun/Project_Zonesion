package com.zonesion.tool;

import java.util.Map;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * UserConfig是用户数据处理工具类：存储、获取用户网关信息以及电话号码信息，这些信息主要是String类型， 故使用SharedPreferences实现了对String类型数据的存储与访问。详细的SharedPreferences介绍及方法使用，参见 {@link ProfilesConfig}
 * 
 * @author Administrator
 * 
 */
@SuppressLint("WorldReadableFiles")
public class UserConfig {
	/** 上下文 */
	private Context mContext;
	/** SharedPreferences实例 */
	private SharedPreferences mSharedPreferences;

	public Map<String, ?> getSPList() {
		return mSharedPreferences.getAll();
	}

	/**
	 * UserConfig构造方法：通过上下文获取到的保存用户数据的SharedPreferences实例
	 * 
	 * @param context
	 *            上下文
	 */
	public UserConfig(Context context) {
		mContext = context;
		// 通过上下文获取到的保存用户数据的SharedPreferences实例
		mSharedPreferences = mContext.getSharedPreferences("_user_config", Context.MODE_WORLD_READABLE);
	}

	/**
	 * 根据key值获取存储在SharedPreferences实例中与key值相对应的String值
	 * 
	 * @param key
	 *            关键字
	 * @return 与key对应的String值
	 */

	public String getString(String key) {
		// 通过SharedPreferences实例来获取于key对应的字符串
		return mSharedPreferences.getString(key, "");
	}

	public String getString(String key, String val) {
		return mSharedPreferences.getString(key, val);
	}

	public Boolean getBoolean(String key) {
		return mSharedPreferences.getBoolean(key, false);
	}

	public Long getLong(String key) {
		return mSharedPreferences.getLong(key, 0);
	}

	/**
	 * 存储key(关键字)-val(String类型值)键值对到SharedPreferences实例中
	 * 
	 * @param key
	 *            关键字
	 * @param val
	 *            String类型值
	 * @return 如果返回true,则存储数据成功；如果返回false，则存储数据失败。
	 */
	public boolean putString(String key, String val) {
		Editor editer = mSharedPreferences.edit();
		editer.putString(key, val);
		return editer.commit();
	}

	public boolean putBoolean(String key, Boolean val) {
		Editor editer = mSharedPreferences.edit();
		editer.putBoolean(key, val);
		return editer.commit();
	}

	public boolean putLong(String key, Long val) {
		Editor editer = mSharedPreferences.edit();
		editer.putLong(key, val);
		return editer.commit();
	}

}
