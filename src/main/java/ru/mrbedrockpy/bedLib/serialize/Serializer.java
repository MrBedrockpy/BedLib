package ru.mrbedrockpy.bedLib.serialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mrbedrockpy.bedLib.manager.ManagerItem;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public class Serializer<T> implements ManagerItem {

    private final Class<T> type;
    private final Function<T, String> serializer;
    private final Function<String, T> deserializer;

    public String serialize(T value) {
        return serializer.apply(value);
    }
    public T deserialize(String value) {
        return deserializer.apply(value);
    }

    @Override
    public String getId() {
        return type.getName();
    }
}
