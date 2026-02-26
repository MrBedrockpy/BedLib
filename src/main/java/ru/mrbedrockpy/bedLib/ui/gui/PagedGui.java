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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Getter
public class PagedGui implements Gui {

    private final ChestSize size;
    private final Map<Character, Supplier<Item>> items;

    private Text title;
    private Structure structure;
    private char contentChar = '\'';
    private List<Item> contents= new ArrayList<>();
    private int page = 1;

    public PagedGui(ChestSize size) {
        this.title = Text.fromText("");
        this.structure = Structure.defaultStruct(size);
        this.size = size;
        this.items = new HashMap<>();
    }

    @Override
    public Map<Integer, SlotData> render() {
        Map<Integer, SlotData> slots = new HashMap<>();
        List<SlotData> structureMap = structure.getViewWindow(0, 0);
        List<Integer> contentSlots = structureMap.stream()
                .filter(data -> data.symbol() == contentChar)
                .map(SlotData::slot)
                .sorted()
                .toList();
        int itemsPerPage = contentSlots.size();
        int startIndex = (page - 1) * itemsPerPage;
        int contentIndex = 0;
        for (SlotData data : structureMap) {
            if (data.symbol() == contentChar) {
                int index = startIndex + contentIndex;
                if (index < Math.min(startIndex + itemsPerPage, contents.size())) slots.put(data.slot(), data.item(contents.get(index)));
                contentIndex++;
                continue;
            }
            Supplier<Item> getter = items.get(data.symbol());
            if (getter != null) slots.put(data.slot(), data.item(getter.get()));
        }
        return slots;
    }

    public PagedGui setTitle(Text title) {
        this.title = title;
        return this;
    }

    public PagedGui setStructure(Structure structure) {
        this.structure = structure;
        return this;
    }

    public PagedGui setContentChar(char contentChar) {
        this.contentChar = contentChar;
        return this;
    }

    public PagedGui setContents(List<Item> contents) {
        this.contents = contents;
        return this;
    }

    public boolean setPage(int page) {
        if (page > getMaxPage() || page < 1) return false;
        this.page = page;
        return true;
    }

    public PagedGui addIngredient(char symbol, Item item) {
        this.items.put(symbol, () -> item);
        return this;
    }

    public PagedGui addIngredient(char symbol, Supplier<Item> item) {
        this.items.put(symbol, item);
        return this;
    }

    public PagedGui addIngredient(char symbol, ItemBuilder itemBuilder) {
        this.items.put(symbol, () -> new SimpleItem(itemBuilder));
        return this;
    }

    public PagedGui addIngredient(char symbol, Material material) {
        this.items.put(symbol, () -> new SimpleItem(material));
        return this;
    }

    public PagedGui addIngredient(char symbol, Material material, int amount) {
        this.items.put(symbol, () -> new SimpleItem(material, amount));
        return this;
    }

    private int getMaxPage() {
        int contentSlots = (int) structure.getViewWindow(0,0).stream()
                .filter(data -> data.symbol() == contentChar).count();
        return (int) Math.ceil((double) contents.size() / contentSlots);
    }
}
