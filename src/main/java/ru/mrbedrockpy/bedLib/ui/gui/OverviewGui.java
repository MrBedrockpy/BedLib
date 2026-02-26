package ru.mrbedrockpy.bedLib.ui.gui;

import lombok.Getter;
import org.bukkit.Material;
import ru.mrbedrockpy.bedLib.text.Text;
import ru.mrbedrockpy.bedLib.ui.ChestSize;
import ru.mrbedrockpy.bedLib.ui.ItemBuilder;
import ru.mrbedrockpy.bedLib.ui.SlotData;
import ru.mrbedrockpy.bedLib.ui.Structure;
import ru.mrbedrockpy.bedLib.ui.item.Item;
import ru.mrbedrockpy.bedLib.ui.item.SimpleItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class OverviewGui implements Gui {

    @Getter private Text title;
    @Getter private Structure structure;
    @Getter private final ChestSize size;
    @Getter private int x = 0, y = 0;
    private final Map<Integer, Item> staticItems;
    private final Map<Character, Supplier<Item>> dynamicItems;

    public OverviewGui(ChestSize size) {
        this.title = Text.fromText("");
        this.structure = Structure.defaultStruct(size);
        this.size = size;
        this.dynamicItems = new HashMap<>();
        this.staticItems = new HashMap<>();
    }

    @Override
    public Map<Integer, SlotData> render() {
        Map<Integer, SlotData> slots = new HashMap<>();
        this.structure.getViewWindow(x, y).forEach(data -> {
            Supplier<Item> getter = this.dynamicItems.get(data.symbol());
            if (getter != null) slots.put(data.slot(), data.item(getter.get()));
        });
        this.staticItems.forEach((slot, item) -> slots.put(slot, new SlotData().slot(slot).item(item)));
        return slots;
    }

    public OverviewGui setTitle(Text title) {
        this.title = title;
        return this;
    }

    public OverviewGui setStructure(Structure structure) {
        this.structure = structure;
        return this;
    }

    public OverviewGui setStaticItem(int slot, Item item) {
        this.staticItems.put(slot, item);
        return this;
    }

    public boolean canOffset(int x, int y) {
        x += this.x;
        y += this.y;
        if (x < 0 || y < 0) return false;
        if (structure.getWidth() < x + 9) return false;
        return structure.getHeight() >= y + size.getRows();
    }

    public OverviewGui addOffset(int x, int y) {
        if (canOffset(x, y)) {
            this.x += x;
            this.y += y;
        }
        return this;
    }

    public OverviewGui setStaticItem(int slot, Supplier<Item> itemSupplier) {
        this.staticItems.put(slot, itemSupplier.get());
        return this;
    }

    public OverviewGui setStaticItem(int slot, ItemBuilder itemBuilder) {
        this.staticItems.put(slot, new SimpleItem(itemBuilder));
        return this;
    }

    public OverviewGui setStaticItem(int slot, Material material) {
        this.staticItems.put(slot, new SimpleItem(material));
        return this;
    }

    public OverviewGui setStaticItem(int slot, Material material, int amount) {
        this.staticItems.put(slot, new SimpleItem(material, amount));
        return this;
    }

    public OverviewGui setStaticItem(List<Integer> slots, Item item) {
        slots.forEach(slot -> this.staticItems.put(slot, item));
        return this;
    }

    public OverviewGui setStaticItem(List<Integer> slots, Supplier<Item> itemSupplier) {
        slots.forEach(slot -> this.staticItems.put(slot, itemSupplier.get()));
        return this;
    }

    public OverviewGui setStaticItem(List<Integer> slots, ItemBuilder itemBuilder) {
        slots.forEach(slot -> this.staticItems.put(slot, new SimpleItem(itemBuilder)));
        return this;
    }

    public OverviewGui setStaticItem(List<Integer> slots, Material material) {
        slots.forEach(slot -> this.staticItems.put(slot, new SimpleItem(material)));
        return this;
    }

    public OverviewGui setStaticItem(List<Integer> slots, Material material, int amount) {
        slots.forEach(slot -> this.staticItems.put(slot, new SimpleItem(material, amount)));
        return this;
    }

    public OverviewGui setDynamicItem(char symbol, Item item) {
        this.dynamicItems.put(symbol, () -> item);
        return this;
    }

    public OverviewGui setDynamicItem(char symbol, Supplier<Item> item) {
        this.dynamicItems.put(symbol, item);
        return this;
    }

    public OverviewGui setDynamicItem(char symbol, ItemBuilder itemBuilder) {
        this.dynamicItems.put(symbol, () -> new SimpleItem(itemBuilder));
        return this;
    }

    public OverviewGui setDynamicItem(char symbol, Material material) {
        this.dynamicItems.put(symbol, () -> new SimpleItem(material));
        return this;
    }

    public OverviewGui setDynamicItem(char symbol, Material material, int amount) {
        this.dynamicItems.put(symbol, () -> new SimpleItem(material, amount));
        return this;
    }
}
