package me.kenny.main.lootbox;

import me.kenny.main.Main;
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
import org.bukkit.event.inventory.InventoryDragEvent;
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
    public void onLootMove(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            ItemStack item = event.getCurrentItem();
            Inventory inventory = event.getInventory();
            Inventory clickedInventory = event.getClickedInventory();
            Player player = (Player) event.getWhoClicked();

            if (inventory.getTitle().equalsIgnoreCase(LootTableGui.inventoryTitle)) {
                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)  {
                    if (inventory instanceof CraftInventory && clickedInventory instanceof CraftInventoryPlayer) {
                        boolean rare = false;
                        if (main.getLootConfig().hasIdenticalItem(item)) {
                            player.sendMessage(ChatColor.RED + "You can not add duplicates of an item to the loot table!");
                            event.setCancelled(true);
                        } else {
                            int key = main.getLootConfig().addLoot(item, rare);
                            String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                            player.sendMessage(Info.main(main, "Successfully added " + name + ChatColor.RESET + " to loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + ". (rare: " + ChatColor.RED + rare + ChatColor.RESET + ")"));
                        }
                    } else if (inventory instanceof CraftInventory && clickedInventory instanceof CraftInventory) {
                        int key = main.getLootConfig().removeLoot(item);
                        String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                        player.sendMessage(Info.main(main, "Successfully removed " + name + ChatColor.RESET + " from loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + "."));
                    }
                }
            } else if (inventory instanceof CraftInventoryPlayer) {
                if (event.getAction() == InventoryAction.CLONE_STACK) {

                }
            }
        }
    }

//    // gets the slot an item is in
//    public Integer getSlot(ItemStack item, Inventory inventory) {
//        for (int i = 0; i < inventory.getSize(); i++) {
//            if (inventory.getItem(i).isSimilar(item))
//                return i;
//        }
//        return -1;
//    }
}
