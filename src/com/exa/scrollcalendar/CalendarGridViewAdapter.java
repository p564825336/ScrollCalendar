package com.exa.scrollcalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 日历的适配器
 * @类名称: CalendarGridViewAdapter
 * @类描述: TODO
 * @创建人：PengBo
 * @创建时间：2015-8-7 下午2:29:45 
 * @备注：
 */
public class CalendarGridViewAdapter extends BaseAdapter {

    private static final String TAG = "CalendarGridViewAdapter";
	private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
    private Calendar calSelected = Calendar.getInstance(); // 选择的日历

    public void setSelectedDate(Calendar cal) {
        calSelected = cal;
    }
    

    private Calendar calToday = Calendar.getInstance(); // 今日
    private int iMonthViewCurrentMonth = 0; // 当前视图月

    // 根据改变的日期更新日历
    // 填充日历控件用
    private void UpdateStartDateForMonth() {
        calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
	  
        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

        // 星期一是2 星期天是1 填充剩余天数
        int iDay = 0;
        int iFirstDayOfWeek = Calendar.MONDAY;
        int iStartDay = iFirstDayOfWeek;
        if (iStartDay == Calendar.MONDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
            if (iDay < 0)
                iDay = 6;
        }
        if (iStartDay == Calendar.SUNDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (iDay < 0)
                iDay = 6;
        }
        calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

        calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

    }
    
    ArrayList<Object> titles;

    private ArrayList<Object> getDates() {

        UpdateStartDateForMonth();

        ArrayList<Object> alArrayList = new ArrayList<Object>();
        
        alArrayList.add("日");
        alArrayList.add("一");
        alArrayList.add("二");
        alArrayList.add("三");
        alArrayList.add("四");
        alArrayList.add("五");
        alArrayList.add("六");
        
        
        for (int i = 1; i <= 42; i++) {
            alArrayList.add(calStartDate.getTime());
            calStartDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        
       

        return alArrayList;
    }

    private Activity activity;
    Resources resources;

    // construct
    public CalendarGridViewAdapter(Activity a, Calendar cal) {
        calStartDate = cal;
        activity = a;
        resources = activity.getResources();
        titles = getDates();
    }

    public CalendarGridViewAdapter(Activity a) {
        activity = a;
        resources = activity.getResources();
    }


    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout iv = new LinearLayout(activity);
        iv.setId(position + 5000);
        iv.setGravity(Gravity.CENTER);
        iv.setOrientation(LinearLayout.VERTICAL);
        iv.setBackgroundColor(resources.getColor(R.color.white));
        LinearLayout.LayoutParams iv_Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(activity);
        imageView.setBackgroundResource(R.drawable.direct_index_normal);
        imageView.setLayoutParams(iv_Params);
        
        LinearLayout.LayoutParams iv_Params_sel = new LinearLayout.LayoutParams(GlobalVariable.ceeWidth_px, 20);
        ImageView imageView_sel = new ImageView(activity);
        imageView_sel.setBackgroundResource(R.drawable.rod_blue);
        imageView_sel.setLayoutParams(iv_Params_sel);
        
        TextView txtDay = new TextView(activity);// 日期
        String simpleName = getItem(position).getClass().getSimpleName();
        if (simpleName.equals("String")) {
        	imageView.setVisibility(View.GONE);
        	imageView_sel.setVisibility(View.GONE);
        	txtDay.setText((String)getItem(position));
        	imageView.setVisibility(View.INVISIBLE);
		}else if (simpleName.equals("Date")){
			
			Date myDate = (Date) getItem(position);
			Calendar calCalendar = Calendar.getInstance();
			calCalendar.setTime(myDate);
			
			final int iMonth = calCalendar.get(Calendar.MONTH);
			final int iDay = calCalendar.get(Calendar.DAY_OF_WEEK);
			
			
			// 判断周六周日
			iv.setBackgroundColor(resources.getColor(R.color.white));
			/*if (iDay == 7) {
            // 周六
            iv.setBackgroundColor(resources.getColor(R.color.text_6));
        } else if (iDay == 1) {
            // 周日
            iv.setBackgroundColor(resources.getColor(R.color.text_7));
        } else {

        }*/
			
			 // 判断是否是当前月
			 if (iMonth == iMonthViewCurrentMonth) {
				 txtDay.setTextColor(resources.getColor(R.color.Text));

			 } else {
				 txtDay.setTextColor(resources.getColor(R.color.noMonth));
					imageView_sel.setVisibility(View.GONE);
					imageView.setVisibility(View.INVISIBLE);
			 }
			 
			
			 if (equalsDate(calToday.getTime(), myDate)) {
				// 当前日期
//					iv.setBackgroundColor(resources.getColor(R.color.event_center));
				txtDay.setTextColor(resources.getColor(R.color.blue_base));
//	            txtToDay.setText(calendarUtil.toString());
			} else {
//	            txtToDay.setText(calendarUtil.toString());
			}
			

			// 设置背景颜色
			if (equalsDate(calSelected.getTime(), myDate)) {
				// 选择的
				txtDay.setTextColor(resources.getColor(R.color.selection));
				iv.setBackgroundColor(resources.getColor(R.color.selection));
				
				imageView.setVisibility(View.GONE);
			} else {
				if (equalsDate(calToday.getTime(), myDate)) {
					// 当前日期
					iv.setBackgroundColor(resources.getColor(R.color.calendar_zhe_day));
				}
			}
			 
			int day = myDate.getDate(); // 日期
			txtDay.setText(String.valueOf(day));
			txtDay.setId(position + 500);
			iv.setTag(myDate);
			
		}
        
        txtDay.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        		LayoutParams.MATCH_PARENT, GlobalVariable.cellHeight_px);
        
        iv.setBackgroundColor(resources.getColor(R.color.purple_8859b5));
        iv.addView(txtDay, lp);
        
        return iv;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private Boolean equalsDate(Date date1, Date date2) {

        if (date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonth()
                && date1.getDate() == date2.getDate()) {
            return true;
        } else {
            return false;
        }

    }
    
   
}
