package com.epam.lab.spider.controller.utils.validation.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pattern {
    String regexp();

    Pattern.Flag[] flags() default {};

    String message() default "{com.epam.lab.spider.controller.utils.validation.annotation.Pattern.message}";




    enum Flag {
        UNIX_LINES(1),
        CASE_INSENSITIVE(2),
        COMMENTS(4),
        MULTILINE(8),
        DOTALL(32),
        UNICODE_CASE(64),
        CANON_EQ(128);

        private final int value;

        Flag(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}