package me.kenny.main.item;

import me.kenny.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SpecialItemGui {
    private Main main;

    public SpecialItemGui(Main main, Player player) {
        this.main = main;
        player.openInventory(getGui());
    }

    private Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 9, "Special Items");
        for (SpecialItem specialItem : main.getSpecialItemHandler().getSpecialItems()) {
            inventory.addItem(specialItem.getItem());
        }
        return inventory;
    }
}
