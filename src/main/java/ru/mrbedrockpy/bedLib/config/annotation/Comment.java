package ru.mrbedrockpy.bedLib.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
    CommentType type() default CommentType.INLINE;
    String[] comment();
}
