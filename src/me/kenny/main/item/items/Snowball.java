package me.kenny.main.item.items;

import me.kenny.main.Main;
import me.kenny.main.item.SpecialItem;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class Snowball extends SpecialItem {
    public Snowball(Main main, String name, List<String> description, int cooldown, int uses, Material material) {
        super(main, name, description, cooldown, uses, material);
    }

    @Override
    public void onInteract(PlayerInteractEvent event) { }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        System.out.print(1);
    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent event) { }
}
