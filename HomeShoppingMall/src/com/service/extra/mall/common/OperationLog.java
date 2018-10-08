package com.service.extra.mall.common;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Documented
public @interface OperationLog {
	String operationContent() default "";
	String operationTables() default"";
}
