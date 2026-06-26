package ru.mrbedrockpy.bedlib.util;

import lombok.Setter;
import ru.mrbedrockpy.beddebugger.BedDebugger;
import ru.mrbedrockpy.bedlib.BedPlugin;
import ru.mrbedrockpy.bedlib.manager.Manager;

public class Debug {

    @Setter private static BedDebugger plugin;

    public static <P extends BedPlugin<P>> void snapshot(P plugin) {
        Debug.plugin.getPluginSnapshotManager().create(plugin);
    }

    public static void snapshot(Manager<?> manager) {
        Debug.plugin.getManagerSnapshotManager().create(manager);
    }
}
