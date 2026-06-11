package ru.mrbedrockpy.bedlib.manager;

import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import ru.mrbedrockpy.bedlib.BedPlugin;

public abstract class RunnableManager<P extends BedPlugin<P>> extends BukkitRunnable implements Listener {

    protected final P plugin;

    public RunnableManager(P plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}
