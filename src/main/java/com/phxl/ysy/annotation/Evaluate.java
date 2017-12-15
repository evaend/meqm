/**
* 普华信联公司源代码，版权归普华信联公司所有。
*
* 项目名称	：	commonModule
* 创建日期	：	2017年8月31日
* 所在包		：	com.phxl.ysy.commonModule.annotation
* 文件名称	：	Evaluate.java
* 修改历史	：
* 1.[2017年8月31日]创建文件 by 黄文君
*/

package com.phxl.ysy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 性能评估使用
 * @date	2017年8月31日 下午6:54:30
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Evaluate {

	String value() default "";
	
}
