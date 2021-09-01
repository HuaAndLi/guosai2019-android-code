package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;

/**
 * 控制对象
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

	/**
	 * 器件对象单独的控制
	 * @param ischeck 开关器件
	 */
	public void setControl(Boolean ischeck) {
		if (isCheck == null || isCheck != ischeck) {
			isCheck = ischeck;
			if (name.equals(ConstantUtil.Curtain)) {
				control(name, isCheck ? cmdon : cmdoff, channel);
			} else {
				control(name, channel, isCheck ? cmdon : cmdoff);
			}
		}
	}

	/**
	 * 器件对象的总的控制 
	 */
	public static void control(final String name, final String channel,
			final String cmd) {
		pool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ControlUtils.control(name, channel, cmd);
			}
		});
	}
}
