package com.hecong.cssystem.wight;

import org.aspectj.lang.annotation.Aspect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 切面管理类
 */
@Aspect
public class AspectManager {

    //SingleClick.java
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SingleClick {

    }
}
