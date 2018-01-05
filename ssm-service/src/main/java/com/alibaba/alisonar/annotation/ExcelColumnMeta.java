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
	
    public int colIndex() default -1; //从0开始
    
    public String colName() default "";

}
