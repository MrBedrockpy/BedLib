package ru.mrbedrockpy.bedLib.serialize;

import ru.mrbedrockpy.bedLib.BedPlugin;
import ru.mrbedrockpy.bedLib.text.Text;

public class DefaultSerializeConfig<P extends BedPlugin<P>> extends SerializeConfig<P> {

    public DefaultSerializeConfig(P plugin) {
        super(plugin);
        register(new Serializer<>(Text.class, Text::toText, Text::fromText));
    }
}
