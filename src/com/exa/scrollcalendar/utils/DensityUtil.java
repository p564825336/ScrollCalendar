package com.exa.scrollcalendar.utils;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

public class DensityUtil {
	
	private static final String TAG = "DensityUtil";

	/**
	 * �����ֻ��ķֱ��ʴ�pxתΪdp
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / density + 0.5f);
	}
	
	/**
	 * dpתΪpx
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue){
		float density = context.getResources().getDisplayMetrics().density;
		Log.i(TAG, "density��ֵ��:��" + density + " dpValue��ֵ��: " + dpValue);
		
		return (int) (dpValue * density + 0.5f);
	}
	
	/**
	 * sp 2 px
	 * @author km
	 * @time 2014��6��9�� ����10:44:11 
	 * @param context ������
	 * @param spValue spֵ
	 * @return ��Ӧ��pxֵ
	 */
	public static float sp2px(Context context, float spValue){
		 return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
	}
}
