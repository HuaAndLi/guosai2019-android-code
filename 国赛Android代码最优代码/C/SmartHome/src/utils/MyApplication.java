package utils;

import android.app.Application;
import android.content.Context;

/**
 * 全局Context
 * 
 * @author Administrator
 * 
 */
public class MyApplication extends Application {
	private static Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = getApplicationContext();
	}

	/**
	 * 获取全局Context
	 * 
	 * @return Context
	 */
	public static Context getContext() {
		return context;
	}
}
