package ru.mrbedrockpy.bedLib.ui;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.mrbedrockpy.bedLib.text.Text;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public interface Menu {

    default void open(Player player) {
        Window.single()
                .setTitle(this.getTitle().toInvUI())
                .setGui(this.getGui(player))
                .open(player);
    }

    @NotNull Text getTitle();
    @NotNull Gui getGui(Player player);
}
