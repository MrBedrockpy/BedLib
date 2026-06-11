package ru.mrbedrockpy.bedlib.ui.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.mrbedrockpy.bedlib.ui.Menu;
import ru.mrbedrockpy.bedlib.ui.gui.PagedGui;

@Getter
@AllArgsConstructor
public abstract class PageItem implements Item {

    private final int offset;

    @Override
    public final void onClick(Menu menu, InventoryClickEvent event) {
        event.setCancelled(true);
        if (menu.getGui() instanceof PagedGui gui) gui.setPage(gui.getPage() + offset);
    }
}
