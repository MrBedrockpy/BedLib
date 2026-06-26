package ru.mrbedrockpy.bedlib.text;

import org.bukkit.entity.Player;

public interface Placeholder {

    String getName();

    String getValue(Player player);

}
