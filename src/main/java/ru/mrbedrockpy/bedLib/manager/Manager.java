package ru.mrbedrockpy.bedLib.manager;

import lombok.Getter;
import org.bukkit.event.Listener;
import ru.mrbedrockpy.bedLib.BedPlugin;

@Getter
public abstract class Manager<P extends BedPlugin<P>> implements Listener {

    private final P plugin;

    public Manager(P plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
