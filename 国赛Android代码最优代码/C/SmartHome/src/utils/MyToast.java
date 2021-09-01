package utils;

import android.widget.Toast;
/**
 * 自定义Toast
 * @author Administrator
 *
 */
public class MyToast {
	private static Toast toast;
	/**
	 * 显示Toast
	 * @param text toast的文本
	 */
	public static void makeText(String text) {
		if (toast == null) {
			toast = Toast.makeText(MyApplication.getContext(), text, 0);
		} else {
			toast.setText(text);
		}
		toast.show();
	}
}
