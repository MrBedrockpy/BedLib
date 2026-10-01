package ru.mrbedrockpy.bedlib.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.persistence.PersistentDataType;
import ru.mrbedrockpy.bedlib.BedLib;
import ru.mrbedrockpy.bedlib.manager.Dto;
import ru.mrbedrockpy.bedlib.ui.gui.Gui;
import ru.mrbedrockpy.bedlib.ui.item.GlobalItem;
import ru.mrbedrockpy.bedlib.ui.item.Item;

import java.util.*;

@Getter
@RequiredArgsConstructor
public abstract class Menu<M extends Menu<M>> implements Dto {

    public static final MenuManager MENU_MANAGER = BedLib.getPlugin(BedLib.class).getMenuManager();

    private final List<InventoryCloseEvent.Reason> closableReasons = new ArrayList<>(List.of(
            InventoryCloseEvent.Reason.PLUGIN,
            InventoryCloseEvent.Reason.DISCONNECT
    ));

    private Map<Integer, SlotData> cachedItems;

    private final Player player;
    private Inventory inventory;
    private InventoryView view;
    private Gui gui;

    @Setter private boolean closable = true;

    public final void open() {
        if (this.inventory != null) return;
        this.gui = this.setupGui();
        if (this.gui == null) throw new RuntimeException("Gui cannot be null!");
        if (this.gui.getTitle() == null) throw new RuntimeException("Title cannot be null!");
        this.inventory = Bukkit.createInventory(this.player, this.gui.getSize().getSize(), this.gui.getTitle().toAdventure());
        this.updateItems();
        this.view = this.player.openInventory(inventory);
        this.onOpen();
        MENU_MANAGER.register(this);
    }

    public void updateGui() {
        this.gui = this.setupGui();
        if (this.gui == null) throw new RuntimeException("Gui cannot be null!");
        if (this.gui.getTitle() == null) throw new RuntimeException("Title cannot be null!");
        this.updateItems();
    }

    public void updateItems() {
        this.inventory.clear();
        if (this.view != null) this.view.setTitle(this.gui.getTitle().toVanilla());
        this.cachedItems = this.gui.render();
        this.cachedItems.forEach(this::updateItem);
    }

    public void updateItem(int slot, SlotData data) {
        Item item = data.item();
        if (item instanceof GlobalItem globalItem) {
            globalItem.setMenu(this);
            globalItem.setX(data.structX());
            globalItem.setY(data.structY());
        }
    }

    public final void click(InventoryClickEvent event) {
        if (!event.getView().getTopInventory().equals(this.inventory)) return;
        event.setCancelled(true);
        SlotData data = this.cachedItems.getOrDefault(event.getSlot(), null);
        if (data == null || data.item() == null) return;
        data.item().onClick(this, event);
        this.onClick(event);
    }

    public final void close(InventoryCloseEvent event) {
        this.inventory = null;
        if (!closableReasons.contains(event.getReason()) && !closable) Bukkit.getScheduler()
                .runTaskLater(BedLib.getPlugin(BedLib.class).getMenuManager()
                        .getPlugin(), this::open, 1L);
        else {
            this.onClose(event);
            MENU_MANAGER.unregister(this);
        }
    }

    public abstract Gui setupGui();

    public void onOpen() {}
    public void onClick(InventoryClickEvent event) {}
    public void onClose(InventoryCloseEvent event) {}

    @Override
    public String getId() {
        return player.getName();
    }
}
