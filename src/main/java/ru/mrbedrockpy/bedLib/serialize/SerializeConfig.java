package ru.mrbedrockpy.bedLib.serialize;

import ru.mrbedrockpy.bedLib.BedPlugin;
import ru.mrbedrockpy.bedLib.manager.RegistryManager;

public class SerializeConfig<P extends BedPlugin<P>> extends RegistryManager<P, Serializer<?>> {

    public SerializeConfig(P plugin) {
        super(plugin);
    }

    public <T> Serializer<T> getSerializer(Class<T> clazz) {
        return (Serializer<T>) get(clazz.getName());
    }
}
