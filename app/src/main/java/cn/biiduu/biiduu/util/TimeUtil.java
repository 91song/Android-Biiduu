package cn.biiduu.biiduu.util;

import android.annotation.SuppressLint;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {
    private static final long COUNT_DOWN_OFF_TIME = -8 * 60 * 60 * 1000;
    private static final int HOUR_SECONDS = 60 * 60;

    /**
     * 获取当前的日期
     */
    public static String getCurrentDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 获取当前的日期
     */
    public static String getCurrentDay2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        return df.format(new Date());
    }

    /**
     * 获取当前的日期
     */
    public static String getCurrentDay3() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(new Date());
    }

    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm
     */
    private static String getCurrentTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTimeToss(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    public static String getTimeToDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(time);
    }

    /**
     * 返回当前时间的格式为 yyyy-MM-dd
     */
    public static String getCurrentSimpleTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * 返回当前时间的格式为 yyyy/MM/dd
     */
    public static String transformTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(time);
    }

    /**
     * 返回当前时间的格式为 yyyy-MM-dd
     */
    public static String getCurrentSimpleTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date().getTime());
    }

    /**
     * 格式化时间
     */
    public static String formatTime(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    /**
     * 格式化倒计时
     */
    public static String formatCountDown(long offsetTime) {
        return formatTime(offsetTime + COUNT_DOWN_OFF_TIME, "HH:mm:ss");
    }

    /**
     * 获取过去多少时间
     */
    public static String getPastedTimeDesc(long start) {
        long timeMillis = System.currentTimeMillis();
        int offsetTime = (int) ((timeMillis - start) / 1000);

        if (offsetTime <= 60) {
            return "刚刚";
        } else if (offsetTime < HOUR_SECONDS) {
            return (offsetTime / 60) + "分钟前";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int curDay = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTimeInMillis(start);
            int lastDay = calendar.get(Calendar.DAY_OF_MONTH);

            int offsetDay = curDay - lastDay;

            if (offsetDay == 1) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                return "昨天 " + sdf.format(start);
            } else if (offsetDay == 0) {
                return (offsetTime / HOUR_SECONDS) + "小时前";
            }
            return getCurrentTime(start);
        }
    }

    /**
     * 将毫秒转成分秒
     *
     * @param time 毫秒数
     */
    public static String timeFormatBySecond(long time) {
        int sec = (int) time / 1000;
        int min = sec / 60;
        sec = sec % 60;
        if (min < 10) {
            if (sec < 10) {
                return "0" + min + ":0" + sec;
            } else {
                return "0" + min + ":" + sec;
            }
        } else {
            if (sec < 10) {
                return min + ":0" + sec;
            } else {
                return min + ":" + sec;
            }
        }
    }

    /**
     * 计算指定时间距离当前天数
     *
     * @param ms 指定日期的时间戳
     */
    public static long transformToday(long ms) {
        long currentMillis = System.currentTimeMillis();
        return ((ms - currentMillis) / 1000 / 60 / 60 / 24);
    }

    /**
     * 计算指定日期对应星期数
     *
     * @param ms 指定日期的时间戳
     */
    public static String getWeek(long ms) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = format.parse(TimeUtil.getTimeToDay(ms));
        } catch (ParseException e) {
            Logger.i("时间格式解析错误");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(date);
    }
}
