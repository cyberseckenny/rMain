package me.kenny.main.crate;

import me.kenny.main.Main;
import me.kenny.main.gui.CrateGui;
import me.kenny.main.gui.LootTableGui;
import me.kenny.main.util.Info;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CrateListener implements Listener {
    private Main main;

    public CrateListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        for (Crate crate : main.getCrateConfig().getCrates()) {
            Location l1 = crate.getLocation();
            Location l2 = event.getBlock().getLocation();
            if (l1.getBlockX() == l2.getBlockX() &&
                    l1.getBlockY() == l2.getBlockY() &&
                    l1.getBlockZ() == l2.getBlockZ() ) {
                Player player = event.getPlayer();
                if (player.hasPermission("main.modify")) {
                    event.getPlayer().openInventory(new CrateGui(main, crate.getText()).getGui());
                    event.setCancelled(true);
                } else {
                    System.out.println(crate.getItems());
                    Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', crate.getText()));
                    for (ItemStack item : crate.getItems()) {
                        inventory.addItem(item);
                    }
                    event.getPlayer().openInventory(inventory);
                }
            }
        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            for (Crate crate : main.getCrateConfig().getCrates()) {
                Location l1 = crate.getLocation();
                Location l2 = event.getClickedBlock().getLocation();
                if (l1.getBlockX() == l2.getBlockX() &&
                        l1.getBlockY() == l2.getBlockY() &&
                        l1.getBlockZ() == l2.getBlockZ()) {
                    Player player = event.getPlayer();
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onLootMove(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            Inventory inventory = event.getInventory();
            if (inventory.getTitle().endsWith(" (currently editing)")) {
                ItemStack item = event.getCurrentItem();
                Inventory clickedInventory = event.getClickedInventory();
                Player player = (Player) event.getWhoClicked();

                Crate crate = null;
                for (Crate c : main.getCrateConfig().getCrates()) {
                    if (ChatColor.translateAlternateColorCodes('&', c.getText()).equals(inventory.getTitle().replace(" (currently editing)", "")))
                        crate = c;
                }

                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    if (inventory instanceof CraftInventory && clickedInventory instanceof CraftInventoryPlayer) {
                        boolean rare = false;
                        if (main.getCrateConfig().hasIdenticalItem(item, crate.getText() + ".items")) {
                            player.sendMessage(ChatColor.RED + "You can not add duplicates of an item to the crate!");
                            event.setCancelled(true);
                        } else {
                            int key = main.getCrateConfig().addItem(item, rare, crate.getText() + ".items");
                            String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                            player.sendMessage(Info.main("Successfully added " + name + ChatColor.RESET + " to crates.yml as key " + ChatColor.RED + key + ChatColor.WHITE + ". (rare: " + ChatColor.RED + rare + ChatColor.RESET + ")"));
                        }
                    } else if (inventory instanceof CraftInventory && clickedInventory instanceof CraftInventory) {
                        int key = main.getCrateConfig().removeItem(item, crate.getText() + ".items");
                        String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                        player.sendMessage(Info.main("Successfully removed " + name + ChatColor.RESET + " from crates.yml as key " + ChatColor.RED + key + ChatColor.WHITE + "."));
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
