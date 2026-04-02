package ru.mrbedrockpy.bedLib.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public class Placeholder<T> {

    public static final Placeholder<Player> PLAYER = new Placeholder<>("player", Player::getName);

    private final String id;
    private final Function<T, String> serializer;

    public Function<String, String> apply(T obj) {
        String serialized = serializer.apply(obj);
        return s -> s.replace("%" + id + "%", serialized);
    }
}
