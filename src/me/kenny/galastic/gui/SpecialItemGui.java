package me.kenny.galastic.gui;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.item.SpecialItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class SpecialItemGui implements Gui {
    private Galastic galastic;

    public SpecialItemGui(Galastic galastic) {
        this.galastic = galastic;
    }

    @Override
    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 9, "Special Items");
        for (SpecialItem specialItem : galastic.getSpecialItemHandler().getSpecialItems()) {
            inventory.addItem(specialItem.getItem());
        }
        return inventory;
    }
}
