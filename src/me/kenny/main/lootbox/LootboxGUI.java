package me.kenny.main.lootbox;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootboxGUI {
    public LootboxGUI(Player player) {
        player.openInventory(getGUI());
    }

    public Inventory getGUI() {
        int size = 63;
        // won't place borders if the index is one of these
        int[] center = {21, 22, 23, 30, 31, 32, 39, 40, 41, 49};

        Inventory gui = Bukkit.createInventory(null, size);
        ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
        for (int i = 0; i < size; i++) {
            boolean isCenter = false;
            for (int x : center) {
                if (x == i) {
                    isCenter = true;
                    break;
                }
            }

            if (!isCenter)
                gui.setItem(i, border);
        }

        return gui;
    }
}
