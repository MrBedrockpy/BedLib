package ru.mrbedrockpy.bedLib.serialize;

import ru.mrbedrockpy.bedLib.BedPlugin;
import ru.mrbedrockpy.bedLib.text.Text;

public class DefaultSerializeConfig<P extends BedPlugin<P>> extends SerializeConfig<P> {

    public DefaultSerializeConfig(P plugin) {
        super(plugin);
        registerAll(
                Serializers.BYTE, Serializers.SHORT, Serializers.INTEGER, Serializers.LONG,
                Serializers.FLOAT, Serializers.DOUBLE, Serializers.CHAR, Serializers.BOOLEAN
        );
        register(new Serializer<>(Text.class, Text::toText, Text::fromText));
    }
}
