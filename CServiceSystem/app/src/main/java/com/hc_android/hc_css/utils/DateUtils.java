package com.hc_android.hc_css.utils;


import android.text.TextUtils;

import com.hc_android.hc_css.utils.android.AppLogMessageMgr;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class DateUtils {

    private static long calendarLong = 1533081600000L;
    private static String calendar = "CalendarDay{2018-7-6}";
    public static void main(String[] args) {
        System.out.println(getCurrentDateBefore30Day());
    }
    
    public static String formatCanlendar(String calendar){
        return calendar.substring(calendar.indexOf("{")+1,calendar.indexOf("}"));
    }
    
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");
	/** 定义常量 **/
	public static final String DATE_JFP_STR = "yyyyMM";
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
	static final String formatPattern = "yyyy-MM-dd";

	static final String formatPattern_Short = "yyyyMMdd";

	private static final long serialVersionUID = 1L;

    /**
     * 获取系统时间(格式：yyyyMMddHHmmss)
     * @return String 返回时间
     */
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(Calendar.getInstance().getTime());
    }
	/**
	 * 获取系统时间(格式：HHmm)
	 * @return String 返回时间
	 */
	public static String getHourTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		return sdf.format(Calendar.getInstance().getTime());
	}
    /**
     * 获取系统时间(格式：yyyyMMddHHmmss)
     * @return String 返回时间
     */
    public static String getStringTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    /**
     * 获取系统时间(格式：yyyyMMddHHmmssSSS)
     * @return String 返回时间
     */
    public static String getStringTimeFull() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return df.format(new Date());
    }

    /**
     * 判断日期是否属于今天日期(精确到天)
     * @param sDate 日期值
     * @return boolean 返回true表示是，false表示不是
     */
    public static boolean getSysIsToday(String sDate) {
        boolean falg = false;
        try {
            Date date = null;
            date = dateFormaterFull.get().parse(sDate);
            Date today = new Date();
            if (date != null) {
                String nowDate = dateFormater.get().format(today);
                String timeDate = dateFormater.get().format(date);
                if (nowDate.equals(timeDate)) {
                    falg = true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            AppLogMessageMgr.e("AppSysDateMgr-->>getSysIsToday", e.getMessage().toString());
        }
        return falg;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    
    private final static ThreadLocal<SimpleDateFormat> dateFormaterFull = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    
    /**
     * 检查日期是否有效
     * @param year 年
     * @param month 月
     * @param day 日
     * @return boolean
     */
    public static boolean getDateIsTrue(String year, String month, String day){
        try {
            String data = year + month + day;
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            simpledateformat.setLenient(false);
            simpledateformat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            AppLogMessageMgr.e("AppSysDateMgr-->>getDateIsTrue", e.getMessage().toString());
            return false;
        }
        return true;
    }

    /**
     * 判断两个字符串日期的前后
     * @param strdate1  字符串时间1
     * @param strdate2  字符串时间2
     * @return boolean
     * 日期与时间
     */
    public static boolean getDateIsBefore(String strdate1, String strdate2){
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            AppLogMessageMgr.i("AppSysDateMgr-->>getDateIsBefore-->>strdate1: ", strdate1);
            AppLogMessageMgr.i("AppSysDateMgr-->>getDateIsBefore-->>strdate2: ", strdate2);
            return df.parse(strdate1).before(df.parse(strdate2));
        } catch (ParseException e) {
            e.printStackTrace();
            AppLogMessageMgr.e("AppSysDateMgr-->>getDateIsBefore", e.getMessage().toString());
            return false;
        }
    }
    /**
     * 判断两个字符串日期的前后
     * @param strdate1  字符串时间1
     * @param strdate2  字符串时间2
     * @return boolean
     */
    public static boolean getDateIsEqual(String strdate1, String strdate2){
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(strdate1).equals(df.parse(strdate2));
        } catch (ParseException e) {
            e.printStackTrace();
            AppLogMessageMgr.e("AppSysDateMgr-->>getDateIsBefore", e.getMessage().toString());
            return false;
        }
    }

    /**
     * 判断两个字符串日期的前后
     * @param Longdate1  字符串时间1
     * @param Longdate2  字符串时间2
     * @return boolean
     */
    public static boolean getDateIsBefore(Long Longdate1, Long Longdate2){
        try {
            AppLogMessageMgr.i("AppSysDateMgr-->>getDateIsBefore-->>strdate1: ", Longdate1 + "");
            AppLogMessageMgr.i("AppSysDateMgr-->>getDateIsBefore-->>strdate2: ", Longdate2 + "");
            Longdate1 = (null == Longdate1) ? 0 : Longdate1;
            Longdate2 = (null == Longdate2) ? 0 : Longdate2;
            return  Longdate1 > Longdate2 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            AppLogMessageMgr.e("AppSysDateMgr-->>getDateIsBefore", e.getMessage().toString());
            return false;
        }
    }

    /**
     * 判断两个时间日期的前后
     * @param date1  日期1
     * @param date2  日期2
     * @return boolean
     */
    public static boolean getDateIsBefore(Date date1, Date date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateIsBefore(df.format(date1), df.format(date2));
    }
    
	/**
	 * 得到当前年
	 * 
	 * @return
	 */
	public static int getCurrYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 得到当前月份 注意，这里的月份依然是从0开始的
	 * 
	 * @return
	 */
	public static int getCurrMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 得到当前日
	 * 
	 * @return
	 */
	public static int getCurrDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到当前星期
	 * 
	 * @return
	 */
	public static int getCurrWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 得到当前小时
	 * 
	 * @return
	 */
	public static int getCurrHour() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR);
	}

	/**
	 * 得到当前分钟
	 * 
	 * @return
	 */
	public static int getCurrMinute() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 得到当前秒
	 * 
	 * @return
	 */
	public static int getCurrSecond() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.SECOND);
	}

	/**
	 * Date类型转换到Calendar类型
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar Date2Calendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * Calendar类型转换到Date类型
	 * 
	 * @param cal
	 * @return
	 */
	public static Date calendar2Date(Calendar cal) {
		return cal.getTime();
	}

	/**
	 * Date类型转换到Timestamp类型
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp date2Timestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * Calendar类型转换到Timestamp类型
	 * 
	 * @return
	 */
	public static Timestamp calendar2Timestamp(Calendar cal) {
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Timestamp类型转换到Calendar类型
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Calendar timestamp2Calendar(Timestamp timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		return cal;
	}

	/**
	 * 得到当前时间的毫秒数
	 * 
	 * @return
	 */
	public static Long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取任意时间后num天的时间
	 * 
	 * @param date
	 *            java.util.Date
	 * @param num
	 * @return
	 */
	public static Date nextDate(Date date, int num) {
		Calendar cla = Calendar.getInstance();
		cla.setTime(date);
		cla.add(Calendar.DAY_OF_YEAR, num);
		return cla.getTime();
	}

	/**
	 * 获取任意时间后num天的时间
	 * 
	 * @param date
	 *            String; <br>
	 *            格式支持�?<br>
	 *            yyyy-MM-dd HH:mm:ss <br>
	 *            yyyy年MM月dd日HH时mm分ss�?<br>
	 *            yyyy/MM/dd HH:mm:ss <br>
	 *            默认时间格式
	 * @param num
	 *            int
	 * @return java.util.Date
	 * @throws ParseException
	 */
	public static Date nextDate(String date, int num) throws ParseException {
		if (date == null)
			return null;
		SimpleDateFormat sdf = null;
		if (date.indexOf("-") != -1)
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else if (date.indexOf("-") != -1)
			sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss");
		else if (date.indexOf("/") != -1)
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		else if (date.indexOf("CST") != -1)
			sdf = new SimpleDateFormat();
		else
			System.out.println("no match format:");
		return nextDate(sdf.parse(date), num);
	}

	/**
	 * 获取当天时间num天后的时间<br>
	 * 如果num小于0则返回当前时间的前num天的时间<br>
	 * ，否则返回当天时间后num天的时间
	 * 
	 * @param num
	 *            int;
	 * @return java.util.Date
	 */
	public static Date nextDate(int num) {
		return nextDate(new Date(), num);
	}

	/**
	 * 取得当前日期是多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		/**
		 * 设置一年中第一个星期所需的最少天数，例如，如果定义第一个星期包含一年第一个月的第一天，则使用值 1 调用此方法。
		 * 如果最少天数必须是一整个星期，则使用值 7 调用此方法。
		 **/
		c.setMinimalDaysInFirstWeek(1);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(new Date());
	}

    /**
     * 获取当前日期前30天
     *
     * @return
     */
    public static String getCurrentDateBefore30Day() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -30);
        return new SimpleDateFormat(formatPattern).format(now.getTime());
    }
	/**
	 *获取一个月前的日期
	 * @param
	 * @return
	 */
	public static String getMonthAgo() {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getCurrentTimeMillis());
		calendar.add(Calendar.MONTH, -1);
		String monthAgo = simpleDateFormat.format(calendar.getTime());
		return monthAgo;
	}

	/**
	 * 获取制定毫秒数之前的日期
	 * 
	 * @param timeDiff
	 * @return
	 */
	public static String getDesignatedDate(long timeDiff) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		long nowTime = System.currentTimeMillis();
		long designTime = nowTime - timeDiff;
		return format.format(designTime);
	}

	/**
	 * 
	 * 获取前几天的日期
	 */
	public static String getPrefixDate(String count) {
		Calendar cal = Calendar.getInstance();
		int day = 0 - Integer.parseInt(count);
		cal.add(Calendar.DATE, day); // int amount 代表天数
		Date datNew = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(datNew);
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(date);
	}

	/**
	 * 字符串转换日期
	 * 
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		// str = " 2008-07-10 19:20:00 " 格式
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		if (!str.equals("") && str != null) {
			try {
				return format.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	// java中怎样计算两个时间如：“21:57”和“08:20”相差的分钟数、小时数 java计算两个时间差小时 分钟 秒 .
	public void timeSubtract() {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = null;
		Date end = null;
		try {
			begin = dfs.parse("2004-01-02 11:30:24");
			end = dfs.parse("2004-03-26 13:31:40");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60;
		System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分"
				+ second1 + "秒");
	}

	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, DATE_FULL_STR);
	}

	/**
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 两个时间比较
	 * 
	 * @param date
	 * @return
	 */
	public static int compareDateWithNow(Date date) {
		Date date2 = new Date();
		int rnum = date.compareTo(date2);
		return rnum;
	}

	/**
	 * 两个时间比较(时间戳比较)
	 * 
	 * @param date
	 * @return
	 */
	public static int compareDateWithNow(long date) {
		long date2 = dateToUnixTimestamp();
		if (date > date2) {
			return 1;
		} else if (date < date2) {
			return -1;
		} else {
			return 0;
		}
	}
	/**
	 * 获取系统当前时间上下午
	 *
	 * @return
	 */
	public static String getNowTime_A() {
		String str = null;
		Date d = new Date();
		if (d.getHours() < 13) {
			str = "上午";
		} else {
			str = "下午";
		}
		return str;
	}
	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getNowTime(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	/**
	 * 获取系统当前计费期
	 * 
	 * @return
	 */
	public static String getJFPTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
		return df.format(new Date());
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param date
	 *            date 需要转换的日期 yyyy-MM-dd HH:mm:ss
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp(String date) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date)
					.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 *            date 需要转换的日期 yyyy-MM-dd
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp(String date, String dateFormat) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 将当前日期转换成Unix时间戳
	 * 
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param timestamp timestamp 时间戳
	 * @return String 日期字符串
	 */
	public static String unixTimestampToDate(long timestamp) {
		SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sd.format(new Date(timestamp));
	}

    /**
     * 格式化时间为
     * @param timeMillis
     * @return
     */
	public static String formatTimeInMillis(long timeMillis){
	    String time = "2018-01-01";
        try {
            Date curDate = new Date(timeMillis);// 获取当前时间
            SimpleDateFormat format = new SimpleDateFormat(DATE_SMALL_STR);
            time = format.format(curDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;
    }
    
	public static int getSystemTime() {
		int result = -1;
		try {
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String time = format.format(curDate);
			result = Integer.parseInt(time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 20130918;
		}
		return result;
	}

	public static int getTime() {
		int result = -1;
		try {
			URL url = new URL("http://www.bjtime.cn");
			URLConnection uc = url.openConnection();// 生成连接对象
			uc.connect(); // 发出连接
			long ld = uc.getDate(); // 取得网站日期时间
			Date date = new Date(ld); // 转换为标准时间对象
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String time = format.format(date);
			result = Integer.parseInt(time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return getSystemTime();
		}
		return result;
	}

	/**
	 * long time to string
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}


	/**
	 * 時間戳转为yyyy-MM-dd
	 */

	public static String getDateStr(long time){

		Date date=new Date(time);
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
		return sdf1.format(date);
	}
	/**
	 * 2018-08-05T16:00:00.000Z转为yyyy-MM-dd
	 */

	public static String getDateFormat(String create_time){

		String format = "";
		if (create_time != null && create_time != "NULL" && create_time != "") {
			if (isDate(create_time)) {
				format = create_time;
			} else {
				//转换日期格式(将Mon Jun 18 2018 00:00:00 GMT+0800 (中国标准时间) 转换成yyyy-MM-dd)
				create_time = create_time.replace("Z", " UTC");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
				Date d = null;//Mon Mar 06 00:00:00 CST 2017
				try {
					d = sdf1.parse(create_time);
					format= formationDate(d);
				} catch (ParseException e) {
					e.printStackTrace();
				}
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				format = sdf.format(d);//2017-03-06
			}
		}

		return format;
	}
	/**
	 * 将时间戳转成2018-08-05T16:00:00.000Z
	 */

	public static String getDateFormat(long time){

		Date date=new Date(time);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		return sdf1.format(date);
	}
	/**
	 * 2018-08-05T16:00:00.000Z获取data
	 */

	public static Date getDate(String create_time){

		Date format =null;
		if (create_time != null && create_time != "NULL" && create_time != "") {

				//转换日期格式(将Mon Jun 18 2018 00:00:00 GMT+0800 (中国标准时间) 转换成yyyy-MM-dd)
				create_time = create_time.replace("Z", " UTC");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");

				try {
					format = sdf1.parse(create_time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				format = sdf.format(d);//2017-03-06

		}

		return format;
	}
	/**
	 * 判断日期格式和范围
	 */
	private static boolean isDate(String date)
	{

		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(date);
		boolean dateType = mat.matches();
		return dateType;
	}

	/**
	 * 获取时间差
	 * @param date_create
	 * @return
	 */
	public static String formationDate(Date date_create) {
		String dateString = null;
		// 获取系统当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now = new Date();
		try {
			Date date = date_create;
			long endTime = now.getTime();
			long currentTime = date.getTime();
			// 计算两个时间点相差的秒数
			long seconds = (endTime - currentTime);

			if (date.getYear()==now.getYear()&&date.getMonth()==now.getMonth()&&date.getDay()==now.getDay()){//判断是否是同一天
				//当天时间显示时分
				dateString = new SimpleDateFormat("HH:mm").format(date);
			}else if (date.getYear() != now.getYear()){
				//超过一年显示年月日
				dateString = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
			}else {
				//其余都显示
				dateString = new SimpleDateFormat("MM-dd HH:mm").format(date);
			}
//			if (seconds < 10 * 1000) {
////				dateString = "刚刚";
//			} else if (seconds < 60 * 1000) {
////				dateString = seconds / 1000 + "秒前";
//			} else if (seconds < 60 * 60 * 1000) {
////				dateString = seconds / 1000 / 60 + "分钟前";
//			} else if (seconds < 60 * 60 * 24 * 1000) {
////				dateString = seconds / 1000 / 60 / 60 + "小时前";
//			} else if (seconds < 60 * 60 * 24 * 1000 * 30L) {
////				dateString = seconds / 1000 / 60 / 60 / 24 + "天前";
//				//今年并且大于30天显示具体月日
//			} else if (date.getYear() == now.getYear()) {
//				dateString = new SimpleDateFormat("MM-dd").format(date.getTime());
//				//大于今年显示年月日
//			} else if (date.getYear() != now.getYear()) {
//				dateString = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
//			} else {
//				dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}
	/**
	 * 获取访客轨迹
	 * @param date_create
	 * @return
	 */
	public static String formationDate_V(Date date_create) {
		String dateString = null;
		// 获取系统当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now = new Date();
		try {
			Date date = date_create;
			long endTime = now.getTime();
			long currentTime = date.getTime();
			// 计算两个时间点相差的秒数
			long seconds = (endTime - currentTime);

			if (date.getYear()==now.getYear()&&date.getMonth()==now.getMonth()&&date.getDay()==now.getDay()){//判断是否是同一天
				//当天时间显示时分
				dateString = new SimpleDateFormat("HH:mm:ss").format(date);
			}else if (date.getYear() != now.getYear()){
				//超过一年显示年月日
				dateString = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
			}else {
				//其余都显示
				dateString = new SimpleDateFormat("MM-dd HH:mm").format(date);
			}
//			if (seconds < 10 * 1000) {
////				dateString = "刚刚";
//			} else if (seconds < 60 * 1000) {
////				dateString = seconds / 1000 + "秒前";
//			} else if (seconds < 60 * 60 * 1000) {
////				dateString = seconds / 1000 / 60 + "分钟前";
//			} else if (seconds < 60 * 60 * 24 * 1000) {
////				dateString = seconds / 1000 / 60 / 60 + "小时前";
//			} else if (seconds < 60 * 60 * 24 * 1000 * 30L) {
////				dateString = seconds / 1000 / 60 / 60 / 24 + "天前";
//				//今年并且大于30天显示具体月日
//			} else if (date.getYear() == now.getYear()) {
//				dateString = new SimpleDateFormat("MM-dd").format(date.getTime());
//				//大于今年显示年月日
//			} else if (date.getYear() != now.getYear()) {
//				dateString = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
//			} else {
//				dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}
	/*
	 * 毫秒转化时分秒毫秒
	 */
	public static String formatTime(Long ms) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		StringBuffer sb = new StringBuffer();
		if(day > 0) {
			sb.append(day+"天");
		}
		if(hour > 0) {
			sb.append(hour+"小时");
		}
		if(minute > 0) {
			sb.append(minute+"分");
		}
		if(second > 0) {
			sb.append(second+"秒");
		}
		if(milliSecond > 0) {
			//sb.append(milliSecond+"毫秒");
		}
		return sb.toString();
	}

	/**
	 * 获取真实的年龄
	 *
	 * @param idNum
	 * @return
	 */
	public static int getRealYear(String idNum) {

		Calendar cal = Calendar.getInstance();
		//当前年
		int currentYear = cal.get(Calendar.YEAR);
		//当前月
		int currentMonth = (cal.get(Calendar.MONTH)) + 1;
		//当前月的第几天：即当前日
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);


		int birthYear = 0;
		int birthMonth = 0;
		int birthDay = 0;
		int realYear = 0;

		if (!TextUtils.isEmpty(idNum)) {
			String birthDate = idNum.substring(6, 14);

			if (!TextUtils.isEmpty(birthDate) && birthDate.length() == 8) {
				birthYear = Integer.valueOf(birthDate.substring(0, 4));
				birthMonth = Integer.valueOf(birthDate.substring(4, 6));
				birthDay = Integer.valueOf(birthDate.substring(6, 8));
			}

			realYear = currentYear - birthYear;

			if (birthMonth > currentMonth) {
				realYear = realYear - 1;
			} else if (birthMonth == currentMonth) {
				if (birthDay > currentDay) {
					realYear = realYear - 1;
				} else {
					realYear = realYear;
				}
			} else {
				realYear = realYear;
			}
		}

		return realYear;
	}

	/**
	 * 判断是否小于或者等于当前age的年龄
	 *
	 * @param age
	 * @return
	 */
	public static boolean isChildUnderTargetAge(String idNum, int age, boolean isIncludeAge) {
		int realYear = getRealYear(idNum);
		if (isIncludeAge) {
			if (realYear <= age) {
				return true;
			} else {
				return false;
			}
		} else {
			if (realYear < age) {
				return true;
			} else {
				return false;
			}
		}

	}
}
