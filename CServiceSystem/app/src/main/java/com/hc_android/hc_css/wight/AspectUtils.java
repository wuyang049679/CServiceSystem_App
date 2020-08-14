package com.hc_android.hc_css.wight;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AspectUtils {

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClickListener(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{

    }
}
