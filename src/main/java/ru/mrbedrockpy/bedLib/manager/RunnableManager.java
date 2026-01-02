package ru.mrbedrockpy.bedLib.manager;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import ru.mrbedrockpy.bedLib.BedPlugin;

@Getter
public abstract class RunnableManager<P extends BedPlugin<P>> extends BukkitRunnable implements Listener {

    private final P plugin;

    public RunnableManager(P plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}
