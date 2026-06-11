package ru.mrbedrockpy.bedlib.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.key.InvalidKeyException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.intellij.lang.annotations.Subst;
import ru.mrbedrockpy.bedlib.manager.Dto;
import ru.mrbedrockpy.bedlib.manager.Registry;
import ru.mrbedrockpy.bedlib.text.Text;
import ru.mrbedrockpy.bedlib.ui.ItemBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@UtilityClass
public class LoaderUtil {

    public <T extends Dto> void loadManagerItems(File file, Registry<T> registry, BiFunction<FileConfiguration, String, T> loader) {
        loadManagerItems(file, registry::clear, registry::register, loader, f -> {});
    }

    public <T extends Dto> void loadManagerItems(File file, Registry<T> registry, BiFunction<FileConfiguration, String, T> loader, Consumer<File> saveIfNotExist) {
        loadManagerItems(file, registry::clear, registry::register, loader, saveIfNotExist);
    }

    public <T> void loadManagerItems(File file, Runnable clear, Consumer<T> register, BiFunction<FileConfiguration, String, T> loader, Consumer<File> saveIfNotExist) {
        if (!file.exists()) saveIfNotExist.accept(file);
        clear.run();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.getKeys(false).stream()
                .map(s -> loader.apply(config, s)).filter(Objects::nonNull).forEach(register);
    }

    public void saveConfig(File file, Consumer<FileConfiguration> consumer) {
        if (!file.getParentFile().exists()) if (!file.getParentFile().mkdirs()) throw new RuntimeException("Failed to create directory " + file.getParentFile());
        try {
            if (!file.exists()) if (!file.createNewFile()) throw new RuntimeException("Failed to create file " + file);
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            consumer.accept(config);
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ItemBuilder loadItemBuilder(ConfigurationSection section) {
        Material material = Material.getMaterial(section.getString("material", "STONE"));
        if (material == null) return null;
        Text displayName = Text.fromText(section.getString("display-name", "Default"));
        List<Text> description = section.getStringList("description").stream().map(Text::fromText).toList();
        int amount = section.getInt("amount", 1);
        List<Enchant> enchants = section.getStringList("enchants").stream()
                .map(LoaderUtil::enchantFromString).filter(Objects::nonNull).toList();
        ItemBuilder builder = new ItemBuilder(material, amount).setDisplayName(displayName)
                .setLore(description);
        enchants.forEach(builder::addEnchant);
        return builder;
    }

    public void saveItemBuilder(ConfigurationSection section, ItemBuilder builder) {
        section.set("material", builder.getMaterial().name());
        section.set("display-name", builder.getDisplayName().toText());
        section.set("lore", builder.getLore().stream().map(Text::toText).toList());
        section.set("amount", builder.getAmount());
        section.set("enchants", builder.getEnchants().stream().map(LoaderUtil::enchantToString).toList());
    }

    public Enchant enchantFromString(String enchant) {
        @Subst("") String[] split = enchant.split(":");
        if (split.length != 2) return null;
        try {
            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(split[0]));
            int level = Integer.parseInt(split[1]);
            return new Enchant(enchantment, level);
        } catch (InvalidKeyException | NumberFormatException e) {
            throw new RuntimeException("Invalid enchantment: " + enchant);
        }
    }

    public String enchantToString(Enchant enchant) {
        return enchant.getEnchantment().getKey().getKey() + ":" + enchant.getLevel();
    }
}
