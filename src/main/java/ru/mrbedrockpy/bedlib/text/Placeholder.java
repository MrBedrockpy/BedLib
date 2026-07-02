package ru.mrbedrockpy.bedlib.text;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mrbedrockpy.bedlib.BedPlugin;

@AllArgsConstructor
public abstract class Placeholder<P extends BedPlugin<P>> extends PlaceholderExpansion {

    private final P plugin;

    @Override
    public @NotNull String getIdentifier() {
        return name();
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(" ", plugin.getDescription().getAuthors());
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return hook(player, params);
    }

    public abstract String name();
    public abstract String hook(Player player, String params);
}
