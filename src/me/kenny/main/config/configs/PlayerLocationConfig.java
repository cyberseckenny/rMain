package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLocationConfig extends Config implements Listener {
    public PlayerLocationConfig(Main main) {
        super(main, "locations");
    }

    public void setLocation(Player player) {
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();
        String world = player.getLocation().getWorld().getName();
        getFileConfiguration().set(player.getUniqueId().toString() + ".x", x);
        getFileConfiguration().set(player.getUniqueId().toString() + ".y", y);
        getFileConfiguration().set(player.getUniqueId().toString() + ".z", z);
        getFileConfiguration().set(player.getUniqueId().toString() + ".yaw", yaw);
        getFileConfiguration().set(player.getUniqueId().toString() + ".pitch", pitch);
        getFileConfiguration().set(player.getUniqueId().toString() + ".world", world);
        save();
    }

    public Location getLocation(OfflinePlayer player) {
        for (String key : getFileConfiguration().getKeys(false)) {
            if (key.equals(player.getUniqueId().toString())) {
                double x = getFileConfiguration().getDouble(player.getUniqueId() + ".x");
                double y = getFileConfiguration().getDouble(player.getUniqueId() + ".y");
                double z = getFileConfiguration().getDouble(player.getUniqueId() + ".z");
                float yaw = (float) getFileConfiguration().getDouble(player.getUniqueId() + ".yaw");
                float pitch = (float) getFileConfiguration().getDouble(player.getUniqueId() + ".pitch");
                World world = Bukkit.getWorld(getFileConfiguration().getString(player.getUniqueId() + ".world"));

                return new Location(world, x, y, z, yaw, pitch);
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        setLocation(event.getPlayer());
    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event) {
        setLocation(event.getPlayer());
    }
}
