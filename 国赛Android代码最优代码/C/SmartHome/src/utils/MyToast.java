package utils;

import android.widget.Toast;
/**
 * �Զ���Toast
 * @author Administrator
 *
 */
public class MyToast {
	private static Toast toast;
	/**
	 * ��ʾToast
	 * @param text toast���ı�
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
