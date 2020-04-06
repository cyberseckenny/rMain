package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.gkit.Gkit;
import me.kenny.main.util.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class GkitMenuGui implements Gui {
    private Main main;
    public static String title = ChatColor.RED + "Gkits";

    public GkitMenuGui(Main main, Player player) {
        this.main = main;
    }

    @Override
    public Inventory getGui() {
        int[] center = {20, 21, 22, 23, 24, 29, 30, 31, 32, 33};
        Inventory inventory = Bukkit.createInventory(null, 54, title);
        InventoryUtils.setBorder(inventory, 54, center);

        for (Gkit gkit : main.getGkitConfig().getGkits()) {
            ;
        }

        return inventory;
    }
}
