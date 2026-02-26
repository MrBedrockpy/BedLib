package ru.mrbedrockpy.bedLib.ui.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.mrbedrockpy.bedLib.ui.ItemBuilder;
import ru.mrbedrockpy.bedLib.ui.Menu;

public interface Item {

    ItemBuilder getProvider(Player player);

    void onClick(Menu<?> menu, InventoryClickEvent event);

}
