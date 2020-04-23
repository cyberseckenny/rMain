package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.item.SpecialItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SpecialItemGui implements Gui {
    private Main main;

    public SpecialItemGui(Main main) {
        this.main = main;
    }

    @Override
    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 9, "Special Items");
        for (SpecialItem specialItem : main.getSpecialItemHandler().getSpecialItems()) {
            inventory.addItem(specialItem.getItem());
        }
        return inventory;
    }
}
