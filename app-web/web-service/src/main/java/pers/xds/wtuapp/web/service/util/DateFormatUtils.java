package pers.xds.wtuapp.web.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DeSen Xu
 * @date 2022-12-08 16:34
 */
public class DateFormatUtils {

    private DateFormatUtils() {}

    private static final SimpleDateFormat NORMAL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");


    /**
     * 将时间格式化为yyyy-MM-dd HH-mm-ss的形式
     * @param date 日期
     * @return 格式化后的结果
     */
    public static String toLocalString(Date date) {
        return NORMAL_FORMAT.format(date);
    }
}
