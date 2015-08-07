package com.exa.scrollcalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.exa.scrollcalendar.utils.DensityUtil;
import com.exa.scrollcalendar.utils.NumberHelper;
import com.exa.scrollcalendar.view.CalendarGridView;
import com.exa.scrollcalendar.view.MyScrollView;
import com.exa.scrollcalendar.view.MyScrollView.OnScrollListener;

/**
 * 
 * @������: MainActivity
 * @������: TODO
 * @�����ˣ�PengBo
 * @����ʱ�䣺2015-8-6 ����6:14:42 
 * @��ע��
 */
public class MainActivity extends Activity implements OnScrollListener, OnClickListener, OnTouchListener{
	private static final String TAG = "MainActivity";
	/**
	 * �Զ����MyScrollView
	 */
	private MyScrollView myScrollView;
	/**
	 * ��MyScrollView����Ĺ��򲼾�
	 */
	private LinearLayout buy;
	/**
	 * λ�ڶ����Ĺ��򲼾�
	 */
	private LinearLayout top_buy_layout;
	private boolean isShowCalendar = false;
	
	
	/******************************����Ϊ������ز���**************************************/
	/**
     * ��������ID
     */
    private static final int CAL_LAYOUT_ID = 55;
    //�ж�������
    private static final int SWIPE_MIN_DISTANCE = 120;

    private static final int SWIPE_MAX_OFF_PATH = 250;

    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    //����
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private ViewFlipper viewFlipper;
    GestureDetector mGesture = null;


    /**
     * ��һ���°�ť
     */
    private ImageView mPreMonthImg;

    /**
     * ��һ���°�ť
     */
    private ImageView mNextMonthImg;

    /**
     * ������ʾ���������
     */
    private TextView mDayMessage;

    /**
     * ����װ��������View
     */
    private RelativeLayout mCalendarMainLayout;

    // ��������
    private Context mContext = MainActivity.this;
    /**
     * ��һ����View
     */
    private GridView firstGridView;

    /**
     * ��ǰ��View
     */
    private GridView currentGridView;

    /**
     * ��һ����View
     */
    private GridView lastGridView;

    /**
     * ��ǰ��ʾ������
     */
    private Calendar calStartDate = Calendar.getInstance();

    /**
     * ѡ�������
     */
    private Calendar calSelected = Calendar.getInstance();

    /**
     * ����
     */
    private Calendar calToday = Calendar.getInstance();

    /**
     * ��ǰ����չʾ������Դ
     */
    private CalendarGridViewAdapter currentGridAdapter;

    /**
     * Ԥװ����һ����չʾ������Դ
     */
    private CalendarGridViewAdapter firstGridAdapter;

    /**
     * Ԥװ����һ����չʾ������Դ
     */
    private CalendarGridViewAdapter lastGridAdapter;
    //
    /**
     * ��ǰ��ͼ��
     */
    private int mMonthViewCurrentMonth = 0;

    /**
     * ��ǰ��ͼ��
     */
    private int mMonthViewCurrentYear = 0;

    /**
     * ��ʼ��
     */
    private int iFirstDayOfWeek = Calendar.MONDAY;


    private int title_layout_height_dp;
    private RelativeLayout message_layout;
    private int message_layout_height_dp;//�ؼ��ĸ߶�
    
    
	boolean isShowTask = true;
	boolean isShowFinishTask = false;


