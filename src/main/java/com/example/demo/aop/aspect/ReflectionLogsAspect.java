package com.example.demo.aop.aspect;

import com.example.demo.aop.annotation.ReflectionLogs;
import com.example.demo.aop.service.LogStrategy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@Component
@Slf4j
public class ReflectionLogsAspect {

    private static final String BODY = "body";

    @Around("@annotation(com.example.demo.aop.annotation.ReflectionLogs)")
    public Object reflectionLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[AOP Logs] Method Request ... : {}", joinPoint.getSignature().getName());

        // Target Method 수행
        Object result = joinPoint.proceed();

        // Target Method의 @ReflectionLogs Annotation 정보를 읽어온다.
        Method method = getCallMethod(joinPoint);

        if(!ObjectUtils.isEmpty(method)) {
            //Controller에서 반환된 객체는 ResponseEntity로 감싸져 있기 때문에,
            //접근하고자 하는 실제 반환 객체를 얻기 위해 Reflection으로 데이터를 꺼내온다.
            Object realObject = getFieldObject(result, BODY, true);
            ReflectionLogs annotation = method.getAnnotation(ReflectionLogs.class);

            int depth = annotation.depth(); // 대상 필드의 depth
            String[] names = annotation.names(); // reflection 처리를 위한 field name
            ReflectionLogs.LogType type = annotation.type(); // enum type

            // enum type을 기반으로 구현체를 찾은 후, 수행한다.
            LogStrategy strategy = findLogStrategy(type);
            strategy.process(depth, realObject, names);
        }

        log.info("[AOP Logs] Method Response ... : {}", joinPoint.getSignature().getName());

        return result;
    }

    /**
     * 호출된 Method 정보를 조회한다.
     * */
    private Method getCallMethod(ProceedingJoinPoint joinPoint) {

        // 타깃 메서드의 name을 추출한 후
        // 객체의 전체 메서드에서 타깃 메서드와 일치하는 것을 찾아 반환한다.
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetBean = joinPoint.getTarget().getClass();
        Method[] methods = ReflectionUtils.getDeclaredMethods(targetBean);

        return Arrays.stream(methods)
                .filter(method -> methodName.equals(method.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 객체와 필드명을 받아 Object를 조회한다.
     * */
    private Object getFieldObject(Object data, String name, boolean superClass) {
        // ResponseEntity의 Body는 해당 객체가 아닌,
        // 상속받는 HttpEntity에 존재하므로 Field를 가져올 때 SuperClass 타입을 지정해야 한다.
        Field field = ReflectionUtils.findField(superClass ? data.getClass().getSuperclass() : data.getClass(), name);
        field.setAccessible(true);
        return ReflectionUtils.getField(field, data);
    }

    /**
     * Enum Type에 따라 구현체를 반환한다.
     *
     * ALL - 전체 로그를 기록한 후 종료한다.
     * PART_FIELD - 특정 필드를 뽑은 후 기록한다.
     * */
    private LogStrategy findLogStrategy(ReflectionLogs.LogType type) {
        return ReflectionLogs.LogType.ALL == type ? getAllStrategy() : getPartialStrategy();
    }

    private LogStrategy getAllStrategy() {
        return (depth, data, names) -> {
            log.info("All Type Strategy ... depth: {}, Object: {}, names: {}", depth, data, names);
            // TODO... Repository 호출 혹은 정보를 활용하여 후처리 가능..
        };
    }

    private LogStrategy getPartialStrategy() {
        return (depth, data, names) -> {
            log.info("Partial Type Strategy ... depth: {}, Object: {}, names: {}", depth, data, names);

            for(int i=0; i<depth; i++) {
                // 마지막 depth를 수행할 때 로그를 기록한다.
                if(i == depth - 1) {
                    String logs;
                    // Object Type이 일반 객체인지, Collection인지 확인한 후 로그를 기록한다.
                    if(data instanceof Collection<?> collection) {
                        logs = collection.stream().map(obj -> String.valueOf(getFieldObject(obj, names[depth-1], false)))
                                .collect(Collectors.joining(","));
                    } else {
                        logs = String.valueOf(getFieldObject(data, names[i], false));
                    }

                    log.info("Partial Type Strategy Logs... Field Data: {}", logs);
                    break;
                }
                data = getFieldObject(data, names[i], false);
            }
        };
    }
}
