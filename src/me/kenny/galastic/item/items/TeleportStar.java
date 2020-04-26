package me.kenny.galastic.item.items;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.item.SpecialItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeleportStar extends SpecialItem {
    private Map<Player, Player> lastAttacker = new HashMap<>();

    public TeleportStar(Galastic galastic, String name, List<String> description, int cooldown, int uses, Material material) {
        super(galastic, name, description, cooldown, uses, material);
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Map<Player, Player> lastAttacker = galastic.getSpecialItemHandler().getLastAttacker();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (lastAttacker.get(player) != null) {
                String message = "Teleporting to current location of " + ChatColor.GREEN + lastAttacker.get(player).getName() + ChatColor.GOLD + " in " + ChatColor.GREEN + "5 seconds" + ChatColor.GOLD + ".";
                if (use(player, message)) {
                    deductItem(player, player.getInventory().getItemInHand());
                    Location targetLocation = lastAttacker.get(player).getLocation().clone();
                    Bukkit.getScheduler().runTaskLater(galastic, new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(targetLocation);
                        }
                    }, 100L);
                }
            } else {
                player.sendMessage(ChatColor.RED + "Nobody has attacked you in the last 15 seconds!");
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

    }

    @Override
    public void onFish(PlayerFishEvent event) {

    }
}