	private ImageView iv_pre_year_above;
	private ImageView iv_next_year_above;
	private int ll_calendar_all_height_px;
	private LinearLayout ll_calendar_all;


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main);
		initView();
	}




	private void initView() {
		myScrollView = (MyScrollView) findViewById(R.id.scrollView);
		buy = (LinearLayout) findViewById(R.id.buy);
		top_buy_layout = (LinearLayout) findViewById(R.id.top_buy_layout);
		tv_sel_day = (TextView) top_buy_layout.findViewById(R.id.tv_sel_day);
		ImageView iv_calendar_show = (ImageView) top_buy_layout.findViewById(R.id.iv_calendar_show);
		iv_calendar_show.setOnClickListener(this);
		top_buy_layout.findViewById(R.id.tv_back_today).setOnClickListener(onTodayClickListener);
		//��������ص�
		aboutCalendar();
		
		myScrollView.setOnScrollListener(this);
		
		//�����ֵ�״̬���߿ؼ��Ŀɼ��Է����ı�ص��Ľӿ�
		findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				//��һ������Ҫ��ʹ������Ĺ��򲼾ֺ�����Ĺ��򲼾��غ�
				onScroll(myScrollView.getScrollY());
			}
		});
	}


	private void aboutCalendar() {
		ll_calendar_all = (LinearLayout) findViewById(R.id.ll_calendar_all);
        mDayMessage = (TextView) findViewById(R.id.day_message);
        mCalendarMainLayout = (RelativeLayout) findViewById(R.id.calendar_main);
        mPreMonthImg = (ImageView) findViewById(R.id.left_img);
        mNextMonthImg = (ImageView) findViewById(R.id.right_img);
        iv_pre_year_above = (ImageView) findViewById(R.id.iv_pre_year_above);
        iv_next_year_above = (ImageView) findViewById(R.id.iv_next_year_above);
        message_layout = (RelativeLayout) findViewById(R.id.message_layout_sign);
        
        mPreMonthImg.setOnClickListener(onPreMonthClickListener);
        mNextMonthImg.setOnClickListener(onNextMonthClickListener);
        
        iv_pre_year_above.setOnClickListener(onPreYearClickListener);
        iv_next_year_above.setOnClickListener(onNextYearClickListener);
        
        //��ȡ�ؼ��ĳߴ�       
        ViewTreeObserver message_layout_observer = message_layout.getViewTreeObserver();
        message_layout_observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {			

			@Override
			public void onGlobalLayout() {
				message_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int message_layout_height_px = message_layout.getMeasuredHeight();
				message_layout_height_dp = DensityUtil.px2dip(mContext, message_layout_height_px);
				
			}
		});
        
        
        ViewTreeObserver ll_calendar_all_observer = ll_calendar_all.getViewTreeObserver();
        ll_calendar_all_observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				ll_calendar_all.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				ll_calendar_all_height_px = ll_calendar_all.getMeasuredHeight();
				
				myScrollView.scrollTo(0, ll_calendar_all_height_px);
			}
		});
        
        
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
    	
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    	
    	int widthPixels = displayMetrics.widthPixels;
    	int heightPixels = displayMetrics.heightPixels;
    	int heightDp = DensityUtil.px2dip(mContext, heightPixels);
    	int widthDp = DensityUtil.px2dip(mContext, widthPixels);
        
    	int cell_height_dp = (heightDp - title_layout_height_dp - message_layout_height_dp)/(7*2);
    	int cell_height_px = DensityUtil.dip2px(mContext, cell_height_dp);
    	
    	GlobalVariable.cellHeight_px = cell_height_px;
    	
    	int cell_width_dp = (widthDp - 7*6)/7;
    	int cell_width_px = DensityUtil.dip2px(mContext, cell_width_dp);
    	
    	GlobalVariable.ceeWidth_px = cell_width_px;
		
    	updateStartDateForMonth();
        generateContetView(mCalendarMainLayout);
        slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);

        slideLeftIn.setAnimationListener(animationListener);
        slideLeftOut.setAnimationListener(animationListener);
        slideRightIn.setAnimationListener(animationListener);
        slideRightOut.setAnimationListener(animationListener);

        mGesture = new GestureDetector(this, new GestureListener());
	}
	
	 /**
     * ���ڼ��ص���ǰ�����ڵ��¼�
     */
    private View.OnClickListener onTodayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            calStartDate = Calendar.getInstance();
            updateStartDateForMonth();
            generateContetView(mCalendarMainLayout);            
        }
    };
    
    /**
     * ���ڼ�����һ��
     */
    private View.OnClickListener onNextYearClickListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			 viewFlipper.setInAnimation(slideLeftIn);
	         viewFlipper.setOutAnimation(slideLeftOut);
	         viewFlipper.showNext();
	         setNextYearViewItem();
		}
	};
    
	/**
	 * ��һ��
	 */
	private void setNextYearViewItem() {
		 mMonthViewCurrentMonth = mMonthViewCurrentMonth + 12;	  
	     calStartDate.set(Calendar.DAY_OF_MONTH, 1);
	     calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth);
	     calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear);
	}
	
    /**
     * ���ڼ�����һ��
     */
    private View.OnClickListener onPreYearClickListener = new View.OnClickListener() {		
		@Override
		public void onClick(View view) {
			viewFlipper.setInAnimation(slideRightIn);
            viewFlipper.setOutAnimation(slideRightOut);
            viewFlipper.showPrevious();
            setPreYearViewItem();
		}
	};
	
	
	/**
	 * ��һ��
	 */
	private void setPreYearViewItem() {
		mMonthViewCurrentMonth = mMonthViewCurrentMonth - 12;// ��ǰѡ����--
        
        calStartDate.set(Calendar.DAY_OF_MONTH, 1); // ������Ϊ����1��
        calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth); // ������
        calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear); // ������
	}
	
	

    /**
     * ���ڼ�����һ�������ڵ��¼�
     */
    private View.OnClickListener onPreMonthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewFlipper.setInAnimation(slideRightIn);
            viewFlipper.setOutAnimation(slideRightOut);
            viewFlipper.showPrevious();
            setPrevViewItem();
        }
    };

    /**
     * ���ڼ�����һ�������ڵ��¼�
     */
    private View.OnClickListener onNextMonthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewFlipper.setInAnimation(slideLeftIn);
            viewFlipper.setOutAnimation(slideLeftOut);
            viewFlipper.showNext();
            setNextViewItem();
        }
    };


    /**
     * ��Ҫ�������ɷ�ǰչʾ������View
     *
     * @param layout ��Ҫ����ȥ���صĲ���
     */
    @SuppressWarnings("deprecation")
	private void generateContetView(RelativeLayout layout) {
        // ����һ����ֱ�����Բ��֣��������ݣ�
        viewFlipper = new ViewFlipper(this);
        viewFlipper.setId(CAL_LAYOUT_ID);
        calStartDate = getCalendarStartDate();
        CreateGirdView();
        
        RelativeLayout.LayoutParams params_cal = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        
        layout.addView(viewFlipper, params_cal);

        LinearLayout br = new LinearLayout(this);
        RelativeLayout.LayoutParams params_br = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 1);
        params_br.addRule(RelativeLayout.BELOW, CAL_LAYOUT_ID);
        br.setBackgroundColor(getResources().getColor(R.color.calendar_background));
        layout.addView(br, params_br);
    }

    /**
     * ���ڴ�����ǰ��Ҫ����չʾ��View
     */
    private void CreateGirdView() {
    	Calendar firstCalendar = Calendar.getInstance(); // ��ʱ
    	Calendar currentCalendar = Calendar.getInstance(); // ��ʱ
    	Calendar lastCalendar = Calendar.getInstance(); // ��ʱ
    		
		firstCalendar.setTime(calStartDate.getTime());
		currentCalendar.setTime(calStartDate.getTime());
		lastCalendar.setTime(calStartDate.getTime());
			
        firstGridView = new CalendarGridView(mContext);
        firstCalendar.add(Calendar.MONTH, -1);
        firstGridAdapter = new CalendarGridViewAdapter(this, firstCalendar);
        firstGridView.setAdapter(firstGridAdapter);// ���ò˵�Adapter
        firstGridView.setId(CAL_LAYOUT_ID);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Log.i(TAG, "currentCalendar��ֵ��: " + df.format(currentCalendar.getTime()));
        currentGridView = new CalendarGridView(mContext);
        currentGridAdapter = new CalendarGridViewAdapter(this, currentCalendar);
        
        currentGridView.setAdapter(currentGridAdapter);// ���ò˵�Adapter
        currentGridView.setId(CAL_LAYOUT_ID);

        lastGridView = new CalendarGridView(mContext);
        lastCalendar.add(Calendar.MONTH, 1);
        lastGridAdapter = new CalendarGridViewAdapter(this, lastCalendar);
        lastGridView.setAdapter(lastGridAdapter);// ���ò˵�Adapter
        lastGridView.setId(CAL_LAYOUT_ID);

        currentGridView.setOnTouchListener(this);
        firstGridView.setOnTouchListener(this);
        lastGridView.setOnTouchListener(this);

        if (viewFlipper.getChildCount() != 0) {
            viewFlipper.removeAllViews();
        }

        viewFlipper.addView(currentGridView);
        viewFlipper.addView(lastGridView);
        viewFlipper.addView(firstGridView);

        String s = calStartDate.get(Calendar.YEAR)
                + "-"
                + NumberHelper.LeftPad_Tow_Zero(calStartDate
                .get(Calendar.MONTH) + 1);
        mDayMessage.setText(s);
        

        String s2 = s + "-" + NumberHelper.LeftPad_Tow_Zero(calStartDate.get(Calendar.DAY_OF_MONTH));
        tv_sel_day.setText(s2);
    }

    /**
     * ��һ����
     */
    private void setPrevViewItem() {
		 mMonthViewCurrentMonth--;// ��ǰѡ����--
		 // �����ǰ��Ϊ�����Ļ���ʾ��һ��
		 if (mMonthViewCurrentMonth == -1) {
			 mMonthViewCurrentMonth = 11;
			 mMonthViewCurrentYear--;
		 }
		 calStartDate.set(Calendar.DAY_OF_MONTH, 1); // ������Ϊ����1��
		 calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth); // ������
		 calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear); // ������
    }

    int i = 0;
    
    
    /**
     * ��һ����
     */
    private void setNextViewItem() {
	   mMonthViewCurrentMonth++;
        if (mMonthViewCurrentMonth == 12) {
            mMonthViewCurrentMonth = 0;
            mMonthViewCurrentYear++;
        }
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear);
		
    }

    /**
     * ���ݸı�����ڸ�������
     * ��������ؼ���
     */
    private void updateStartDateForMonth() {
//        calStartDate.set(Calendar.DATE, 1); // ���óɵ��µ�һ��
        	calStartDate.set(Calendar.DATE, 1); // ���óɵ��µ�һ��
		
        mMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// �õ���ǰ������ʾ����
        mMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);// �õ���ǰ������ʾ����

        String s = calStartDate.get(Calendar.YEAR)
                + "-"
                + NumberHelper.LeftPad_Tow_Zero(calStartDate
                .get(Calendar.MONTH) + 1);
        mDayMessage.setText(s);
        
        // ����һ��2 ��������1 ���ʣ������
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

    }

    /**
     * ���ڻ�ȡ��ǰ��ʾ�·ݵ�ʱ��
     *
     * @return ��ǰ��ʾ�·ݵ�ʱ��
     */
    private Calendar getCalendarStartDate() {
        calToday.setTimeInMillis(System.currentTimeMillis());
        calToday.setFirstDayOfWeek(iFirstDayOfWeek);

        if (calSelected.getTimeInMillis() == 0) {
            calStartDate.setTimeInMillis(System.currentTimeMillis());
            calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
        } else {
            calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
            calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
        }

        return calStartDate;
    }

    AnimationListener animationListener = new AnimationListener() {
        

		@Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //��������ɺ����
            CreateGirdView();
        }
    };
	private TextView tv_sel_day;
	

    class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {//����
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                    viewFlipper.showNext();
                    setNextViewItem();
                    return true;

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                    viewFlipper.showPrevious();
                    setPrevViewItem();
                    return true;

                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //�õ���ǰѡ�е��ǵڼ�����Ԫ��
            int pos = currentGridView.pointToPosition((int) e.getX(), (int) e.getY());
            LinearLayout txtDay = (LinearLayout) currentGridView.findViewById(pos + 5000);
            if (txtDay != null) {
                if (txtDay.getTag() != null) {
					Date date = (Date) txtDay.getTag();
					calSelected.setTime(date);
					
					if ( (calStartDate.get(Calendar.MONTH) + 1) == (calSelected.get(Calendar.MONTH) + 1)) {
						currentGridAdapter.setSelectedDate(calSelected);
						currentGridAdapter.notifyDataSetChanged();
						firstGridAdapter.setSelectedDate(calSelected);
						firstGridAdapter.notifyDataSetChanged();
						
						lastGridAdapter.setSelectedDate(calSelected);
						lastGridAdapter.notifyDataSetChanged();
						
						Log.i(TAG, "date: " + date);
						String s2 = calSelected.get(Calendar.YEAR)
								+ "-"
								+ NumberHelper.LeftPad_Tow_Zero(calSelected
										.get(Calendar.MONTH) + 1) + "-" +  NumberHelper.LeftPad_Tow_Zero(calSelected.get(Calendar.DAY_OF_MONTH));
						tv_sel_day.setText(s2);
					}
                }
            }

            Log.i("TEST", "onSingleTapUp -  pos=" + pos);
            return false;
        }
    }

	@Override
	public void onScroll(int scrollY) {
		int buy2ParentTop = Math.max(scrollY, buy.getTop());
		if (scrollY >= buy.getTop()) {//����û����ʾ
			isShowCalendar = false;
		}else {
			isShowCalendar = true;
		}
		Log.i(TAG, "scrollY: " + scrollY  + "    buy.getTop(): " + buy.getTop());
		top_buy_layout.layout(0, buy2ParentTop, top_buy_layout.getWidth(), buy2ParentTop + top_buy_layout.getHeight());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_calendar_show:
			Log.i(TAG, "�����������...");
			if (isShowCalendar) {
				isShowCalendar = false;
				myScrollView.smoothScrollBy(0, ll_calendar_all_height_px);
			}else {
				isShowCalendar = true;
				myScrollView.fullScroll(ScrollView.FOCUS_UP);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		 return mGesture.onTouchEvent(event);
	}
}
