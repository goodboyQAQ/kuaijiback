package org.wang.kuaijiback.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelTitle {
    String[] value();
    String[] title();
}
