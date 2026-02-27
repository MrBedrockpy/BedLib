package ru.mrbedrockpy.bedLib.ui.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.mrbedrockpy.bedLib.ui.ItemBuilder;
import ru.mrbedrockpy.bedLib.ui.Menu;

public class SimpleItem implements Item {

    private final ItemBuilder builder;

    public SimpleItem(ItemStack stack) {
        this.builder = new ItemBuilder(stack);
    }
    public SimpleItem(ItemBuilder builder) {
        this.builder = builder;
    }
    public SimpleItem(Material material) {
        this.builder = new ItemBuilder(material);
    }
    public SimpleItem(Material material, int amount) {
        this.builder = new ItemBuilder(material, amount);
    }

    @Override
    public ItemBuilder getProvider(Player player) {
        return this.builder;
    }

    @Override
    public void onClick(Menu menu, InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
