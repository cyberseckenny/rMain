package me.kenny.main.gkit;

import net.minecraft.server.v1_7_R4.ItemArmor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Gkit {
    private String name, displayName, description;
    private Material material;
    private int cooldown;
    private ItemStack[] items;
    private ItemStack[] armor;

    public Gkit(String name, String displayName, String description, Material material, ItemStack[] items, ItemStack[] armor) {
        this.items = items;
        this.armor = armor;
        this.name = name;
        this.material = material;
        this.displayName = displayName;
        this.description = description;
    }

    public void giveGkit(Player player) {
        for (int i = 0; i < armor.length; i++) {
            switch (i) {
                case 3:
                    if (player.getInventory().getHelmet() == null) {
                        player.getInventory().setHelmet(armor[i]);
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), armor[i]);
                    }
                    break;
                case 2:
                    if (player.getInventory().getChestplate() == null) {
                        player.getInventory().setChestplate(armor[i]);
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), armor[i]);
                    }
                    break;
                case 1:
                    if (player.getInventory().getLeggings() == null) {
                        player.getInventory().setLeggings(armor[i]);
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), armor[i]);
                    }
                    break;
                case 0:
                    if (player.getInventory().getBoots() == null) {
                        player.getInventory().setBoots(armor[i]);
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), armor[i]);
                    }
                    break;
                default:
                    break;
            }
        }

        for (ItemStack item : items) {
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            } else {
                player.getInventory().addItem(item);
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() { return description; }

    public Material getMaterial() { return material; }

    public ItemStack[] getItems() { return items; }

    public ItemStack[] getArmor() { return armor; }
}
