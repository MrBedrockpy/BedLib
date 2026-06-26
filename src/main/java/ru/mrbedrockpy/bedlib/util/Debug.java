package ru.mrbedrockpy.bedlib.util;

import lombok.Setter;
import ru.mrbedrockpy.beddebugger.BedDebugger;
import ru.mrbedrockpy.bedlib.BedPlugin;
import ru.mrbedrockpy.bedlib.manager.Manager;

public class Debug {

    @Setter private static BedDebugger plugin = null;

    public static <P extends BedPlugin<P>> void snapshot(P plugin) {
        if (Debug.plugin != null) Debug.plugin.getPluginSnapshotManager().create(plugin);
    }

    public static void snapshot(Manager<?> manager) {
        if (Debug.plugin != null) Debug.plugin.getManagerSnapshotManager().create(manager);
    }
}
