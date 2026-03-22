package ru.mrbedrockpy.bedLib.ui;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.mrbedrockpy.bedLib.text.Text;
import ru.mrbedrockpy.bedLib.util.Enchant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
public class ItemBuilder implements Supplier<ItemStack> {

    private final Material material;
    private final ItemMeta meta;
    private int amount;

    private ItemBuilder(ItemBuilder builder) {
        this.material = builder.material;
        this.meta = builder.meta.clone();
        this.amount = builder.amount;
    }

    public ItemBuilder(ItemStack item) {
        this.material = item.getType();
        this.amount = item.getAmount();
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public Text getDisplayName() {
        return Text.fromAdventure(this.meta.displayName());
    }

    public ItemBuilder setDisplayName(Text displayName) {
        this.meta.displayName(displayName.toAdventure());
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        return this.setDisplayName(Text.fromText(displayName));
    }

    public ItemBuilder setDisplayName(String displayName, Text.TextFormat format) {
        return this.setDisplayName(Text.fromText(displayName, format));
    }

    public List<Text> getLore() {
        List<Component> lore = this.meta.lore();
        if (lore == null) return new ArrayList<>();
        return lore.stream().map(Text::fromAdventure).collect(Collectors.toList());
    }

    public ItemBuilder setLore(List<Text> lore) {
        this.meta.lore(lore.stream().map(Text::toAdventure).toList());
        return this;
    }

    public ItemBuilder setLore(List<String> lore, Text.TextFormat format) {
        return this.setLore(lore.stream().map(text -> Text.fromText(text, format)).toList());
    }

    public ItemBuilder setLore(Text... lore) {
        return this.setLore(Arrays.asList(lore));
    }

    public ItemBuilder setLore(String... lore) {
        return this.setLore(Arrays.stream(lore).map(Text::fromText).toList());
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public boolean isUnbreakable() {
        return this.meta.isUnbreakable();
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }

    public int getCustomModelData() {
        return this.meta.getCustomModelData();
    }

    public int setCustomModelData(int data) {
        this.meta.setCustomModelData(data);
    }

    public int getDurability() {
        return ((Damageable) this.meta).getDamage();
    }

    public ItemBuilder setDurability(int durability) {
        ((Damageable) this.meta).setDamage(durability);
        return this;
    }

    public List<Enchant> getEnchants() {
        return this.meta.getEnchants().entrySet().stream().map(Enchant::new).toList();
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder addEnchant(Enchant enchant) {
        return this.addEnchant(enchant.getEnchantment(), enchant.getLevel());
    }

    public ItemBuilder removeEnchant(Enchant enchant) {
        return this.removeEnchant(enchant.getEnchantment());
    }

    public <T> ItemBuilder setPersist(NamespacedKey key, PersistentDataType<?, T> type, T value) {
        this.meta.getPersistentDataContainer().set(key, type, value);
        return this;
    }

    public <T> T getPersist(NamespacedKey key, PersistentDataType<?, T> type) {
        return this.meta.getPersistentDataContainer().get(key, type);
    }

    public <T> T getPersistOrDefault(NamespacedKey key, PersistentDataType<?, T> type, T value) {
        return this.meta.getPersistentDataContainer().getOrDefault(key, type, value);
    }

    public <T> boolean hasPersist(NamespacedKey key) {
        return this.meta.getPersistentDataContainer().has(key);
    }

    public <T> boolean hasPersist(NamespacedKey key, PersistentDataType<?, T> type) {
        return this.meta.getPersistentDataContainer().has(key, type);
    }

    public ItemBuilder addAttrib(Attribute attribute, AttributeModifier modifier) {
        this.meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder removeAttrib(Attribute attribute) {
        this.meta.removeAttributeModifier(attribute);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        return this;
    }

    @Override
    public ItemStack get() {
        ItemStack stack = new ItemStack(this.material, this.amount);
        stack.setItemMeta(this.meta);
        return stack;
    }

    public ItemBuilder copy() {
        return new ItemBuilder(this);
    }
}
