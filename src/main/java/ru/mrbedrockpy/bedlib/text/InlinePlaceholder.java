package ru.mrbedrockpy.bedlib.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.function.Function;

@AllArgsConstructor
public class InlinePlaceholder implements Placeholder {
    
    @Getter private final String name;
    private final Function<Player, String> handler;

    @Override
    public String getValue(Player player) {
        return handler.apply(player);
    }
}
