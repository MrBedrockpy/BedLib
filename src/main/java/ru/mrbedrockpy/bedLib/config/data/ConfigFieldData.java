package ru.mrbedrockpy.bedLib.config.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import ru.mrbedrockpy.bedLib.config.annotation.CommentType;

import java.lang.reflect.Field;
import java.util.List;

@Getter
@AllArgsConstructor
public class ConfigFieldData<T> {

    private final String name;
    private final Class<T> type;
    private final Field field;

    @Nullable private final CommentType commentType;
    @Nullable private final List<String> comment;

    public T getValue() {
        try {
            return (T) field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(Object value) {
        try {
            field.set(null, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
