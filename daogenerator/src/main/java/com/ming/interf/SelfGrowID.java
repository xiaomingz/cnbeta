package com.ming.interf;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自增长Id
 * Created by ming on 16/2/26.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface SelfGrowID {
}
