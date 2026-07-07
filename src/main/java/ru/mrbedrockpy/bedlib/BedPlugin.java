package ru.mrbedrockpy.bedlib;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbedrockpy.bedlib.autoregister.AutoRegisterManager;
import ru.mrbedrockpy.bedlib.command.CommandManager;
import ru.mrbedrockpy.bedlib.config.ConfigManager;
import ru.mrbedrockpy.bedlib.serialize.SerializeConfig;
import ru.mrbedrockpy.bedlib.text.PlaceholderManager;

@Getter
@RequiredArgsConstructor
public abstract class BedPlugin<P extends BedPlugin<P>> extends JavaPlugin {

    private CommandManager<P> commandManager;
    private PlaceholderManager<P> placeholderManager;
    protected SerializeConfig<P> serializeConfig;
    protected ConfigManager<P> configManager;

    @Override
    public final void onEnable() {
        this.commandManager = new CommandManager<>((P) this);
        this.placeholderManager = new PlaceholderManager<>((P) this);
        this.importLibraries();
        this.initConfigs();
        this.initManagers();
        AutoRegisterManager.register((P) this);
        this.commandManager.registerCommands();
        this.placeholderManager.registerPlaceholders();
    }

    @Override
    public final void onDisable() {
        if (this.commandManager != null) this.commandManager.unregisterCommands();
        this.saveManagers();
        this.saveConfigs();
    }

    protected void importLibraries() {}
    protected void initConfigs() {}
    protected void initManagers() {}

    protected void saveManagers() {}
    protected void saveConfigs() {}
}
