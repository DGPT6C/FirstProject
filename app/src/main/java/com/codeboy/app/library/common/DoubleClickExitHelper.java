/*
 * Copyright (c) 2014. CodeBoyTeam
 */

package com.codeboy.app.library.common;

import com.codeboy.app.oschina.R;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;


/**
 * 类名 DoubleClickExitHelper.java</br>
 * 创建日期 2014年4月28日</br>
 * @author LeonLee (http://my.oschina.net/lendylongli)</br>
 * Email lendylongli@gmail.com</br>
 * 更新时间 2014年4月28日 上午12:14:06</br>
 * 最后更新者 LeonLee</br>
 * 
 * 说明 双击退出
 */
public class DoubleClickExitHelper {

	private final Activity mActivity;
	
	private boolean isOnKeyBacking;
	private Handler mHandler;
	private Toast mBackToast;
	
	public DoubleClickExitHelper(Activity activity) {
		mActivity = activity;
		mHandler = new Handler(Looper.getMainLooper());
	}
	
	/**
	 * Activity onKeyDown事件
	 * */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		if(isOnKeyBacking) {
			mHandler.removeCallbacks(onBackTimeRunnable);
			if(mBackToast != null){
				mBackToast.cancel();
			}
			mActivity.finish();
			return true;
		} else {
			isOnKeyBacking = true;
			if(mBackToast == null) {
				//显示时间为3秒
				mBackToast = Toast.makeText(mActivity, R.string.back_exit_tips, 3000);
			}
			mBackToast.show();
			//线程沉睡时间为3秒，在3秒之内再次按下back则直接退出应用
			//postDelayed 在一段时间之后，执行新的线程，即可以达到一段特定程序延迟执行的目的
			mHandler.postDelayed(onBackTimeRunnable, 3000);
			return true;
		}
	}
	
	private Runnable onBackTimeRunnable = new Runnable() {
		
		@Override
		public void run() {
			isOnKeyBacking = false;
			if(mBackToast != null){
				mBackToast.cancel();
			}
		}
	};
}
