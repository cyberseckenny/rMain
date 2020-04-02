package me.kenny.main.lootbox;

import me.kenny.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class LootTableGui {
    private Main main;
    public final static String inventoryTitle = "Viewing loot table";

    public LootTableGui(Main main, Player player) {
        this.main = main;
    }

    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 54, inventoryTitle);
        for (Map.Entry<ItemStack, Integer> loot : main.getLootConfig().getLoot().entrySet()) {
            // inventories start 0, keys start at 1
            int key = loot.getValue() - 1;
            ItemStack item = loot.getKey();
            inventory.setItem(key, item);
        }
        return inventory;
    }
}
