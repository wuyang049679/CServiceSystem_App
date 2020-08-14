package com.hc_android.hc_css.wight;


import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.NullUtils;

import java.util.Calendar;


public class CustomDateUtils {


    public static void getDateTime(Context mContext, final TextView dateTv,final TextView timeTv,boolean isDate) {
        String dateString = dateTv.getText().toString();
        String timeString = timeTv.getText().toString();
        View date_time_picker = View.inflate(mContext, R.layout.date_time_picker, null);

        final DatePicker datePicker = (DatePicker) date_time_picker.findViewById(R.id.data_picker);

        final TimePicker timePicker = (TimePicker) date_time_picker.findViewById(R.id.timer_picker);


        if (!NullUtils.isNull(dateString)){//设置日期
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(DateUtils.parse(dateTv.getText().toString(),DateUtils.DATE_SMALL_STR));
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), null);

        }
        if (!NullUtils.isNull(timeString)){//设置时间
            String s = timeTv.getText().toString();
            if (s.contains("下午")){
                String[] split = s.replace("下午", "").trim().split(":");
                int hours= 12+Integer.parseInt(split[0]);
                timePicker.setCurrentHour(hours);
                timePicker.setCurrentMinute(Integer.valueOf(split[1]));
            }
            if (s.contains("上午")){
                String[] split = s.replace("上午", "").trim().split(":");
                int hours= Integer.parseInt(split[0]);
                timePicker.setCurrentHour(hours);
                timePicker.setCurrentMinute(Integer.valueOf(split[1]));
            }

        }
//		timePicker.setIs24HourView(true);

        //  Build  DateTimeDialog

        if (isDate){//判断是否是日期或者时间选择器
            timePicker.setVisibility(View.GONE);
        }else {
            datePicker.setVisibility(View.GONE);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setView(date_time_picker);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNeutralButton("清除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (isDate) {
                    dateTv.setText("");
                    timeTv.setText("");
                }else {
                    timeTv.setText("上午  08:00");
                }

            }
        });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                String dateStr ="";


                int intMonth = (datePicker.getMonth()) + 1;
                if (intMonth<10){//月份小于10
                    dateStr = datePicker.getYear() + "-0" + ((datePicker.getMonth())+1) + "-" + datePicker.getDayOfMonth();
                }else {
                    dateStr = datePicker.getYear() + "-" + ((datePicker.getMonth())+1) + "-" + datePicker.getDayOfMonth();
                }



                int currentMinute = timePicker.getCurrentMinute();
                int currentHour = timePicker.getCurrentHour();

                String curMinute = "";
                String curHour = "";

                if (currentMinute < 10) {//分钟小于10

                    curMinute = "0" + currentMinute;

                } else {

                    curMinute = String.valueOf(currentMinute);

                }
                if (currentHour<10){
                    curHour = "0" + currentHour;
                }else {
                    if ((currentHour-12)<10){//12小时制上下午计算时间
                     curHour=   "0"+(currentHour-12);
                    }else {
                     curHour= String.valueOf(currentHour-12);
                    }
                }

                String timeStr = "";

                if (isDate) {
                    dateTv.setText(dateStr);
                }else {
                    if (timePicker.getCurrentHour()>12){//设置上下午时间
                        timeStr="下午  "+curHour+":"+curMinute;
                    }else {
                        timeStr="上午  "+curHour+":"+curMinute;
                    }
                    timeTv.setText(timeStr);
                }
                if (NullUtils.isNull(dateString)){//如果日期为空则设置当前
                    dateTv.setText(DateUtils.getCurrentDate());
                }
                if (NullUtils.isNull(timeString)){//如果时间为空则设置默认时间
                    timeTv.setText("上午  08:00");
                }
            }

        });

        builder.show();

    }
}
