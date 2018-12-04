package org.wang.kuaijiback.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.FIELD)
public @interface Fields {
    String value();
}
