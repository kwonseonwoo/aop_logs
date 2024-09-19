package com.example.demo.aop.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReflectionLogs {

    int depth() default 1;

    String[] names() default {};

    LogType type() default LogType.ALL;


    enum LogType {
        ALL("A", "전체 대상 로그 처리"),
        PART_FIELD("PF", "특정 필드 로그 처리"),
        ;

        private final String code;
        private final String description;

        LogType(String code, String desc) {
            this.code = code;
            this.description = desc;
        }
    }
}
