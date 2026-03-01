package ru.mrbedrockpy.bedLib.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.mrbedrockpy.bedLib.util.LoaderUtil;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DtoLoader<I extends Dto> {

    private final Registry<I> registry;
    private final File configFile;
    private final BiFunction<FileConfiguration, String, I> loader;
    private final Consumer<File> defaultConfigSaver;
    private final BiConsumer<FileConfiguration, I> saver;

    public DtoLoader(Registry<I> registry, File configFile, BiFunction<FileConfiguration, String, I> loader) {
        this(registry, configFile, loader, config -> {}, (config, item) -> {});
    }

    public DtoLoader(Registry<I> registry, File configFile, BiFunction<FileConfiguration, String, I> loader, Consumer<FileConfiguration> defaultConfigSaver) {
        this(registry, configFile, loader, defaultConfigSaver, (config, item) -> {});
    }

    public DtoLoader(Registry<I> registry, File configFile, BiFunction<FileConfiguration, String, I> loader, BiConsumer<FileConfiguration, I> saver) {
        this(registry, configFile, loader, config -> {}, saver);
    }

    public DtoLoader(Registry<I> registry, File configFile, BiFunction<FileConfiguration, String, I> loader, Consumer<FileConfiguration> defaultConfigSaver, BiConsumer<FileConfiguration, I> saver) {
        this.registry = registry;
        this.configFile = configFile;
        this.loader = loader;
        this.defaultConfigSaver = (file) -> defaultConfigSaver.accept(YamlConfiguration.loadConfiguration(file));
        this.saver = saver;
    }

    public void load() {
        LoaderUtil.loadManagerItems(this.configFile, this.registry, this.loader, this.defaultConfigSaver);
    }

    public void save() {
        LoaderUtil.saveConfig(this.configFile, config -> this.registry.getElements().forEach(el -> this.saver.accept(config, el)));
    }
}
