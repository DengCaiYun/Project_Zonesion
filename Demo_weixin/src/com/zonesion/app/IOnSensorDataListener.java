package com.zonesion.app;
/**
 * 传感器数据监听器
 * @author Administrator
 *
 */
public interface IOnSensorDataListener {
	/**
	 * 接收数据
	 * @param mac mac地址
	 * @param data 指令
	 */
	void onSensorData(String mac, byte[] data);
}
