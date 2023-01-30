package pers.xds.wtuapp.web.redis.common;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Supplier;

/**
 * 用于计算结束时间。
 * <p>
 * 请注意，请不要被枚举常量的名称所误解，例如在使用{@link Duration#HOUR}时，并不是从当前时间开始，在一小时结束！
 * 例如当前时间为<code>14:12</code>那么结束时间为<code>14:59</code>，而不是<code>15:12</code>
 * @author DeSen Xu
 * @date 2022-12-12 22:26
 */
public enum Duration {

    /**
     * 一小时结束
     */
    HOUR(new HourDurationSupplier()),
    /**
     * 持续到本日结束
     */
    DAY(new DayDurationSupplier()),
    /**
     * 持续到本周日结束
     */
    WEEK(new DayDurationSupplier(), TemporalAdjusters.next(DayOfWeek.SATURDAY)),
    /**
     * 持续到本月结束
     */
    MONTH(new DayDurationSupplier(), TemporalAdjusters.lastDayOfMonth());

    private final TemporalAdjuster adjuster;

    private final Supplier<LocalDateTime> dateTimeSupplier;


    /**
     * @param dateTimeSupplier 给定一个时间生成方式，并将其返回值作为结束时间, <b>不可以为空</b>
     */
    Duration(Supplier<LocalDateTime> dateTimeSupplier) {
        this(dateTimeSupplier, null);
    }

    Duration(Supplier<LocalDateTime> dateTimeSupplier, TemporalAdjuster adjuster) {
        this.adjuster = adjuster;
        this.dateTimeSupplier = dateTimeSupplier;
    }

    /**
     * 计算当前时间到截止时间之间有多少毫秒
     * @return 间隔的毫秒
     */
    public long countDuration() {
        LocalDateTime dateTime = this.dateTimeSupplier.get();
        if (adjuster != null) {
            dateTime = dateTime.with(adjuster);
        }
        return dateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() - System.currentTimeMillis();
    }
}
