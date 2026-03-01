package ru.mrbedrockpy.bedLib.script;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.mrbedrockpy.bedLib.BedPlugin;
import ru.mrbedrockpy.bedLib.util.Pos;

@Getter
@AllArgsConstructor
public class ScriptContext<P extends BedPlugin<P>> {

    private final P plugin;
    private final Player player;

    public Pos getPos() {
        return Pos.fromLoc(player.getLocation());
    }
    public Location getLocation() {
        return player.getLocation();
    }
    public World getWorld() {
        return player.getWorld();
    }
    public Server getServer() {
        return player.getServer();
    }

}
