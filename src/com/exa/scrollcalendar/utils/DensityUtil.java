package com.exa.scrollcalendar.utils;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

public class DensityUtil {
	
	private static final String TAG = "DensityUtil";

	/**
	 * 根据手机的分辨率从px转为dp
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / density + 0.5f);
	}
	
	/**
	 * dp转为px
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue){
		float density = context.getResources().getDisplayMetrics().density;
		Log.i(TAG, "density的值是:　" + density + " dpValue的值是: " + dpValue);
		
		return (int) (dpValue * density + 0.5f);
	}
	
	/**
	 * sp 2 px
	 * @author km
	 * @time 2014年6月9日 上午10:44:11 
	 * @param context 上下文
	 * @param spValue sp值
	 * @return 对应的px值
	 */
	public static float sp2px(Context context, float spValue){
		 return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
	}
}
