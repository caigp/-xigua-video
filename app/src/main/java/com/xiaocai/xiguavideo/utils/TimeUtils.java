package com.xiaocai.xiguavideo.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimeUtils {

    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_1 = "yyyy-MM-dd";

    public static final String DATE_WEEK = "yyyy-MM-dd EEEE";

    public static final String TIME = "HH:mm:ss";

    public static final String DATE = "yyyy年MM月dd日";

    private static SimpleDateFormat sdf = new SimpleDateFormat(PATTERN, Locale.getDefault());

    public static String getNow(String pattern) {
        sdf.applyPattern(pattern);
        return sdf.format(System.currentTimeMillis());
    }

    public static String format(String pattern, long t) {
        sdf.applyPattern(pattern);
        return sdf.format(t);
    }
}
