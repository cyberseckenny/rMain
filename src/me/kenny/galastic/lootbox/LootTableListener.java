package me.kenny.galastic.lootbox;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.gui.LootTableGui;
import me.kenny.galastic.util.Info;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootTableListener implements Listener {
    private Galastic galastic;

    public LootTableListener(Galastic galastic) {
        this.galastic = galastic;
    }

    @EventHandler
    public void onLootMove(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            Inventory inventory = event.getInventory();
            if (inventory.getTitle().equalsIgnoreCase(LootTableGui.inventoryTitle)) {
                ItemStack item = event.getCurrentItem();
                Inventory clickedInventory = event.getClickedInventory();
                Player player = (Player) event.getWhoClicked();

                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)  {
                    if (inventory instanceof CraftInventory && clickedInventory instanceof CraftInventoryPlayer) {
                        boolean rare = false;
                        if (galastic.getLootConfig().hasIdenticalItem(item, "")) {
                            player.sendMessage(ChatColor.RED + "You can not add duplicates of an item to the loot table!");
                            event.setCancelled(true);
                        } else {
                            int key = galastic.getLootConfig().addItem(item, rare, "");
                            String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                            player.sendMessage(Info.main("Successfully added " + name + ChatColor.RESET + " to loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + ". (rare: " + ChatColor.RED + rare + ChatColor.RESET + ")"));
                        }
                    } else if (inventory instanceof CraftInventory && clickedInventory instanceof CraftInventory) {
                        int key = galastic.getLootConfig().removeItem(item, "");
                        String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                        player.sendMessage(Info.main("Successfully removed " + name + ChatColor.RESET + " from loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + "."));
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
