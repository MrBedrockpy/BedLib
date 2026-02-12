package ru.mrbedrockpy.bedLib.ui.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.mrbedrockpy.bedLib.ui.Menu;

@Getter
@AllArgsConstructor
public abstract class ScrollItem implements Item {

    private final int x;
    private final int y;

    @Override
    public void onClick(Menu menu, InventoryClickEvent event) {
        menu.setX(menu.getX() + x);
        menu.setY(menu.getY() + y);
        event.setCancelled(true);
    }
}
