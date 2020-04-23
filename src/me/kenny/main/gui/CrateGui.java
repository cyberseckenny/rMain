package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.crate.Crate;
import me.kenny.main.util.DoubleValue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CrateGui implements Gui {
    private String name;
    private Main main;

    public CrateGui(Main main, String name) {
        this.main = main;
        this.name = name;
    }

    @Override
    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', name) + " (currently editing)");
        for (String path : main.getCrateConfig().getFileConfiguration().getKeys(false)) {
            if (path.equals(name)) {
                for (Map.Entry<ItemStack, DoubleValue> entry : main.getCrateConfig().getItems(path + ".items").entrySet()) {
                    // inventories start 0, keys start at 1
                    int key = entry.getValue().getInteger() - 1;
                    ItemStack item = entry.getKey();
                    inventory.setItem(key, item);
                    inventory.addItem(item);
                }
            }
        }
        return inventory;
    }
}
