package ru.mrbedrockpy.bedlib.ui.item;

import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.mrbedrockpy.bedlib.ui.Menu;

@Setter
public abstract class GlobalItem<M extends Menu<M>> implements Item {

    protected M menu;
    protected int x, y;

    public abstract void onClick(InventoryClickEvent event);

    @Override
    @Deprecated
    public void onClick(Menu<?> menu, InventoryClickEvent event) {
        this.onClick(event);
    }
}
