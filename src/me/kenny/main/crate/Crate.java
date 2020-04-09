package me.kenny.main.crate;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class Crate {
    private Location location;
    private String text;
    private Set<ItemStack> items;

    public Crate(Location location, String text, Set<ItemStack> items, boolean bool) {
        this.location = location;
        this.text = text;
        this.items = items;
    }

    public void giveKey(Player player) {
        ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = key.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', text) + ChatColor.RESET + ChatColor.GRAY + " Key");
        key.setItemMeta(meta);
        player.getInventory().addItem(key);
    }

    public String getText() {
        return text;
    }

    public Location getLocation() {
        return location;
    }

    public Set<ItemStack> getItems() {
        return items;
    }
}
