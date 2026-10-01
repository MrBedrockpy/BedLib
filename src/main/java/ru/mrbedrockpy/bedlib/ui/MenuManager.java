package ru.mrbedrockpy.bedlib.ui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import ru.mrbedrockpy.bedlib.BedLib;
import ru.mrbedrockpy.bedlib.manager.RegistryRunnableManager;
import ru.mrbedrockpy.bedlib.ui.item.UpdateItem;

import java.util.Map;

public class MenuManager extends RegistryRunnableManager<BedLib, Menu<?>> {

    private long tick = 0;

    public MenuManager(BedLib plugin) {
        super(plugin);
        this.runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void run() {
        tick++;
        items.values().forEach(menu -> {
            for (Map.Entry<Integer, SlotData> data : menu.getCachedItems().entrySet()) {
                if (!(data.getValue().item() instanceof UpdateItem item)) continue;
                if (this.tick % item.getInterval() != 0) continue;
                menu.updateItem(data.getKey(), data.getValue());
            }
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Menu<?> menu = findMenuByEvent(event);
        if (menu != null) menu.click(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Menu<?> menu = findMenuByEvent(event);
        if (menu != null) menu.close(event);
    }

    public Menu<?> findMenuByEvent(InventoryEvent event) {
        return get(event.getView().getPlayer().getName());
    }

    public void closeAll() {
        getItems().values().forEach(menu -> menu.getPlayer().closeInventory());
    }

    public BedLib getPlugin() {
        return this.plugin;
    }
}
