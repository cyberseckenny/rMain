package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.util.DoubleValue;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class RareLootTableGui implements Gui {
    private Main main;
    public final static String inventoryTitle = "Viewing rare loot table";

    public RareLootTableGui(Main main) {
        this.main = main;
    }

    @Override
    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 54, inventoryTitle);
        for (Map.Entry<ItemStack, DoubleValue> loot : main.getRareLootConfig().getItems("").entrySet()) {
            // inventories start 0, keys start at 1
            int key = loot.getValue().getInteger() - 1;
            boolean rare = loot.getValue().getBoolean();
            ItemStack item = loot.getKey();
            inventory.setItem(key, item);
        }
        return inventory;
    }
}
