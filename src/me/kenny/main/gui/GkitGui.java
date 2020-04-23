package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.gkit.Gkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GkitGui implements Gui {
    private Main main;
    private Gkit gkit;

    public GkitGui(Main main, Gkit gkit) {
        this.main = main;
        this.gkit = gkit;
    }

    @Override
    public Inventory getGui() {
        int total = gkit.getItems().length + gkit.getArmor().length;
        Inventory inventory = Bukkit.createInventory(null, (9 * (total / 9)) + 9, ChatColor.translateAlternateColorCodes('&', gkit.getDisplayName()));

        for (int i = gkit.getArmor().length - 1; i > -1; i--) {
            inventory.addItem(gkit.getArmor()[i]);
        }

        for (ItemStack item : gkit.getItems()) {
            inventory.addItem(item);
        }

        return inventory;
    }
}
