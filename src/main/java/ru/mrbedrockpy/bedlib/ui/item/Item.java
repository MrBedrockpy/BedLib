package ru.mrbedrockpy.bedlib.ui.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.mrbedrockpy.bedlib.ui.ItemBuilder;
import ru.mrbedrockpy.bedlib.ui.Menu;

public interface Item {

    ItemBuilder getProvider(Player player);

    void onClick(Menu<?> menu, InventoryClickEvent event);

}
