package com.ems.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author valarchie
 */
@Retention(RetentionPolicy.RUNTIME)//表示这个注解会保留到运行时。可以通过反射读取，例如 MyClass.class.getAnnotation(MyAnnotation.class)。
@Target(ElementType.TYPE)//表示这个注解只能用在类型声明上。包括：类、接口、枚举、记录类、注解类型等。不能用在方法、字段、参数上。
public @interface ExcelSheet {
    /**
     * sheet名称
     */
    String name() default "";

}
