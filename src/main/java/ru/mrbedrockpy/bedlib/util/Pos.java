package ru.mrbedrockpy.bedlib.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@AllArgsConstructor
public class Pos {

    private final int x;
    private final int y;
    private final int z;
    private final float yaw;
    private final float pitch;

    public static Pos of(int x, int y, int z) {
        return of(x, y, z, 0, 0);
    }
    public static Pos of(int x, int y, int z, float yaw, float pitch) {
        return new Pos(x, y, z, yaw, pitch);
    }

    public Location getLocation(World world) {
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Pos fromString(String s) {
        int x = 0, y = 0, z = 0;
        float yaw = 0, pitch = 0;
        String[] coords = s.split(" ");
        try {
            x = Integer.parseInt(coords[0]);
            y = Integer.parseInt(coords[1]);
            z = Integer.parseInt(coords[2]);
            yaw = Float.parseFloat(coords[3]);
            pitch = Float.parseFloat(coords[4]);
            return new Pos(x, y, z, yaw, pitch);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return new Pos(x, y, z, yaw, pitch);
        }
    }

    public static Pos fromLoc(Location loc) {
        return new Pos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), loc.getPitch());
    }

    public String serialize() {
        return x + " " + y + " " + z + " " + yaw + " " + pitch;
    }

    @Override
    public String toString() {
        return "Pos " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch;
    }
}
