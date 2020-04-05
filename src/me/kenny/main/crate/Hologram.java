package me.kenny.main.crate;

import me.kenny.main.item.items.ArmorSwapperAxe;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Hologram {
    private String text;
    private Location location;

    public Hologram(String text, Location location) {
        this.text = text;
        this.location = location;

        Horse horse = getHorseAtLocation(location);
        if (getHorseAtLocation(location) == null) {
            createHologram();
        }
    }

    @Deprecated
    public void createHologram() {
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        EntityWitherSkull skull = new EntityWitherSkull(world);
        skull.setLocation(location.getX() + 0.5, location.getY() + 55.5, location.getZ() + 0.5 , 0, 0);
        ((CraftWorld) location.getWorld()).getHandle().addEntity(skull);

        EntityHorse horse = new EntityHorse(world);
        horse.setLocation(location.getX(), location.getY() + 54.5, location.getZ(), 0, 0);
        horse.setAge(-1700000);
        horse.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
        horse.setCustomNameVisible(true);
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(horse);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            nmsPlayer.playerConnection.sendPacket(packet);

            PacketPlayOutAttachEntity pa = new PacketPlayOutAttachEntity(0, horse, skull);
            nmsPlayer.playerConnection.sendPacket(pa);
        }
    }

    public Horse getHorseAtLocation(Location loc) {
        for (LivingEntity entity : loc.getWorld().getLivingEntities()) {
            if (entity.getLocation().equals(loc) && entity instanceof Horse)
                return (Horse) entity;
        }
        return null;
    }
}
