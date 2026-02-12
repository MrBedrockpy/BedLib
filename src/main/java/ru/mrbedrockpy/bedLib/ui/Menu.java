package ru.mrbedrockpy.bedLib.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;
import ru.mrbedrockpy.bedLib.manager.ManagerItem;
import ru.mrbedrockpy.bedLib.ui.item.Item;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Menu implements ManagerItem {

    public static final NamespacedKey MENU_ID = new NamespacedKey("bedlib", "menu_id");

    private final Player player;
    private Inventory inventory;
    private int x = 0, y = 0;
    private boolean closable = true;

    public final void open() {
        if (inventory != null) return;
        Gui gui = getGui();
        if (gui == null) throw new IllegalStateException("Gui cannot be null!");
        if (gui.getTitle() == null) throw new IllegalStateException("Title of gui cannot be null!");
        this.inventory = Bukkit.createInventory(player, gui.getInventorySize());
        this.update(gui);
        player.openInventory(inventory);
        this.onOpen();
    }

    public final void update() {
        if (inventory == null) return;
        Gui gui = getGui();
        if (gui == null) throw new IllegalStateException("Gui cannot be null!");
        if (gui.getTitle() == null) throw new IllegalStateException("Title of gui cannot be null!");
        this.update(gui);
        this.onUpdate();
    }

    private void update(Gui gui) {
        gui.getGui(x, y).forEach((index, item) -> inventory.setItem(index, item.getProvider(this.player)
                .setPersist(MENU_ID, PersistentDataType.STRING, this.getId()).get()));
    }

    public final void click(InventoryClickEvent event) {
        if (!event.getInventory().equals(this.inventory)) return;
        Gui gui = getGui();
        if (gui == null) throw new IllegalStateException("Gui cannot be null!");
        if (gui.getTitle() == null) throw new IllegalStateException("Title of gui cannot be null!");
        Item item = gui.getGui(x, y).getOrDefault(event.getSlot(), null);
        if (item == null) return;
        item.onClick(this, event);
        this.onClick(event);
    }

    public final void close(InventoryCloseEvent event) {
        this.inventory = null;
        if (event.getReason().equals(InventoryCloseEvent.Reason.PLAYER)) {
            this.open();
            return;
        }
        this.onClose(event);
    }

    public abstract Gui getGui();

    public void onOpen() {}
    public void onUpdate() {}
    public void onClick(InventoryClickEvent event) {}
    public void onClose(InventoryCloseEvent event) {}

    @Override
    public String getId() {
        return UUID.randomUUID().toString();
    }
}
