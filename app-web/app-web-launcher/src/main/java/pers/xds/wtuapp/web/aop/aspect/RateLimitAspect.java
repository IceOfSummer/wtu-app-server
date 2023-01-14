package pers.xds.wtuapp.web.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.aop.RateLimit;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.redis.CounterCache;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;

/**
 * 限流切面。当用户流量达到指定次数后，会返回一个{@link ResponseTemplate}对象
 * @author DeSen Xu
 * @date 2023-01-10 21:15
 */
@Aspect
@Component
public class RateLimitAspect {

    private CounterCache counterCache;

    @Autowired
    public void setCounterCache(CounterCache counterCache) {
        this.counterCache = counterCache;
    }

    @Pointcut("@annotation(pers.xds.wtuapp.web.aop.RateLimit)")
    public void test() {}

    @Around("test() && @annotation(rateLimit)")
    public Object doRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        StringBuilder key = new StringBuilder(60);
        if (rateLimit.prefix().isEmpty()) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            key.append(joinPoint.getTarget().getClass().getName()).append('.').append(signature.getName()).append(':');
        } else {
            key.append(rateLimit.prefix()).append(':');
        }
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        key.append(userPrincipal.getId());
        String keyStr = key.toString();
        int invokeCount = counterCache.getInvokeCount(keyStr, rateLimit.duration());
        if (invokeCount < rateLimit.value()) {
            counterCache.increaseInvokeCount(keyStr);
            return joinPoint.proceed();
        } else {
            if (rateLimit.limitMessage().isEmpty()) {
                return ResponseTemplate.fail(ResponseCode.RATE_LIMIT);
            } else {
                return ResponseTemplate.fail(ResponseCode.RATE_LIMIT, rateLimit.limitMessage());
            }
        }
    }


}
