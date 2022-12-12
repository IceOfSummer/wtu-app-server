package pers.xds.wtuapp.web.redis.common;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Supplier;

/**
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
