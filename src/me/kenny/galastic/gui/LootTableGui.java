package me.kenny.galastic.gui;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.util.DoubleValue;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class LootTableGui implements Gui {
    private Galastic galastic;
    public final static String inventoryTitle = "Viewing loot table";

    public LootTableGui(Galastic galastic) {
        this.galastic = galastic;
    }

    @Override
    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 54, inventoryTitle);
        for (Map.Entry<ItemStack, DoubleValue> loot : galastic.getLootConfig().getItems("").entrySet()) {
            // inventories start 0, keys start at 1
            int key = loot.getValue().getInteger() - 1;
            boolean rare = loot.getValue().getBoolean();
            ItemStack item = loot.getKey();
            inventory.setItem(key, item);
        }
        return inventory;
    }
}
