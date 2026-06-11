package ru.mrbedrockpy.bedlib.ui.gui;

import lombok.Getter;
import org.bukkit.Material;
import ru.mrbedrockpy.bedlib.text.Text;
import ru.mrbedrockpy.bedlib.ui.ChestSize;
import ru.mrbedrockpy.bedlib.ui.ItemBuilder;
import ru.mrbedrockpy.bedlib.ui.SlotData;
import ru.mrbedrockpy.bedlib.ui.item.Item;
import ru.mrbedrockpy.bedlib.ui.item.SimpleItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Getter
public class SlotGui implements Gui {

    private Text title;
    private final ChestSize size;
    private final Map<Integer, Item> items;

    public SlotGui(ChestSize size) {
        this.title = Text.fromText("");
        this.size = size;
        this.items = new HashMap<>();
    }

    @Override
    public Map<Integer, SlotData> render() {
        Map<Integer, SlotData> slots = new HashMap<>();
        this.items.forEach((key, value) -> slots.put(key, new SlotData().slot(key).item(value)));
        return slots;
    }

    public SlotGui setTitle(Text title) {
        this.title = title;
        return this;
    }

    public SlotGui setItem(int slot, Item item) {
        this.items.put(slot, item);
        return this;
    }

    public SlotGui setItem(int slot, Supplier<Item> itemSupplier) {
        this.items.put(slot, itemSupplier.get());
        return this;
    }

    public SlotGui setItem(int slot, ItemBuilder itemBuilder) {
        this.items.put(slot, new SimpleItem(itemBuilder));
        return this;
    }

    public SlotGui setItem(int slot, Material material) {
        this.items.put(slot, new SimpleItem(material));
        return this;
    }

    public SlotGui setItem(int slot, Material material, int amount) {
        this.items.put(slot, new SimpleItem(material, amount));
        return this;
    }

    public SlotGui setItem(List<Integer> slots, Item item) {
        slots.forEach(slot -> this.items.put(slot, item));
        return this;
    }

    public SlotGui setItem(List<Integer> slots, Supplier<Item> itemSupplier) {
        slots.forEach(slot -> this.items.put(slot, itemSupplier.get()));
        return this;
    }

    public SlotGui setItem(List<Integer> slots, ItemBuilder itemBuilder) {
        slots.forEach(slot -> this.items.put(slot, new SimpleItem(itemBuilder)));
        return this;
    }

    public SlotGui setItem(List<Integer> slots, Material material) {
        slots.forEach(slot -> this.items.put(slot, new SimpleItem(material)));
        return this;
    }

    public SlotGui setItem(List<Integer> slots, Material material, int amount) {
        slots.forEach(slot -> this.items.put(slot, new SimpleItem(material, amount)));
        return this;
    }
}
