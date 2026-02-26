package ru.mrbedrockpy.bedLib.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import ru.mrbedrockpy.bedLib.serialize.Serializer;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Text {

    private final TextComponent component;

    private Text(TextComponent component) {
        this.component = component;
    }

    public static Text fromAdventure(TextComponent component) {
        return new Text(component);
    }
    public static Text fromAdventure(Component component) {
        return Text.fromAdventure((TextComponent) component);
    }
    public static Text fromText(String text) {
        return fromText(text, TextFormat.MINI_MESSAGE);
    }
    public static Text fromText(String text, TextFormat format) {
        return new Text(format.deserialize(text));
    }

    public TextComponent toAdventure() {
        return Component.textOfChildren(component);
    }
    public String toText() {
        return this.toText(TextFormat.MINI_MESSAGE);
    }
    public String toText(TextFormat format) {
        return format.serialize(component);
    }
    public String toVanilla() {
        return ChatColor.translateAlternateColorCodes('&', this.toText(TextFormat.LEGACY_AMPERSAND));
    }

    @SafeVarargs
    public final Text applyPlaceholders(Function<String, String>... placeholders) {
        AtomicReference<String> text = new AtomicReference<>(this.toText());
        Arrays.stream(placeholders).forEach(p -> text.set(p.apply(text.get())));
        return fromText(text.get());
    }

    public enum TextFormat {

        LEGACY_SECTION(
                LegacyComponentSerializer.legacySection()::serialize,
                LegacyComponentSerializer.legacyAmpersand()::deserialize
        ),
        LEGACY_AMPERSAND(
                LegacyComponentSerializer.legacyAmpersand()::serialize,
                LegacyComponentSerializer.legacyAmpersand()::deserialize
        ),
        MINI_MESSAGE(
                MiniMessage.miniMessage()::serialize,
                (text) -> (TextComponent) MiniMessage.miniMessage().deserialize(text)
        );

        private final Serializer<TextComponent> serializer;

        TextFormat(Function<TextComponent, String> serializer, Function<String, TextComponent> deserializer) {
            this.serializer = new Serializer<>(TextComponent.class, serializer, deserializer);
        }

        public String serialize(TextComponent text) {
            return serializer.serialize(text);
        }
        public TextComponent deserialize(String text) {
            return serializer.deserialize(text);
        }
    }
}
