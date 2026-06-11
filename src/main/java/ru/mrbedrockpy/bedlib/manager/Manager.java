package ru.mrbedrockpy.bedlib.manager;

import org.bukkit.event.Listener;
import ru.mrbedrockpy.bedlib.BedPlugin;

public abstract class Manager<P extends BedPlugin<P>> implements Listener {

    protected final P plugin;

    public Manager(P plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
