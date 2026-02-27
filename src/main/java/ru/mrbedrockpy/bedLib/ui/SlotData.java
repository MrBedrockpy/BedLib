package ru.mrbedrockpy.bedLib.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.mrbedrockpy.bedLib.ui.item.Item;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class SlotData {

    private int structX;
    private int structY;
    private int slot;
    private char symbol;
    private Item item;

    public SlotData structX(int x) {
        this.structX = x;
        return this;
    }

    public SlotData structY(int y) {
        this.structY = y;
        return this;
    }

    public SlotData slot(int slot) {
        this.slot = slot;
        return this;
    }

    public SlotData symbol(char symbol) {
        this.symbol = symbol;
        return this;
    }

    public SlotData item(Item item) {
        this.item = item;
        return this;
    }
}
