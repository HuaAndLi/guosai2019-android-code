package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.smarthome.MainActivity;

/**
 * 控制对象
 * 单控、模式、联动都由此对象控制
 * 
 * @author Administrator
 * 
 */
public class CMD {
	public String name;
	public String channel;
	public String cmdon;
	public String cmdoff;
	public Boolean isCheck = false;
	private static ExecutorService pool = Executors.newSingleThreadExecutor();

	/**
	 * @param name 器件的板号
	 * @param channel 器件的通道号
	 * @param cmdon 器件的开命令
	 * @param cmdoff 器件的关命令
	 */
	public CMD(String name, String channel, String cmdon, String cmdoff) {
		super();
		this.name = name;
		this.channel = channel;
		this.cmdon = cmdon;
		this.cmdoff = cmdoff;
	}

	@Override
	public String toString() {
		return "CMD [name=" + name + ", channel=" + channel + ", cmdon="
				+ cmdon + ", cmdoff=" + cmdoff + "]";
	}

	//对象控制方法
	public void setControl(Boolean ischeck) {
		if (isCheck == null || isCheck != ischeck) {
			isCheck = ischeck;
			if (name.equals(ConstantUtil.Curtain)) {
				control(name, ischeck ? cmdon : cmdoff, channel);
			} else {
				control(name, channel, ischeck ? cmdon : cmdoff);
			}
			MyUtils.editor.putBoolean(MainActivity.name + name + channel,
					isCheck).commit();
		}
	}

	//总的控制方法
	public static void control(final String name, final String channel,
			final String cmd) {
		pool.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ControlUtils.control(name, channel, cmd);
				Log.e("control", "[name=" + name + ", channel=" + channel
						+ ", cmd=" + cmd + "]");
			}
		});
	}
}
