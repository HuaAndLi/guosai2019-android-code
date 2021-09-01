package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.smarthome.MainActivity;

/**
 * ���ƶ���
 * ���ء�ģʽ���������ɴ˶������
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
	 * @param name �����İ��
	 * @param channel ������ͨ����
	 * @param cmdon �����Ŀ�����
	 * @param cmdoff �����Ĺ�����
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

	//������Ʒ���
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

	//�ܵĿ��Ʒ���
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
