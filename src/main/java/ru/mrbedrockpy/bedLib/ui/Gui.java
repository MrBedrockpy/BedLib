package ru.mrbedrockpy.bedLib.ui;

import lombok.Getter;
import org.bukkit.Material;
import ru.mrbedrockpy.bedLib.text.Text;
import ru.mrbedrockpy.bedLib.ui.item.Item;
import ru.mrbedrockpy.bedLib.ui.item.SimpleItem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Gui {

    protected final Map<Integer, Item> staticItems = new HashMap<>();
    protected final Map<Character, Item> dynamicItems = new HashMap<>();

    @Getter protected final int inventorySize;

    @Getter protected Text title = null;
    @Getter protected Structure structure = null;

    protected Gui(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public Map<Integer, Item> getGui(int x, int y) {
        Map<Integer, Item> items = new HashMap<>();
        if (structure != null) {
            structure.getIngredients(x, y).forEach((key, value) -> items.put(
                    key, dynamicItems.getOrDefault(value, new SimpleItem(Material.AIR))
            ));
        }
        items.putAll(staticItems);
        return items;
    }

    public static Gui createGui(int inventorySize) {
        return new Gui(inventorySize);
    }

    public Gui setTitle(Text title) {
        this.title = title;
        return this;
    }

    public Gui setStructure(Structure structure) {
        this.structure = structure;
        return this;
    }

    public Gui addStaticItem(int slot, Item item) {
        this.staticItems.put(slot, item);
        return this;
    }

    public Gui addDynamicItem(char symbol, Item item) {
        this.dynamicItems.put(symbol, item);
        return this;
    }

    public Gui addStaticItem(int slot, Supplier<Item> item) {
        return this.addStaticItem(slot, item.get());
    }

    public Gui addDynamicItem(char symbol, Supplier<Item> item) {
        return this.addDynamicItem(symbol, item.get());
    }

    public Gui addStaticItem(int slot, ItemBuilder item) {
        return this.addStaticItem(slot, new SimpleItem(item.get()));
    }

    public Gui addDynamicItem(char symbol, ItemBuilder item) {
        return this.addDynamicItem(symbol, new SimpleItem(item.get()));
    }

    public Gui addStaticItem(int slot, Material material) {
        return this.addStaticItem(slot, new SimpleItem(material));
    }

    public Gui addDynamicItem(char symbol, Material material) {
        return this.addDynamicItem(symbol, new SimpleItem(material));
    }

    public Gui addStaticItem(int slot, Material material, int amount) {
        return this.addStaticItem(slot, new SimpleItem(material, amount));
    }

    public Gui addDynamicItem(char symbol, Material material, int amount) {
        return this.addDynamicItem(symbol, new SimpleItem(material, amount));
    }
}
