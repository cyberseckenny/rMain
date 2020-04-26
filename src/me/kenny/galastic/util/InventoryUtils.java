package me.kenny.galastic.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtils {
    public static void setBorder(Inventory inventory, int size, int[] center) {
        ItemStack[] contents = new ItemStack[size];

        ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
        ItemMeta borderItemMeta = border.getItemMeta();
        borderItemMeta.setDisplayName(" ");
        border.setItemMeta(borderItemMeta);

        for (int i = 0; i < size; i++) {
            boolean isCenter = false;
            for (int x : center) {
                if (x == i) {
                    isCenter = true;
                    break;
                }
            }

            if (!isCenter)
                inventory.setItem(i, border);
        }
    }
}
