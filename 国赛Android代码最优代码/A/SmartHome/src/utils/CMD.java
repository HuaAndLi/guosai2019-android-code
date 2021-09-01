package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;

/**
 * 控制对象方便单控模式等的控制
 * 
 * @author Administrator
 * 
 */
public class CMD {
	public String name;
	public String channel;
	public String cmdon;
	public String cmdoff;
	public Boolean isCheck;
	private static ExecutorService pool = Executors.newSingleThreadExecutor();

	/**
	 * @param name 器件的板号
	 * @param channel 器件的通道号
	 * @param cmdon 器件开的命令
	 * @param cmdoff 器件关的命令
	 */
	public CMD(String name, String channel, String cmdon, String cmdoff) {
		super();
		this.name = name;
		this.channel = channel;
		this.cmdon = cmdon;
		this.cmdoff = cmdoff;
	}

	public void setControl(Boolean ischeck) {
		if (ischeck == null) {
			control(name, "4", channel);
		} else {
			if (isCheck != ischeck) {
				isCheck = ischeck;
				if (name.equals(ConstantUtil.Curtain)) {
					control(name, isCheck ? cmdon : cmdoff, channel);
				} else {
					control(name, channel, isCheck ? cmdon : cmdoff);
				}
			}
		}
	}

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
