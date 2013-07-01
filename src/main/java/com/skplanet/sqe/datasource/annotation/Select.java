package com.skplanet.sqe.datasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 28.
 * Time: 오전 10:08
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Select {
}
