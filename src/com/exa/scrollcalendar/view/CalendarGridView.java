package com.exa.scrollcalendar.view;


import com.exa.scrollcalendar.R;
import com.exa.scrollcalendar.R.color;
import com.exa.scrollcalendar.R.drawable;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.RelativeLayout;



/**
 * ������������չʾ��GridView����
 */
public class CalendarGridView extends GridView {

    /**
     * ��ǰ�����������Ķ���
     */
    private Context mContext;

    /**
     * CalendarGridView ������
     *
     * @param context ��ǰ�����������Ķ���
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
     * ��ʼ��gridView �ؼ��Ĳ���
     */
    private void setGirdView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        setSelector(R.drawable.tips_listv_bg);
        //TODO 
        setLayoutParams(params);
        setNumColumns(7); // ����ÿ������
        setGravity(Gravity.CENTER_VERTICAL); // λ�þ���
//        setVerticalSpacing(DensityUtil.dip2px(mContext, 1)); // ��ֱ���
//        setHorizontalSpacing(DensityUtil.dip2px(mContext, 1)); // ˮƽ���
        setBackgroundColor(getResources().getColor(R.color.calendar_background));

       /* WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int i = display.getWidth() / 7;
        int j = display.getWidth() - (i * 7);
        int x = j / 2;
        setPadding(x, 0, 0, 0);// ����
*/    }
}
