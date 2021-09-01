package utils;

import android.widget.Toast;

/**
 * ×Ô¶¨ÒåToast
 * 
 * @author Administrator
 * 
 */
public class MyToast {
	private static Toast toast;

	public static void makeText(String text) {
		if (toast == null) {
			toast = Toast.makeText(MyApplication.getContext(), text, 0);
		} else {
			toast.setText(text);
		}
		toast.show();
	}
}
