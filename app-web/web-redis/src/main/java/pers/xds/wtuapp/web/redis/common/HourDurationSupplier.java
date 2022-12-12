package pers.xds.wtuapp.web.redis.common;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.function.Supplier;

/**
 * 获取当前小时的 59分59秒时对应的时间戳
 * @author DeSen Xu
 * @date 2022-12-12 22:44
 */
public class HourDurationSupplier implements Supplier<LocalDateTime> {
    @Override
    public LocalDateTime get() {
        Calendar instance = Calendar.getInstance();
        return LocalDateTime.of(
                instance.get(Calendar.YEAR),
                instance.get(Calendar.MONTH) + 1,
                instance.get(Calendar.DATE),
                instance.get(Calendar.HOUR_OF_DAY),
                59,
                59
        );
    }
}
