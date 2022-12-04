package gb.spring.emarket.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TimingCollectingAspect {

    @Pointcut("execution(public * gb.spring.emarket.controllers.ProductRestController.*(..))")
    private void productControllerMethod() {
    }

    @Pointcut("execution(public * gb.spring.emarket.controllers.UserRestController.*(..))")
    private void userControllerMethod() {
    }

    @Pointcut("execution(public * gb.spring.emarket.controllers.AuthenticationController.*(..))")
    private void authControllerMethod() {
    }


    @Pointcut("execution(public * gb.spring.emarket.controllers.ShoppingCartRestController.*(..))")
    private void cartControllerMethod() {
    }

    @Pointcut("execution(public * gb.spring.emarket.controllers.OrderRestController.*(..))")
    private void orderControllerMethod() {
    }


    @Around("productControllerMethod()")
    public Object addProductTiming(ProceedingJoinPoint pjp) throws Throwable {
        long startingTime = System.currentTimeMillis();
        Object object = pjp.proceed();
        long endingTime = System.currentTimeMillis();
        Statistic.addProductsTime(endingTime - startingTime);

        return object;
    }

    @Around("userControllerMethod() || authControllerMethod()")
    public Object addUserTiming(ProceedingJoinPoint pjp) throws Throwable {
        long startingTime = System.currentTimeMillis();
        Object object = pjp.proceed();
        long endingTime = System.currentTimeMillis();
        Statistic.addUsersTime(endingTime - startingTime);

        return object;
    }

    @Around("cartControllerMethod()")
    public Object addCartTiming(ProceedingJoinPoint pjp) throws Throwable {
        long startingTime = System.currentTimeMillis();
        Object object = pjp.proceed();
        long endingTime = System.currentTimeMillis();
        Statistic.addCartTime(endingTime - startingTime);

        return object;
    }

    @Around("orderControllerMethod()")
    public Object addOrderTiming(ProceedingJoinPoint pjp) throws Throwable {
        long startingTime = System.currentTimeMillis();
        Object object = pjp.proceed();
        long endingTime = System.currentTimeMillis();
        Statistic.addOrderTime(endingTime - startingTime);

        return object;
    }

}
