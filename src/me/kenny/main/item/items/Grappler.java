package me.kenny.main.item.items;

import me.kenny.main.Main;
import me.kenny.main.item.SpecialItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class Grappler extends SpecialItem {
    public Grappler(Main main, String name, List<String> description, int cooldown, int uses, Material material) {
        super(main, name, description, cooldown, uses, material);
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
        String message = "Used grappler!";
        if (use(player, message)) {
            player.setVelocity(event.getHook().getVelocity().multiply(1.3).setY(0.45));
        }
    }
}
