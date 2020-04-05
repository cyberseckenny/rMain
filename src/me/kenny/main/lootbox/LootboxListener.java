package me.kenny.main.lootbox;

import me.kenny.main.Main;
import me.kenny.main.gui.LootboxGui;
import me.kenny.main.util.Info;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class LootboxListener implements Listener {
    private Main main;
    private Map<Player, LootboxStorage> lootboxStorage = new HashMap<>();

    public LootboxListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onLootboxOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack clicked = event.getItem();
            if (clicked != null && clicked.getItemMeta() != null && isLootbox(clicked.getItemMeta())) {
                if (main.getLootConfig().getItems().size() < 10) {
                    if (player.hasPermission("main.modify"))
                        player.sendMessage(ChatColor.RED + "You must add at least 10 items to the loot table for lootboxes to function.");
                    event.setCancelled(true);
                    return;
                }

                player.openInventory(new LootboxGui(main).getGui());
                removeOne(player, clicked);
                player.sendMessage(Info.lootboxes(main, "Opening lootbox."));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLootboxOpen(InventoryOpenEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(main.getLootboxName())) {
            Player player = (Player) event.getPlayer();
            lootboxStorage.put(player, new LootboxStorage(getRandomLoot(10)));
        }
    }

    @EventHandler
    public void onLootboxClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (lootboxStorage.containsKey(player))
            lootboxStorage.remove(player);
    }

    @EventHandler
    public void onLootboxGuiClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getTitle().equals(main.getLootboxName()))
            event.setCancelled(true);

        if (event.getCurrentItem() != null) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked.getItemMeta() != null) {
                LootboxStorage storage = lootboxStorage.get(player);
                ItemStack item;
                if (clicked.getType() == Material.CHEST) {
                    item = storage.getNextItemStack();
                    addItem(player, event.getInventory(), item, event.getSlot());
                } else if (clicked.getType() == Material.ENDER_CHEST) {
                    if (storage.isMaxed()) {
                        item = storage.getRareItemStack();
                        addItem(player, event.getInventory(), item, event.getSlot());
                        Bukkit.getScheduler().runTaskLater(main, new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.getOpenInventory().close();
                            }
                        }, 40L);
                    } else {
                        player.sendMessage(ChatColor.RED + "You must open all other chests first!");
                    }
                }
            }
        }
    }

    public void addItem(Player player, Inventory inventory, ItemStack item, int slot) {
        String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + String.valueOf(item.getType());
        inventory.setItem(slot, item);
        player.sendMessage(Info.lootboxes(main, "Received " + ChatColor.RED + item.getAmount() + "x " + name + ChatColor.RESET + "."));
    }

    // only removes one from an
    public void removeOne(Player player, ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().removeItem(item);
        }
    }

    public ItemStack[] getRandomLoot(int amount) {
        List<ItemStack> loot = new ArrayList<>();
        loot.addAll(main.getLootConfig().getItems().keySet());
        Collections.shuffle(loot);
        ItemStack[] items = new ItemStack[amount];
        for (int i = 0; i < amount; i++)
            items[i] = loot.get(i);
        return items;
    }

    public boolean isLootbox(ItemMeta meta) {
        if (meta.getLore() != null && !meta.getLore().isEmpty()) {
            if (meta.getDisplayName().equals(main.getLootboxName())) {
                if (meta.getLore().get(0).equals(main.getLootboxLore())) {
                    return true;
                }
            }
        }
        return false;
    }
}
