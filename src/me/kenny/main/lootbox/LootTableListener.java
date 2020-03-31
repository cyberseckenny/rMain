package me.kenny.main.lootbox;

import me.kenny.main.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LootTableListener implements Listener {
    private Main main;

    public LootTableListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onLootMove(InventoryMoveItemEvent event) {
        ItemStack item = event.getItem();
        Inventory initiator = event.getInitiator();
        Inventory source = event.getSource();
        if (item != null && item.getType() != Material.AIR) {

        }
    }
}
