package utils;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;

public class MyUtils {
	public static Handler mHandler = new Handler();
	public static double temp, humidity, ill, smoke, gas, co2, pm25, airp,
			statehuman;
	public static double[] values = new double[9];
	public static CMD alert, curtain, lamp, fan, tv, air, dvd, door;
	public static String ip = "12.1.6.2";
	public static SharedPreferences shared;
	public static SharedPreferences.Editor editor;
	public static SQLiteDatabase sdb;
	public static void init() {
		alert = new CMD(ConstantUtil.WarningLight, "3", "1", "0");
		curtain = new CMD(ConstantUtil.Curtain, "3", "1", "2");
		lamp = new CMD(ConstantUtil.Lamp, "3", "1", "0");
		fan = new CMD(ConstantUtil.Fan, "3", "1", "0");
		tv = new CMD(ConstantUtil.INFRARED_1_SERVE, "3", "1", "0");
		air = new CMD(ConstantUtil.INFRARED_1_SERVE, "1", "1", "0");
		dvd = new CMD(ConstantUtil.INFRARED_1_SERVE, "2", "1", "0");
		door = new CMD(ConstantUtil.RFID_Open_Door, "1", "1", "1");
		SocketClient.getInstance().login(null);
		SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

			@Override
			public void onResult(DeviceBean bean) {
				// TODO Auto-generated method stub
				values[0] = temp = Double.parseDouble(bean.getTemperature());
				values[1] = humidity = Double.parseDouble(bean.getHumidity());
				values[2] = ill = Double.parseDouble(bean.getIllumination());
				values[3] = smoke = Double.parseDouble(bean.getSmoke());
				values[4] = gas = Double.parseDouble(bean.getGas());
				values[5] = co2 = Double.parseDouble(bean.getCo2());
				values[6] = pm25 = Double.parseDouble(bean.getPM25());
				values[7] = airp = Double.parseDouble(bean.getAirPressure());
				values[8] = statehuman = Double.parseDouble(bean
						.getStateHumanInfrared());
			}
		});

		ControlUtils.setUser("bizideal", "123456", ip);
		SocketClient.getInstance().disConnect();
		SocketClient.getInstance().creatConnect();
		if (shared == null) {
			shared = MyApplication.getContext().getSharedPreferences(
					"smarthome", 0);
			editor = MyApplication.getContext()
					.getSharedPreferences("smarthome", 0).edit();
		}
		if(sdb==null){
			sdb = MyApplication.getContext().openOrCreateDatabase("smarthome.db", 0, null);
		}
	}
}
