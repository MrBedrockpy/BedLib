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
import org.bukkit.inventory.InventoryView;
import org.bukkit.persistence.PersistentDataType;
import ru.mrbedrockpy.bedLib.manager.ManagerItem;
import ru.mrbedrockpy.bedLib.ui.gui.Gui;
import ru.mrbedrockpy.bedLib.ui.item.GlobalItem;
import ru.mrbedrockpy.bedLib.ui.item.Item;

import java.util.*;

@Getter
@RequiredArgsConstructor
public abstract class Menu<M extends Menu<M>> implements ManagerItem {

    public static final NamespacedKey MENU_ID = new NamespacedKey("bedlib", "menu_id");

    private final List<InventoryCloseEvent.Reason> closableReasons = new ArrayList<>(List.of(
            InventoryCloseEvent.Reason.PLUGIN,
            InventoryCloseEvent.Reason.DISCONNECT
    ));

    private final String id = UUID.randomUUID().toString();

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
        MenuManager.INSTANCE.register(this);
    }

    public void updateItems() {
        this.inventory.clear();
        if (this.view != null) this.view.setTitle(this.gui.getTitle().toVanilla());
        this.cachedItems = this.gui.render();
        this.cachedItems.forEach((index, data) -> {
            Item item = data.item();
            try {
                GlobalItem<M> globalItem = (GlobalItem<M>) item;
                globalItem.setMenu((M) this);
                globalItem.setX(data.structX());
                globalItem.setY(data.structY());
            } catch (ClassCastException ignored) {}
            this.inventory.setItem(index, item.getProvider(this.player)
                    .setPersist(MENU_ID, PersistentDataType.STRING, this.getId()).get());
        });
    }

    public final void click(InventoryClickEvent event) {
        if (!event.getView().getTopInventory().equals(this.inventory)) return;
        SlotData data = this.cachedItems.getOrDefault(event.getSlot(), null);
        if (data.item() == null) return;
        data.item().onClick(this, event);
        this.onClick(event);
    }

    public final void close(InventoryCloseEvent event) {
        this.inventory = null;
        if (!closableReasons.contains(event.getReason()) && !closable) Bukkit.getScheduler()
                .runTaskLater(MenuManager.INSTANCE.getPlugin(), this::open, 1L);
        else this.onClose(event);
    }

    public abstract Gui setupGui();

    public void onOpen() {}
    public void onClick(InventoryClickEvent event) {}
    public void onClose(InventoryCloseEvent event) {}

    @Override
    public String getId() {
        return id;
    }
}
