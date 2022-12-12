package pers.xds.wtuapp.web.redis.common;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.function.Supplier;

/**
 * 返回当天 23:59:59 59:59 的`LocalDateTime`
 * @author DeSen Xu
 * @date 2022-12-12 22:42
 */
public class DayDurationSupplier implements Supplier<LocalDateTime> {
    @Override
    public LocalDateTime get() {
        Calendar instance = Calendar.getInstance();
        return LocalDateTime.of(
                instance.get(Calendar.YEAR),
                instance.get(Calendar.MONTH) + 1,
                instance.get(Calendar.DATE),
                23,
                59,
                59
        );
    }
}
