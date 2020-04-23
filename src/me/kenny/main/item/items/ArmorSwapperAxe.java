package me.kenny.main.item.items;

import me.kenny.main.Main;
import me.kenny.main.item.SpecialItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ArmorSwapperAxe extends SpecialItem {
    public ArmorSwapperAxe(Main main, String name, List<String> description, int cooldown, int uses, Material material) {
        super(main, name, description, cooldown, uses, material);
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        if (target.getInventory().getHelmet() != null) {
            if (target.getInventory().firstEmpty() != -1) {
                String message = "Swapping the helmet of " + ChatColor.GREEN + target.getName() + ChatColor.GOLD + " in " + ChatColor.GREEN + "5 seconds" + ChatColor.GOLD + ".";
                if (use(damager, message)) {
                    Bukkit.getScheduler().runTaskLater(main, new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (target.getInventory().getHelmet() != null) {
                                ItemStack helmet = target.getInventory().getHelmet();
                                target.getInventory().setHelmet(null);
                                target.getInventory().addItem(helmet);
                            }
                        }
                    }, 100L);
                }
             } else {
                damager.sendMessage(ChatColor.RED + "You can not use " + getName() + " if a player has a full inventory!");
            }
        } else {
            damager.sendMessage(ChatColor.RED + "You can not use a " + getName() + " on a player without a helmet!");
        }
    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent event) {

    }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {

    }

    @Override
    public void onFish(PlayerFishEvent event) {

    }
}
