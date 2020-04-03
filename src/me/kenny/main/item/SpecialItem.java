package me.kenny.main.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecialItem {
    private ItemStack item;

    public SpecialItem(String name, List<String> description, int cooldown, int uses, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        String usesString = uses == -1 ? "Infinity" : String.valueOf(uses);
        List<String> lore = new ArrayList<>();
        System.out.println(description);
        for (String string : description) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&f" + string));
        }
        lore.addAll(Arrays.asList("", ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown: " + cooldown + " seconds", ChatColor.GRAY + "" + ChatColor.ITALIC + "Uses: " + usesString));
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
