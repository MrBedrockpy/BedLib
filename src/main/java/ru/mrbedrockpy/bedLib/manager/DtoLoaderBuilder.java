package ru.mrbedrockpy.bedLib.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DtoLoaderBuilder<I extends Dto> {

    private final File configFile;
    private final BiFunction<FileConfiguration, String, I> loader;
    private final Consumer<FileConfiguration> defaultConfigSaver;
    private final BiConsumer<FileConfiguration, I> saver;

    public DtoLoaderBuilder(File configFile, BiFunction<FileConfiguration, String, I> loader) {
        this(configFile, loader, config -> {}, (config, item) -> {});
    }

    public DtoLoaderBuilder(File configFile, BiFunction<FileConfiguration, String, I> loader, Consumer<FileConfiguration> defaultConfigSaver) {
        this(configFile, loader, defaultConfigSaver, (config, item) -> {});
    }

    public DtoLoaderBuilder(File configFile, BiFunction<FileConfiguration, String, I> loader, BiConsumer<FileConfiguration, I> saver) {
        this(configFile, loader, config -> {}, saver);
    }

    public DtoLoaderBuilder(File configFile, BiFunction<FileConfiguration, String, I> loader, Consumer<FileConfiguration> defaultConfigSaver, BiConsumer<FileConfiguration, I> saver) {
        this.configFile = configFile;
        this.loader = loader;
        this.defaultConfigSaver = defaultConfigSaver;
        this.saver = saver;
    }

    public DtoLoader<I> build(Registry<I> registry) {
        return new DtoLoader<I>(registry, this.configFile, this.loader, this.defaultConfigSaver, this.saver);
    }
}
