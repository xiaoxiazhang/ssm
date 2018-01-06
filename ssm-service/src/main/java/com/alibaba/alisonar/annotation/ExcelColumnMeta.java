/**
 * 
 */
package com.alibaba.alisonar.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wb-zxx263018
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ExcelColumnMeta {
	
	public int outputColIndex() default -1; //输出列
	
    public int inputColIndex() default -1; //导入列
    
    public String colName() default ""; //列名

}
