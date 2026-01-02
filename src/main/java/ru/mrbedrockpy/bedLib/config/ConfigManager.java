package ru.mrbedrockpy.bedLib.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.mrbedrockpy.bedLib.BedPlugin;
import ru.mrbedrockpy.bedLib.config.annotation.Comment;
import ru.mrbedrockpy.bedLib.config.annotation.CommentType;
import ru.mrbedrockpy.bedLib.config.annotation.Config;
import ru.mrbedrockpy.bedLib.config.annotation.ConfigField;
import ru.mrbedrockpy.bedLib.config.data.ConfigData;
import ru.mrbedrockpy.bedLib.config.data.ConfigFieldData;
import ru.mrbedrockpy.bedLib.manager.Manager;
import ru.mrbedrockpy.bedLib.serialize.SerializeConfig;
import ru.mrbedrockpy.bedLib.serialize.Serializer;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public final class ConfigManager<P extends BedPlugin<P>> extends Manager<P> {

    private final File configFolder;
    private final SerializeConfig<P> serializeConfig;
    private final ConfigData[] configs;

    private ConfigManager(P plugin, File configFolder, SerializeConfig<P> serializeConfig, Class<?>... configs) {
        super(plugin);
        this.configFolder = configFolder;
        this.serializeConfig = serializeConfig;
        this.configs = Arrays.stream(configs).map(this::initConfig).filter(Objects::nonNull).toArray(ConfigData[]::new);
        this.loadConfigs();
    }

    private ConfigData initConfig(Class<?> clazz) {
        Config configAnnotation = clazz.getAnnotation(Config.class);
        if (configAnnotation == null) {
            getPlugin().getLogger().warning(clazz.getName() + " is not annotated with @Config");
            return null;
        }
        List<ConfigFieldData<?>> fields = new ArrayList<>();
        for (Field field: clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            ConfigField fieldAnnotation = field.getAnnotation(ConfigField.class);
            if (fieldAnnotation == null) continue;
            field.setAccessible(true);
            try {
                Object value = field.get(null);
                if (value == null) {
                    getPlugin().getLogger().warning("Field cannot be null: " + fieldAnnotation.name());
                    continue;
                }
                Comment comment = field.getAnnotation(Comment.class);
                if (comment != null) fields.add(new ConfigFieldData<>(fieldAnnotation.name(), field.getType(), field, comment.type(), Arrays.asList(comment.comment())));
                else fields.add(new ConfigFieldData<>(fieldAnnotation.name(), field.getType(), field, null, null));
            } catch (IllegalAccessException e) {
                getPlugin().getLogger().warning(fieldAnnotation.name() + "'s access error : " + e.getMessage());
            }
        }
        return new ConfigData(
                configAnnotation.name(),
                fields.toArray(new ConfigFieldData[0]),
                Arrays.stream(clazz.getDeclaredClasses())
                        .map(this::initConfig)
                        .filter(Objects::nonNull)
                        .peek(data -> {
                            Comment comment = data.getClazz().getAnnotation(Comment.class);
                            data.setComment(comment != null ? Arrays.asList(comment.comment()) : null);
                            data.setCommentType(comment != null ? comment.type() : null);
                        })
                        .toArray(ConfigData[]::new),
                clazz,
                null,
                null
        );
    }

    public void loadConfigs() {
        for (ConfigData configData : configs) {
            File configFile = new File(configFolder, configData.getName());
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);
            loadConfig(configData, configuration);
        }
    }

    private void loadConfig(ConfigData configData, ConfigurationSection configuration) {
        for (ConfigFieldData<?> fieldData : configData.getFields()) {
            if (configuration.contains(fieldData.getName())) {
                try {
                    if (isSimpledType(fieldData.getType())) {
                        fieldData.setValue(configuration.get(fieldData.getName()));
                    } else {
                        Serializer<?> serializer = serializeConfig.getSerializer(fieldData.getType());
                        if (serializer == null) throw new RuntimeException("Serializer not found: " + fieldData.getType().getName());
                        fieldData.setValue(serializer.deserialize(configuration.getString(fieldData.getName())));
                    }
                } catch (Exception e) {
                    getPlugin().getLogger().warning("Failed to set value for field " + fieldData.getName() + ": " + e.getMessage());
                }
            }
        }
        for (ConfigData category : configData.getCategories()) {
            ConfigurationSection categoryData = configuration.getConfigurationSection(category.getName());
            if (categoryData == null) continue;
            loadConfig(category, categoryData);
        }
    }

    public void saveConfigs() {
        for (ConfigData config : configs) {
            File file = new File(configFolder, config.getName());
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            saveConfig(config, configuration);
            try {
                configuration.save(file);
            } catch (Exception e) {
                getPlugin().getLogger().warning("Failed to save config " + config.getName() + ": " + e.getMessage());
            }
        }
    }

    private void saveConfig(ConfigData configData, ConfigurationSection configuration) {
        for (ConfigFieldData<?> fieldData : configData.getFields()) {
            try {
                if (isSimpledType(fieldData.getType())) configuration.set(fieldData.getName(), fieldData.getValue());
                else {
                    Serializer<?> serializer = serializeConfig.getSerializer(fieldData.getType());
                    if (serializer == null) throw new RuntimeException("Serializer not found: " + fieldData.getType().getName());
                    configuration.set(fieldData.getName(), serialize(serializer, fieldData.getValue()));
                    if (fieldData.getCommentType() != null) {
                        switch (fieldData.getCommentType()) {
                            case TOP -> configuration.setComments(fieldData.getName(), fieldData.getComment());
                            case INLINE -> configuration.setInlineComments(fieldData.getName(), fieldData.getComment());
                        }
                    }
                }
            } catch (Exception e) {
                getPlugin().getLogger().warning("Failed to get value for field " + fieldData.getName() + ": " + e.getMessage());
            }
        }
        for (ConfigData category : configData.getCategories()) {
            ConfigurationSection categoryData = configuration.createSection(category.getName());
            saveConfig(category, categoryData);
            configuration.set(category.getName(), categoryData);
            if (category.getCommentType() != null) {
                switch (category.getCommentType()) {
                    case TOP -> configuration.setComments(categoryData.getName(), category.getComment());
                    case INLINE -> configuration.setInlineComments(category.getName(), category.getComment());
                }
            }
        }
    }

    private boolean isSimpledType(Class<?> type) {
        return List.of(
                Integer.class, Byte.class, Short.class, Long.class, Float.class, Double.class, Character.class, String.class, Boolean.class,
                int.class, byte.class, short.class, long.class, float.class, double.class, char.class, boolean.class,
                List.class, Set.class, Map.class
        ).contains(type);
    }

    private <F> String serialize(Serializer<F> serializer, Object value) {
        return serializer.serialize((F) value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private File pluginFolder;
        private Class<?>[] configs;

        public Builder setPluginFolder(File pluginFolder) {
            this.pluginFolder = pluginFolder;
            return this;
        }

        public Builder setConfigs(Class<?>... configs) {
            this.configs = configs;
            return this;
        }

        public <P extends BedPlugin<P>> ConfigManager<P> build(P plugin, SerializeConfig<P> serializeConfig) {
            return new ConfigManager<>(plugin, pluginFolder, serializeConfig, configs);
        }
    }
}
