package me.kenny.main.item;

import me.kenny.main.Main;
import me.kenny.main.item.items.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class SpecialItemHandler implements Listener {
    private Main main;
    private List<SpecialItem> specialItems = new ArrayList<>();

    public SpecialItemHandler(Main main) {
        this.main = main;

        for (String path : main.getConfig().getKeys(false)) {
            // checks if the path is actually an item
            if (path.equalsIgnoreCase("items")) {
                ConfigurationSection configuration = main.getConfig().getConfigurationSection(path);
                for (String section : configuration.getKeys(false)) {
                    String name = configuration.getString(section + ".name");
                    List<String> description = configuration.getStringList(section + ".description");
                    int cooldown = configuration.getInt(section + ".cooldown");
                    int uses = configuration.getInt(section + ".uses");
                    Material material = Material.getMaterial(configuration.getString(section + ".material"));
                    SpecialItem specialItem = getNewSpecialItem(name, description, cooldown, uses, material);
                    specialItems.add(specialItem);
                }
            }
        }
    }

    // returns a new special item based on the class it will belong to
    public SpecialItem getNewSpecialItem(String name, List<String> description, int cooldown, int uses, Material material) {
        switch (name) {
            case "Switcher Snowball":
                return new SwitcherSnowball(main, name, description, cooldown, uses, material);
            case "Grappler":
                return new Grappler(main, name, description, cooldown, uses, material);
            case "Cobweb Gun":
                return new CobwebGun(main, name, description, cooldown, uses, material);
            case "Armor Swapper Axe":
                return new ArmorSwapperAxe(main, name, description, cooldown, uses, material);
            case "Teleport Star":
                return new TeleportStar(main, name, description, cooldown, uses, material);
            case "Berserk":
                return new Berserk(main, name, description, cooldown, uses, material);
        }
        return null;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getType() != Material.AIR && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null) {
            for (SpecialItem specialItem : getSpecialItems()) {
                if (matchesSpecialItemName(specialItem.getName(), item.getItemMeta().getDisplayName()))
                    specialItem.onInteract(event);
            }
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            ItemStack item = player.getInventory().getItemInHand();
            if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null) {
                for (SpecialItem specialItem : getSpecialItems()) {
                    if (matchesSpecialItemName(specialItem.getName(), item.getItemMeta().getDisplayName())) {
                        specialItem.onProjectileLaunch(event);
                        event.getEntity().setMetadata(specialItem.getName(), new FixedMetadataValue(main, specialItem.getName()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            for (SpecialItem specialItem : getSpecialItems()) {
                if (event.getEntity().hasMetadata(specialItem.getName()))
                    specialItem.onProjectileHit(event);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getItemInHand();
            if (item != null && item.getType() != Material.AIR && item.getItemMeta() != null) {
                for (SpecialItem specialItem : getSpecialItems()) {
                    if (matchesSpecialItemName(specialItem.getName(), item.getItemMeta().getDisplayName()))
                        specialItem.onDamage(event);
                }
            }
        }

        if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
            for (SpecialItem specialItem : getSpecialItems()) {
                if (event.getDamager().hasMetadata(specialItem.getName()))
                    specialItem.onDamage(event);
            }
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.IN_GROUND || event.getState() == PlayerFishEvent.State.FAILED_ATTEMPT) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInHand();
            if (item != null && item.getType() != Material.AIR && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null) {
                for (SpecialItem specialItem : getSpecialItems()) {
                    if (matchesSpecialItemName(specialItem.getName(), item.getItemMeta().getDisplayName()))
                        specialItem.onFish(event);
                }
            }
        }

    }

    public boolean matchesSpecialItemName(String string, String name) {
        if (ChatColor.stripColor(name).equals(string))
            return true;
        return false;
    }

    public List<SpecialItem> getSpecialItems() {
        return specialItems;
    }
}
