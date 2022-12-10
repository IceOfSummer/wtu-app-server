package pers.xds.wtuapp.web.redis;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

/**
 * 使用redis做计数器
 * @author DeSen Xu
 * @date 2022-12-04 21:21
 */
public interface CounterCache {

    /**
     * 获取参数为某个key时，该方法的调用次数。统计次数每日0点清空
     * @param key 唯一的key
     * @return 调用次数(<b>包括</b>该次调用)
     */
    int getInvokeCount(String key);

    /**
     * 获取参数为某个key时，该方法的调用次数。
     * @param key 唯一的key
     * @param duration 清零时间
     * @return 调用次数(<b>包括</b>该次调用)
     */
    int getInvokeCount(String key, Duration duration);

    enum Duration {
        /**
         * 持续到本日结束
         */
        DAY(null),
        /**
         * 持续到本周日结束
         */
        WEEK(TemporalAdjusters.next(DayOfWeek.SATURDAY)),
        /**
         * 持续到本月结束
         */
        MONTH(TemporalAdjusters.lastDayOfMonth());

        private final TemporalAdjuster adjuster;

        Duration(TemporalAdjuster adjuster) {
            this.adjuster = adjuster;
        }

        /**
         * 计算当前时间到截止时间之间有多少毫秒
         * @return 间隔的毫秒
         */
        public long countDuration() {
            Calendar instance = Calendar.getInstance();
            LocalDateTime dateTime = LocalDateTime.of(
                    instance.get(Calendar.YEAR),
                    instance.get(Calendar.MONTH) + 1,
                    instance.get(Calendar.DATE),
                    23,
                    59,
                    59
            );
            if (adjuster != null) {
                dateTime = dateTime.with(adjuster);
            }
            return dateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() - System.currentTimeMillis();
        }

    }

    /**
     * 为某个key的值加一，一定要先调用getInvokeCount后再调用该方法，以防key不能及时过期
     * @param currentVal 当前的值
     * @param key 唯一的key
     */
    void increaseInvokeCount(int currentVal, String key);

    /**
     * 异步的为某个key加一，一定要先调用getInvokeCount后再调用该方法，以防key不能及时过期
     * @param currentVal 当前的值
     * @param key 唯一的key
     */
    void increaseInvokeCountAsync(int currentVal, String key);



}
