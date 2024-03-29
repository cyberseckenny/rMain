package me.kenny.galastic.gui;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.util.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LootboxGui implements Gui {
    private Galastic galastic;

    public LootboxGui(Galastic galastic) {
        this.galastic = galastic;
    }

    @Override
    public Inventory getGui() {
        int size = 54;
        // won't place borders if the index is one of these
        int[] center = {12, 13, 14, 21, 22, 23, 30, 31, 32, 40};
        Inventory gui = Bukkit.createInventory(null, size, galastic.getLootboxName());
        InventoryUtils.setBorder(gui, size, center);

        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestItemMeta = chest.getItemMeta();
        chestItemMeta.setDisplayName(ChatColor.YELLOW + "???");
        chestItemMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click to reveal loot."));
        chest.setItemMeta(chestItemMeta);

        ItemStack enderChest = new ItemStack(Material.ENDER_CHEST);
        ItemMeta enderChestItemMeta = enderChest.getItemMeta();
        enderChestItemMeta.setDisplayName(ChatColor.RED + "???");
        enderChestItemMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click to reveal loot."));
        enderChest.setItemMeta(enderChestItemMeta);

        for (int i : center) {
            // sets the last item in the array to the ender chest
            if (i == center[center.length - 1])
                gui.setItem(i, enderChest);
            else
                gui.setItem(i, chest);
        }

        return gui;
    }
}
