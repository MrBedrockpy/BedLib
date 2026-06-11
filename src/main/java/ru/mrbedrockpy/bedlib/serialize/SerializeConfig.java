package ru.mrbedrockpy.bedlib.serialize;

import ru.mrbedrockpy.bedlib.BedPlugin;
import ru.mrbedrockpy.bedlib.manager.RegistryManager;

public class SerializeConfig<P extends BedPlugin<P>> extends RegistryManager<P, Serializer<?>> {

    public SerializeConfig(P plugin) {
        super(plugin);
    }

    public <T> Serializer<T> getSerializer(Class<T> clazz) {
        return (Serializer<T>) get(clazz.getName());
    }
}
