package ru.mrbedrockpy.bedlib.serialize;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import ru.mrbedrockpy.bedlib.BedPlugin;
import ru.mrbedrockpy.bedlib.text.Text;

public class DefaultSerializeConfig<P extends BedPlugin<P>> extends SerializeConfig<P> {

    public DefaultSerializeConfig(P plugin) {
        super(plugin);
        registerAll(
                Serializers.BYTE, Serializers.SHORT, Serializers.INTEGER, Serializers.LONG,
                Serializers.FLOAT, Serializers.DOUBLE, Serializers.CHAR, Serializers.BOOLEAN
        );
        register(new Serializer<>(NamedTextColor.class,
                NamedTextColor::toString, NamedTextColor.NAMES::value));
        register(new Serializer<>(Material.class, Material::name, Material::getMaterial));
        register(new Serializer<>(Text.class, Text::toText, Text::fromText));
    }
}
