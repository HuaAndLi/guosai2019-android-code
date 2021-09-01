package utils;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.text.TextUtils;

public class MyUtils {
	public static Handler mHandler = new Handler();
	public static double temp, humidity, ill, smoke, gas, co2, pm25, airp,
			statehuman;
	public static double[] values = new double[9];
	public static CMD alert, curtain, lamp, fan, tv, air, dvd, door;
	public static String ip = "18.1.10.7";
	public static SharedPreferences shared;
	public static SharedPreferences.Editor editor;
	public static List<Double> ills = new ArrayList<Double>();
	public static SQLiteDatabase sdb;
	public static List<CMD> controls = new ArrayList<CMD>();

	public static void init() {
		controls.add(alert = new CMD(ConstantUtil.WarningLight, "3", "1", "0"));
		controls.add(curtain = new CMD(ConstantUtil.Curtain, "3", "1", "2"));
		controls.add(lamp = new CMD(ConstantUtil.Lamp, "3", "1", "0"));
		controls.add(fan = new CMD(ConstantUtil.Fan, "3", "1", "0"));
		controls.add(tv = new CMD(ConstantUtil.INFRARED_1_SERVE, "5", "1", "0"));
		controls.add(air = new CMD(ConstantUtil.INFRARED_1_SERVE, "1", "1", "0"));
		controls.add(dvd = new CMD(ConstantUtil.INFRARED_1_SERVE, "8", "1", "0"));
		controls.add(door = new CMD(ConstantUtil.RFID_Open_Door, "1", "1", "1"));
		ills.add(100.d);
		ills.add(100.d);
		ills.add(100.d);
		ills.add(100.d);
		ills.add(100.d);
		ills.add(100.d);
		SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

			@Override
			public void onResult(DeviceBean bean) {
				// TODO Auto-generated method stub
				if (ill != Double.parseDouble(bean.getIllumination())) {
					ills.add(Double.parseDouble(bean.getIllumination()));
				}
				if (ills.size() > 7) {
					ills.remove(0);
				}
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
		SocketClient.getInstance().login(null);
		ControlUtils.setUser("bizideal", "123456", ip);
		SocketClient.getInstance().release();
		SocketClient.getInstance().creatConnect();
		if (shared == null) {
			shared = MyApplication.getContext().getSharedPreferences(
					"smarthome", 0);
			editor = MyApplication.getContext()
					.getSharedPreferences("smarthome", 0).edit();
		}
		if (sdb == null) {
			sdb = MyApplication.getContext().openOrCreateDatabase(
					"smarthome.db", 0, null);
			sdb.execSQL("create table if not exists datas(bian varchar not null,name varchar not null,isCheck varchar not null)");
		}
	}

	public static boolean isEmpty(String... strs) {
		for (int i = 0; i < strs.length; i++) {
			if (TextUtils.isEmpty(strs[i])) {
				return true;
			}
		}
		return false;
	}
}
