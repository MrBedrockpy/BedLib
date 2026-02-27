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
import java.util.Map;
import java.util.function.Supplier;

@Getter
public class StructGui implements Gui {

    private Text title;
    private Structure structure;
    private final ChestSize size;
    private final Map<Character, Supplier<Item>> items;

    public StructGui(ChestSize size) {
        this.title = Text.fromText("");
        this.structure = Structure.defaultStruct(size);
        this.size = size;
        this.items = new HashMap<>();
    }

    @Override
    public Map<Integer, SlotData> render() {
        Map<Integer, SlotData> slots = new HashMap<>();
        structure.getViewWindow(0, 0).forEach(data -> {
            Supplier<Item> getter = items.get(data.symbol());
            if (getter != null) slots.put(data.slot(), data.item(getter.get()));
        });
        return slots;
    }

    public StructGui setTitle(Text title) {
        this.title = title;
        return this;
    }

    public StructGui setStructure(Structure structure) {
        this.structure = structure;
        return this;
    }

    public StructGui addIngredient(char symbol, Item item) {
        this.items.put(symbol, () -> item);
        return this;
    }

    public StructGui addIngredient(char symbol, Supplier<Item> item) {
        this.items.put(symbol, item);
        return this;
    }

    public StructGui addIngredient(char symbol, ItemBuilder itemBuilder) {
        this.items.put(symbol, () -> new SimpleItem(itemBuilder));
        return this;
    }

    public StructGui addIngredient(char symbol, Material material) {
        this.items.put(symbol, () -> new SimpleItem(material));
        return this;
    }

    public StructGui addIngredient(char symbol, Material material, int amount) {
        this.items.put(symbol, () -> new SimpleItem(material, amount));
        return this;
    }
}
