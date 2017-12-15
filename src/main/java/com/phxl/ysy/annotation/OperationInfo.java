package com.phxl.ysy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationInfo {
	/** 需要记录日志的功能名称 */
	public String operationName() default "";
	
	/**
	 *  需要记录日志的功能所属的模块id
	 */
	public String moduleId() default "";
	
	/**
	 *  需要记录日志的功能所属的模块名称
	 */
	public String moduleName() default "";
	
	/**
	 * 需要记录的操作参数
	 */
	public String keys() default "";
	
	/**
	 * 需要记录的操作参数对应的中文名称
	 */
	public String values() default "";
}