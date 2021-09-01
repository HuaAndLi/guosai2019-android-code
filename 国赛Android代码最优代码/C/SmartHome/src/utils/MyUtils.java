package utils;

import java.util.ArrayList;
import java.util.List;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.text.TextUtils;

/**
 * 工具类 主要初始化控制和数据接收
 * 
 * @author Administrator
 * 
 */
public class MyUtils {
	public static Handler mHandler = new Handler();
	// 全局数据
	public static double temp, humidity, ill, smoke, gas, co2, pm25, airp,
			statehuman;
	public static double[] values = new double[9];
	// 全局控制
	public static CMD alert, curtain, lamp, lamp1, lamp2, fan, tv, air, dvd,
			door;
	public static List<CMD> controls = new ArrayList<CMD>();
	// 服务器默认Ip
	public static String ip = "19.1.10.2";
	// 全局数据库
	public static SQLiteDatabase sdb;

	/**
	 * 初始化控制和数据接收、数据库等
	 */
	public static void init() {
		// 控制对象的实例化
		controls.add(alert = new CMD(ConstantUtil.WarningLight, "3", "1", "0"));
		controls.add(curtain = new CMD(ConstantUtil.Curtain, "3", "1", "2"));
		controls.add(lamp = new CMD(ConstantUtil.Lamp, "7", "1", "0"));
		controls.add(lamp1 = new CMD(ConstantUtil.Lamp, "6", "1", "0"));
		controls.add(lamp2 = new CMD(ConstantUtil.Lamp, "5", "1", "0"));
		controls.add(fan = new CMD(ConstantUtil.Fan, "3", "1", "0"));
		controls.add(tv = new CMD(ConstantUtil.INFRARED_1_SERVE, "5", "1", "0"));
		controls.add(air = new CMD(ConstantUtil.INFRARED_1_SERVE, "1", "1", "0"));
		controls.add(dvd = new CMD(ConstantUtil.INFRARED_1_SERVE, "8", "1", "0"));
		controls.add(door = new CMD(ConstantUtil.RFID_Open_Door, "1", "1", "1"));
		// 数据接收的回调
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
		// 连接服务器
		SocketClient.getInstance().login(null);
		ControlUtils.setUser("bizideal", "123456", ip);
		SocketClient.getInstance().release();
		SocketClient.getInstance().creatConnect();
		// 初始化数据库
		if (sdb == null) {
			sdb = MyApplication.getContext().openOrCreateDatabase(
					"smarthome.db", 0, null);
			sdb.execSQL("create table if not exists datas(time varchar not null,temp varchar not null,ill varchar not null)");
			sdb.execSQL("delete from datas");
		}
	}

	/**
	 * 判断字符串是否为空
	 * 
	 */
	public static boolean isEmpty(String... strs) {
		for (int i = 0; i < strs.length; i++) {
			if (TextUtils.isEmpty(strs[i])) {
				return true;
			}
		}
		return false;
	}
}
