package me.kenny.main.lootbox;

import me.kenny.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootTableGui {
    private Main main;

    public LootTableGui(Main main, Player player) {
        this.main = main;
        player.openInventory(getGui());
    }

    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 90, "Viewing loot table");
        for (ItemStack item : main.getLootConfig().getLoot()) {
            inventory.addItem(item);
        }
        return inventory;
    }
}
