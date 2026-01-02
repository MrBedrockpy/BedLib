package ru.mrbedrockpy.bedLib;

import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbedrockpy.bedLib.command.CommandManager;
import ru.mrbedrockpy.bedLib.config.ConfigManager;
import ru.mrbedrockpy.bedLib.serialize.SerializeConfig;

public abstract class BedPlugin<P extends BedPlugin<P>> extends JavaPlugin {

    private CommandManager<P> commandManager;
    protected SerializeConfig<P> serializeConfig;
    protected ConfigManager<P> configManager;

    @Override
    public final void onEnable() {
        registerConfigs();
        registerManagers();
        this.commandManager = new CommandManager<>((P) this);
        this.commandManager.registerCommands();
    }

    @Override
    public final void onDisable() {
        if (this.commandManager != null) this.commandManager.unregisterCommands();
        saveManagers();
        saveConfigs();
    }

    protected void registerConfigs() {}
    protected void registerManagers() {}

    protected void saveManagers() {}
    protected void saveConfigs() {}
}
