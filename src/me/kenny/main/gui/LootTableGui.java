package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.util.DoubleValue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LootTableGui implements Gui {
    private Main main;
    public final static String inventoryTitle = "Viewing loot table";

    public LootTableGui(Main main) {
        this.main = main;
    }

    @Override
    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 54, inventoryTitle);
        for (Map.Entry<ItemStack, DoubleValue> loot : main.getLootConfig().getItems("").entrySet()) {
            // inventories start 0, keys start at 1
            int key = loot.getValue().getInteger() - 1;
            boolean rare = loot.getValue().getBoolean();
            ItemStack item = loot.getKey();
            inventory.setItem(key, item);
        }
        return inventory;
    }
}
