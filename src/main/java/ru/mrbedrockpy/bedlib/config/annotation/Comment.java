package ru.mrbedrockpy.bedlib.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
    CommentType type() default CommentType.TOP;
    String[] comment();
}
