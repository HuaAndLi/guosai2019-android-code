package utils;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
/**
 * 工具类
 * @author Administrator
 *
 */
public class MyUtils {
	public static Handler mHandler = new Handler();
	public static double[] values = new double[9];
	public static double temp, humidity, ill, smoke, gas, co2, pm25, airp,
			statehuman;
	public static CMD alert, curtain, lamp, fan, tv, air, dvd, door;
	public static String ip = "192.168.1.7";

	/**
	 * 初始化控制、数据接收等，方便之后复用
	 */
	public static void init() {
		alert = new CMD(ConstantUtil.WarningLight, "3", "1", "0");
		curtain = new CMD(ConstantUtil.Curtain, "3", "1", "2");
		lamp = new CMD(ConstantUtil.Lamp, "3", "1", "0");
		fan = new CMD(ConstantUtil.Fan, "3", "1", "0");
		tv = new CMD(ConstantUtil.INFRARED_1_SERVE, "5", "1", "0");
		air = new CMD(ConstantUtil.INFRARED_1_SERVE, "1", "1", "0");
		dvd = new CMD(ConstantUtil.INFRARED_1_SERVE, "8", "1", "0");
		door = new CMD(ConstantUtil.RFID_Open_Door, "1", "1", "1");
		SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

			@Override
			public void onResult(DeviceBean bean) {
				values[0] = temp = toDouble(bean.getTemperature());
				values[1] = humidity = toDouble(bean.getHumidity());
				values[2] = ill = toDouble(bean.getIllumination());
				values[3] = smoke = toDouble(bean.getSmoke());
				values[4] = gas = toDouble(bean.getGas());
				values[5] = co2 = toDouble(bean.getCo2());
				values[6] = pm25 = toDouble(bean.getPM25());
				values[7] = airp = toDouble(bean.getAirPressure());
				values[8] = statehuman = toDouble(bean.getStateHumanInfrared());
			}
		});
		ControlUtils.setUser("bizideal", "123456", ip);
		SocketClient.getInstance().creatConnect();
	}

	private static double toDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}


	public static void startActivity(Activity ac, Class cla) {
		Intent intent = new Intent(ac, cla);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		ac.startActivity(intent);
	}
}
