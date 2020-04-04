package me.kenny.main.item.items;

import me.kenny.main.Main;
import me.kenny.main.item.SpecialItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftSnowball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class SwitcherSnowball extends SpecialItem {
    public SwitcherSnowball(Main main, String name, List<String> description, int cooldown, int uses, Material material) {
        super(main, name, description, cooldown, uses, material);
    }

    @Override
    public void onInteract(PlayerInteractEvent event) { }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile && event.getDamager() instanceof CraftSnowball) {
            Projectile snowball = (Projectile) event.getDamager();
            Player player = (Player) snowball.getShooter();
            Player target = (Player) event.getEntity();
            double distance = player.getLocation().distance(target.getLocation());
            String message = "You hit " + ChatColor.GOLD + target.getName() + ChatColor.WHITE + " from " + ChatColor.GOLD + (int) distance + " blocks" + ChatColor.WHITE + " away!";
            if (distance <= 10 && use(player, message)) {
                Location playerLocation = player.getLocation().clone();
                Location targetLocation = target.getLocation().clone();
                player.teleport(targetLocation);
                target.teleport(playerLocation);
            } else {
                if (distance > 10)
                    refund(player);
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent event) { }

    public void refund(Player player) {
        player.sendMessage(ChatColor.RED + "Your target must be at least 10 blocks away!");
        player.getInventory().addItem(getItem());
    }
}
