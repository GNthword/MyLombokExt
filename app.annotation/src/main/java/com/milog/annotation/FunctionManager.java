package com.milog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by miloway on 2018/7/20.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD,ElementType.LOCAL_VARIABLE})
public @interface FunctionManager {

    String value() default "";

}
