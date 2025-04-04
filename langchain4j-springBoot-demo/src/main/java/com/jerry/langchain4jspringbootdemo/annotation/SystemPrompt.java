package com.jerry.langchain4jspringbootdemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/24 8:45
 * @注释 自定义注解 用于指定系统提示词
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemPrompt {
    String systemPrompt() default "你是一个星座专家";

}
