package pers.xds.wtuapp.web.aop;

import pers.xds.wtuapp.web.redis.common.Duration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 针对每个用户的限流注解
 * @author DeSen Xu
 * @date 2023-01-10 21:12
 * @see pers.xds.wtuapp.web.aop.aspect.RateLimitAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimit {

    /**
     * 最大允许的调用次数
     */
    int value();

    /**
     * 名称前缀, 存放到redis里时的key前缀. 若不提供，则自动由类名+方法名组成
     */
    String prefix() default "";

    /**
     * 计算周期
     */
    Duration duration() default Duration.DAY;

    /**
     * 当用户被限流时提示的消息
     */
    String limitMessage() default "";

}
