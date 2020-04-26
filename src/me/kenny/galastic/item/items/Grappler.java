package me.kenny.galastic.item.items;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.item.SpecialItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class Grappler extends SpecialItem {
    public Grappler(Galastic galastic, String name, List<String> description, int cooldown, int uses, Material material) {
        super(galastic, name, description, cooldown, uses, material);
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

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
        Player player = event.getPlayer();
        String message = "Used " + ChatColor.GREEN + getName() + ChatColor.GOLD + "!";
        if (use(player, message)) {
            Vector playerVector = player.getLocation().toVector();
            Vector hookVector = event.getHook().getLocation().toVector();
            player.setVelocity(hookVector.subtract(playerVector).multiply(0.3));
        }
    }
}
