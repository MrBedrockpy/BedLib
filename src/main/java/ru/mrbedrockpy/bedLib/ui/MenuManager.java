package ru.mrbedrockpy.bedLib.ui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.mrbedrockpy.bedLib.BedLib;
import ru.mrbedrockpy.bedLib.manager.RegistryManager;

public class MenuManager extends RegistryManager<BedLib, Menu> {

    public static MenuManager INSTANCE;

    public MenuManager(BedLib plugin) {
        super(plugin);
        INSTANCE = this;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Menu menu = findMenuByEvent(event);
        if (menu != null) menu.click(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Menu menu = findMenuByEvent(event);
        if (menu != null) menu.close(event);
    }

    public Menu findMenuByEvent(InventoryEvent event) {
        Inventory inventory = event.getInventory();
        for (ItemStack stack : inventory.getContents()) {
            if (stack == null) continue;
            ItemMeta meta = stack.getItemMeta();
            if (meta == null) continue;
            String id = meta.getPersistentDataContainer()
                    .get(Menu.MENU_ID, PersistentDataType.STRING);
            if (id == null) continue;
            Menu menu = this.get(id);
            if (menu != null) return menu;
        }
        return null;
    }

    public void closeAll() {
        getItems().values().forEach(menu -> menu.getPlayer().closeInventory());
    }

    public BedLib getPlugin() {
        return this.plugin;
    }
}
