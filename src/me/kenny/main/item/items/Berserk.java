package me.kenny.main.item.items;

import me.kenny.main.Main;
import me.kenny.main.item.SpecialItem;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Berserk extends SpecialItem {
    public Berserk(Main main, String name, List<String> description, int cooldown, int uses, Material material) {
        super(main, name, description, cooldown, uses, material);
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            int heartsGained = 4;
            int strengthAmplifier = 1;
            int resistanceAmplifier = 1;
            int effectLength = 10;
            String message = ChatColor.GOLD + "You have gone berserk and gained " +
                    ChatColor.GREEN + heartsGained + " hearts" + ChatColor.GOLD + ", " +
                    ChatColor.GREEN + "Strength " + strengthAmplifier + ChatColor.GOLD + ", and " +
                    ChatColor.GREEN + "Resistance " + resistanceAmplifier + ChatColor.GOLD + " " +
                    ChatColor.GOLD + "for " + ChatColor.GREEN + effectLength + " seconds" + ChatColor.GOLD + "!";
            if (use(player, message)) {
                PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, effectLength * 20, strengthAmplifier - 1);
                PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectLength * 20, resistanceAmplifier - 1);
                player.addPotionEffect(strength);
                player.addPotionEffect(resistance);
                addHealth(player, heartsGained);

                player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1, 0);
                player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 1);
                deductItem(player, event.getItem());
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

    public void addHealth(Player player, int amount) {
        Damageable d = (Damageable) player;
        if (d.getHealth() + amount > 20)
            player.setHealth(20);
        else
            player.setHealth(d.getHealth() + amount);
    }

    public void deductItem(Player player, ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().removeItem(item);
        }
    }
}
