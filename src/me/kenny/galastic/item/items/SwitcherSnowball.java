package me.kenny.galastic.item.items;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.item.SpecialItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftSnowball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class SwitcherSnowball extends SpecialItem {
    public SwitcherSnowball(Galastic galastic, String name, List<String> description, int cooldown, int uses, Material material) {
        super(galastic, name, description, cooldown, uses, material);
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
            String message = ChatColor.GOLD + "You hit " + ChatColor.GREEN + target.getName() + ChatColor.GOLD + " from " + ChatColor.GREEN + (int) distance + " blocks" + ChatColor.GOLD + " away!";
            if (distance <= 10 && use(player, message)) {
                Location playerLocation = player.getLocation().clone();
                Location targetLocation = target.getLocation().clone();
                player.teleport(targetLocation);
                target.teleport(playerLocation);
            } else {
                if (distance > 10) 
                    outOfRange(player);
                refund(player);
                event.setCancelled(true);
            }
        }
    }

    private void outOfRange(Player player) {
        player.sendMessage(ChatColor.RED + "Your target must be at least 10 blocks away!");
    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent event) { }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {

    }

    @Override
    public void onFish(PlayerFishEvent event) {

    }

    public void refund(Player player) {
        player.getInventory().addItem(getItem());
    }
}
