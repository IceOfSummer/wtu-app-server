package pers.xds.wtuapp.web.redis;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pers.xds.wtuapp.web.redis.common.Duration;

class CounterCacheTest {


    @Test
    public void testDuration() {
        long time = Duration.MONTH.countDuration();
        Assertions.assertTrue(time > 0);
        // day duration
        System.out.println(time / 1000 / 60 / 60 / 24);
    }

}
