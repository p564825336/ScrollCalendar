package com.exa.scrollcalendar.view;


import com.exa.scrollcalendar.R;
import com.exa.scrollcalendar.R.color;
import com.exa.scrollcalendar.R.drawable;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.RelativeLayout;



/**
 * 用于生成日历展示的GridView布局
 */
public class CalendarGridView extends GridView {

    /**
     * 当前操作的上下文对象
     */
    private Context mContext;

    /**
     * CalendarGridView 构造器
     *
     * @param context 当前操作的上下文对象
     */
    public CalendarGridView(Context context) {
        super(context);
        mContext = context;

        setGirdView();
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, mExpandSpec);  
   }  
    /**
     * 初始化gridView 控件的布局
     */
    private void setGirdView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        setSelector(R.drawable.tips_listv_bg);
        //TODO 
        setLayoutParams(params);
        setNumColumns(7); // 设置每行列数
        setGravity(Gravity.CENTER_VERTICAL); // 位置居中
//        setVerticalSpacing(DensityUtil.dip2px(mContext, 1)); // 垂直间隔
//        setHorizontalSpacing(DensityUtil.dip2px(mContext, 1)); // 水平间隔
        setBackgroundColor(getResources().getColor(R.color.calendar_background));

       /* WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int i = display.getWidth() / 7;
        int j = display.getWidth() - (i * 7);
        int x = j / 2;
        setPadding(x, 0, 0, 0);// 居中
*/    }
}
