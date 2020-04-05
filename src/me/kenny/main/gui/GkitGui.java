package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.gkit.Gkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class GkitGui implements Gui {
    private Main main;
    private String title;
    private Gkit gkit;

    public GkitGui(Main main, String title, Gkit gkit) {
        this.main = main;
        this.title = title;
        this.gkit = gkit;
    }

    @Override
    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', title));
        for (int i = 0; i < 36; i++) {
            inventory.setItem(i, gkit.getItems()[i]);
        }

        for (int i = 43; i < 47; i++) {
            inventory.setItem(i, gkit.getArmor()[i]);
        }

        return inventory;
    }
}
