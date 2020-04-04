package me.kenny.main.item.items;

import me.kenny.main.Main;
import me.kenny.main.item.SpecialItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CobwebGun extends SpecialItem {
    public CobwebGun(Main main, String name, List<String> description, int cooldown, int uses, Material material) {
        super(main, name, description, cooldown, uses, material);
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            String fired = "Fired " + ChatColor.GREEN +  getName() + ChatColor.GOLD + ". Upon spawning, webs will be removed in " + ChatColor.GREEN + "10 seconds" + ChatColor.GOLD + ".";
            if (use(player, fired)) {
                Arrow arrow = player.launchProjectile(Arrow.class);
            }
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {

    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent event) {

    }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        projectile.remove();

        Set<Block> remove = new HashSet<>();
        for (BlockFace face : BlockFace.values()) {
            if (face == BlockFace.UP || face == BlockFace.DOWN ||
                    face == BlockFace.EAST_SOUTH_EAST || face == BlockFace.EAST_NORTH_EAST ||
                    face == BlockFace.SOUTH_SOUTH_EAST || face == BlockFace.SOUTH_SOUTH_WEST ||
                    face == BlockFace.NORTH_NORTH_EAST || face == BlockFace.NORTH_NORTH_WEST ||
                    face == BlockFace.WEST_NORTH_WEST || face == BlockFace.WEST_SOUTH_WEST)
                continue;
            Block block = projectile.getLocation().getBlock().getRelative(face);
            if (block.getType() == Material.AIR)
                block.setType(Material.WEB);
            remove.add(block);
        }

        // custom block faces :)
        for (CustomBlockFace face : CustomBlockFace.values()) {
            Block block = getRelative(face, projectile.getLocation().getBlock());
            if (block.getType() == Material.AIR)
                block.setType(Material.WEB);
            remove.add(block);
        }

        Bukkit.getScheduler().runTaskLater(main, new BukkitRunnable() {
            @Override
            public void run() {
                for (Block block : remove) {
                    block.setType(Material.AIR);
                }
            }
        }, 200L);
    }

    @Override
    public void onFish(PlayerFishEvent event) {

    }

    public enum CustomBlockFace {
        NORTH_NORTH(0, 0, -2),
        SOUTH_SOUTH(0, 0, 2),
        EAST_EAST(2, 0, 0),
        WEST_WEST(-2, 0, 0);

        private int modX;
        private int modY;
        private int modZ;

        CustomBlockFace(int modX, int modY, int modZ) {
            this.modX = modX;
            this.modY = modY;
            this.modZ = modZ;
        }
    }

    public Block getRelative(CustomBlockFace face, Block block) {
        return block.getWorld().getBlockAt(block.getLocation().add(face.modX, face.modY, face.modZ));
    }
}
