package me.kenny.main.lootbox;

import me.kenny.main.Main;
import me.kenny.main.gui.LootTableGui;
import me.kenny.main.gui.RareLootTableGui;
import me.kenny.main.util.Info;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RareLootTableListener implements Listener {
    private Main main;

    public RareLootTableListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onLootMove(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            Inventory inventory = event.getInventory();
            if (inventory.getTitle().equalsIgnoreCase(RareLootTableGui.inventoryTitle)) {
                ItemStack item = event.getCurrentItem();
                Inventory clickedInventory = event.getClickedInventory();
                Player player = (Player) event.getWhoClicked();

                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)  {
                    if (inventory instanceof CraftInventory && clickedInventory instanceof CraftInventoryPlayer) {
                        boolean rare = false;
                        if (main.getRareLootConfig().hasIdenticalItem(item, "")) {
                            player.sendMessage(ChatColor.RED + "You can not add duplicates of an item to the loot table!");
                            event.setCancelled(true);
                        } else {
                            int key = main.getRareLootConfig().addItem(item, rare, "");
                            String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                            player.sendMessage(Info.main("Successfully added " + name + ChatColor.RESET + " to rareLoot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + ". (rare: " + ChatColor.RED + rare + ChatColor.RESET + ")"));
                        }
                    } else if (inventory instanceof CraftInventory && clickedInventory instanceof CraftInventory) {
                        int key = main.getRareLootConfig().removeItem(item, "");
                        String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                        player.sendMessage(Info.main("Successfully removed " + name + ChatColor.RESET + " from rareLoot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + "."));
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
