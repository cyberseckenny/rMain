package me.kenny.main.item;

import com.mysql.jdbc.TimeUtil;
import me.kenny.main.Main;
import me.kenny.main.util.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class SpecialItem {
    public Main main;
    private String name;
    private ItemStack item;
    private int cooldown;
    private int uses;

    public SpecialItem(Main main, String name, List<String> description, int cooldown, int uses, Material material) {
        this.main = main;
        this.name = name;
        this.cooldown = cooldown;
        this.uses = uses;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        String usesString = uses == -1 ? "Infinity" : String.valueOf(uses);
        List<String> lore = new ArrayList<>();
        for (String string : description) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&f" + string));
        }
        String formattedCooldown = TimeUtils.getFormattedTimeWithoutZeroes(cooldown);
        lore.addAll(Arrays.asList("", ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown: " + formattedCooldown, ChatColor.GRAY + "" + ChatColor.ITALIC + "Uses: " + usesString));
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.item = item;
    }

    public abstract void onInteract(PlayerInteractEvent event);
    public abstract void onDamage(EntityDamageByEntityEvent event);
    public abstract void onProjectileLaunch(ProjectileLaunchEvent event);
    public abstract void onProjectileHit(ProjectileHitEvent event);
    public abstract void onFish(PlayerFishEvent event);

    public String getName() { return name; }
    public ItemStack getItem() {
        return item;
    }
    public int getCooldown() { return cooldown; }
    public int getUses() { return uses; }

    public boolean use(Player player, String message) {
        String formattedCooldownString = name.toLowerCase().replace(" ", "");
        if (!main.getCooldownConfig().isOnCooldown(player, formattedCooldownString)) {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + name + ChatColor.GRAY + "] " + ChatColor.GOLD + message);
            main.getCooldownConfig().addCooldown(player, name.toLowerCase().replace(" ", ""), cooldown);
            return true;
        } else {
            int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(main.getCooldownConfig().getCooldown(player, formattedCooldownString) - System.currentTimeMillis());
            player.sendMessage(ChatColor.RED + name + " is on cooldown for " + TimeUtils.getFormattedTime(seconds) + ".");
            return false;
        }
    }

    public void deductItem(Player player, ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().removeItem(item);
        }
    }
}
