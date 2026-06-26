package ru.mrbedrockpy.bedlib.text;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderContext {

    private static final Map<String, Placeholder> placeholders = new HashMap<>();

    private static final Placeholder PLAYER = register(new InlinePlaceholder("player", Player::getName));
    private static final Placeholder WORLD = register(new InlinePlaceholder("world", p -> p.getWorld().getName()));

    public static Placeholder register(Placeholder placeholder) {
        return placeholders.put(placeholder.getName(), placeholder);
    }

    public static String applyPlaceholders(Player player, String text) {
        for (Placeholder placeholder : placeholders.values())
            text = text.replace("%" + placeholder.getName() + "%",
                    placeholder.getValue(player));
        return text;
    }
}
