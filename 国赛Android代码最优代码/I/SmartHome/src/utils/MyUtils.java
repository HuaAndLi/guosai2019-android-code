package utils;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;

import android.app.AlertDialog;
import android.content.Context;
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
	public static SharedPreferences shared;
	public static SharedPreferences.Editor editor;
	public static SQLiteDatabase sdb;
	public static String ip = "19.1.10.2";

	public static void init() {
		alert = new CMD(ConstantUtil.WarningLight, "3", "1", "0");
		curtain = new CMD(ConstantUtil.Curtain, "3", "1", "2");
		lamp = new CMD(ConstantUtil.Lamp, "3", "1", "0");
		fan = new CMD(ConstantUtil.Fan, "3", "1", "0");
		tv = new CMD(ConstantUtil.INFRARED_1_SERVE, "5", "1", "0");
		air = new CMD(ConstantUtil.INFRARED_1_SERVE, "1", "1", "0");
		dvd = new CMD(ConstantUtil.INFRARED_1_SERVE, "8", "1", "0");
		door = new CMD(ConstantUtil.RFID_Open_Door, "3", "1", "0");
		SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

			@Override
			public void onResult(DeviceBean bean) {
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
		if (shared == null) {
			shared = MyApplication.getContext().getSharedPreferences(
					"smarthome", 0);
			editor = MyApplication.getContext()
					.getSharedPreferences("smarthome", 0).edit();
		}
		if (sdb == null) {
			sdb = MyApplication.getContext().openOrCreateDatabase(
					"smarthome.db", 0, null);
			sdb.execSQL("create table if not exists user(username varchar not null,password varchar not null)");
		}
	}

	public static void showAlert(Context context, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton("Ok", null);
		builder.show();
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
