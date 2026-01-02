package ru.mrbedrockpy.bedLib.config.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.mrbedrockpy.bedLib.config.annotation.CommentType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ConfigData {

    private final String name;
    private final ConfigFieldData<?>[] fields;
    private final ConfigData[] categories;
    private final Class<?> clazz;

    @Nullable private CommentType commentType;
    @Nullable private List<String> comment;

}
