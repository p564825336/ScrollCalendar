package com.exa.scrollcalendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	private OnScrollListener onScrollListener;
	
	private float mDownPosX = 0;  
    private float mDownPosY = 0;
	
	public MyScrollView(Context context) {
		this(context, null);
	}
	
	public MyScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/**
	 * ���ù����ӿ�
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}
	
	
	@Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }
	

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(onScrollListener != null){
			onScrollListener.onScroll(t);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		 final float x = ev.getX();  
	        final float y = ev.getY();  
	          
	        final int action = ev.getAction();  
	        switch (action) {  
	        case MotionEvent.ACTION_DOWN:  
	            mDownPosX = x;  
	            mDownPosY = y;  
	              
	            break;  
	        case MotionEvent.ACTION_MOVE:  
	            final float deltaX = Math.abs(x - mDownPosX);  
	            final float deltaY = Math.abs(y - mDownPosY);  
	            if (deltaX > deltaY) {  
	                return false;  
	            }  
	        }  
	                  
	        
		return super.onInterceptTouchEvent(ev);
	}



	/**
	 * 
	 * �����Ļص��ӿ�
	 * 
	 * @author xiaanming
	 *
	 */
	public interface OnScrollListener{
		/**
		 * �ص������� ����MyScrollView������Y�������
		 * @param scrollY
		 * 				��
		 */
		public void onScroll(int scrollY);
	}
	
	

}

