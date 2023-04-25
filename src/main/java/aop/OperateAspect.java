package aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author Joe
 */
@Component
@Aspect
public class OperateAspect {
    /**
     * 定义织入点
     * 横切逻辑
     * 织入
     */
    @Pointcut("@annotation(aop.RecordOperate)")
    public void pointcut() {
    }

    /**
     * 对这个记录日志流水的线程池实时性要求不高，
     */
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)
    );

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 直接执行方法，拿到返回结果
        Object result = proceedingJoinPoint.proceed();

        // 接下来记录流水不影响主链路，使用异步的方法来做
        threadPoolExecutor.execute(() -> {
            try {
                MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
                RecordOperate annotation = methodSignature.getMethod().getAnnotation(RecordOperate.class);

                Class<? extends Convert> convert = annotation.convert();
                Convert logConvert = convert.newInstance();
                OperateLogDO operateLogDO = logConvert.convert(proceedingJoinPoint.getArgs()[0]);
                operateLogDO.setDesc(annotation.desc());
                operateLogDO.setResult(result.toString());

                System.out.println("Insert operateLog " + JSON.toJSONString(operateLogDO));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}
