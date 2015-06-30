package com.zonesion.ui;

import java.text.DecimalFormat;

import com.zhiyun360.wsn.droid.WSNRTConnect;
import com.zonesion.app.IOnSensorDataListener;
import com.zonesion.app.ZApplication;
import com.zonesion.tool.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TemperatureFragment extends Fragment implements
		IOnSensorDataListener {
	private TextView temperatureTextView;
	/** ZApplication实例 */
	private ZApplication mApplication;
	/** WSNRTConnect实例 */
//	public static WSNRTConnect wRTConnect;
	private WSNRTConnect wRTConnect;
	/** 信息采集模块Mac地址，修改为用户自己的温度传感器的MAC地址 */
	private String mMac = "00:12:4B:00:02:CB:A8:52";
	/** 温度值文本格式化 */
	private DecimalFormat fnum = new DecimalFormat("###.0"); // float保留一位小数

	private String TEMPERATURE = "温度值：0.0℃";
	
	public WSNRTConnect getWSNRTConnect(){
		return this.wRTConnect;
		
	}
	// 构造方法
	public TemperatureFragment() {
		System.out.println("实例化了TemperatureFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApplication = (ZApplication) getActivity().getApplication();
		mApplication.registerOnSensorDataListener(this);// 注册监听

		wRTConnect = mApplication.getWSNRConnect();
		// 实时数据访问接口（TCP），根据智云访问接口定义进行修改
		wRTConnect.setServerAddr("t.zhiyun360.com:28081");
		// 用户ID和密钥，根据分配给用户的ID和密钥进行修改
		wRTConnect.initZCloud("1155223953",
				"Xrk6UicNrbo3KiX1tYDDaUq9HAMHBYhuE2Sb4NLKFKdNcLH5");
		wRTConnect.connect();

		if (mMac.length() > 0) {
			wRTConnect.sendMessage(mMac, "{A0=?}".getBytes());
			Toast.makeText(getActivity(), "主动查询温度值", Toast.LENGTH_SHORT).show();

		}

		// 获取初始值
		String defValue = mApplication.getSensorData(mMac);
		if (!defValue.equals("")) {
			defValue = defValue.substring(1, defValue.length() - 1);
			String[] tags = defValue.split(",");
			for (String tag : tags) {
				String[] cv = tag.split("=");
				if (cv[0].equals("A0")) {
					Toast.makeText(getActivity(), "获取到SharedPreferences的值",
							Toast.LENGTH_SHORT).show();
					TEMPERATURE = "温度值：" + cv[1] + "℃";
				}
			}
		}
	}

	// private View rootView;// 缓存Fragment view

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// if (rootView == null) {
		// rootView = inflater.inflate(R.layout.temperature_fragment, null);
		// }
		// // 缓存的rootView需要判断是否已经被加过parent，
		// // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		// ViewGroup parent = (ViewGroup) rootView.getParent();
		// if (parent != null) {
		// parent.removeView(rootView);
		// }
		// return rootView;

//		Toast.makeText(getActivity(), "onCreatView方法被调用", Toast.LENGTH_SHORT)
//				.show();
		System.out.println("onCreatView方法被调用，生成fragment的视图");
		// 生成fragment视图的布局
		View v = inflater.inflate(R.layout.temperature_fragment, container,
				false);
		temperatureTextView = (TextView) v.findViewById(R.id.temperatureInfo);
		temperatureTextView.setText(TEMPERATURE);
		// 将生成的视图返回给托管activity
		return v;

	}

	@Override
	public void onSensorData(String mac, byte[] data) {
		// TODO Auto-generated method stub
		System.out.println("mac:" + mac + ",dat:" + new String(data));
		String dat = new String(data);
		dat = dat.substring(0);
		if (dat.charAt(0) != '{') {
			return;
		}
		if (dat.charAt(dat.length() - 1) != '}')
			return;
		dat = dat.substring(1, dat.length() - 1);
		String[] tags = dat.split(",");// A0=25,A1=34.0
		for (String tag : tags) {
			String[] cv = tag.split("=");
			if (cv.length < 2)
				continue;
			if (mMac.equalsIgnoreCase(mac)) {
				if (cv[0].equals("A0")) {
					float v = Float.parseFloat(cv[1]);
					temperatureTextView.setText("溫度值：" + fnum.format(v) + "℃");
					Toast.makeText(getActivity(), "监听到底层上传的数据",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

}
