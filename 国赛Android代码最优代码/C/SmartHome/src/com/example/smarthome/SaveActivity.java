package com.example.smarthome;

import utils.MyToast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 滑动解锁界面
 * 
 * @author Administrator
 * 
 */
public class SaveActivity extends Activity {

	private SeekBar seekbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		// 绑定控件
		seekbar = (SeekBar) findViewById(R.id.seekBar1);
		// 设置监听
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (seekBar.getProgress() != seekBar.getMax()) {
					seekBar.setProgress(0);
					MyToast.makeText("验证失败");
				} else {
					finish();
					startActivity(new Intent(SaveActivity.this,
							MainActivity.class));
					MyToast.makeText("验证成功");
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub

			}
		});
	}

}
