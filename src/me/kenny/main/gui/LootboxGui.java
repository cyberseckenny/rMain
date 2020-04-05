package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.util.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LootboxGui implements Gui {
    private Main main;

    public LootboxGui(Main main) {
        this.main = main;
    }

    @Override
    public Inventory getGui() {
        int size = 54;
        // won't place borders if the index is one of these
        int[] center = {12, 13, 14, 21, 22, 23, 30, 31, 32, 40};

        // TODO: fix lootbox giving the same index as the last clicked chest and the ender chest

        Inventory gui = Bukkit.createInventory(null, size, main.getLootboxName());
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
