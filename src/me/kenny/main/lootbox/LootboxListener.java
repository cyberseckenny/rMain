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
                if (main.getLootConfig().getItems("").size() < 10) {
                    player.sendMessage(ChatColor.RED + "You must add at least 10 items to the loot table for lootboxes to function.");
                    event.setCancelled(true);
                    return;
                }

                if (main.getRareLootConfig().getItems("").size() < 1) {
                    player.sendMessage(ChatColor.RED + "You must have at least 1 rare item in the loot table for lootboxes to function!");
                    event.setCancelled(true);
                    return;
                }

                player.openInventory(new LootboxGui(main).getGui());
                removeOne(player, clicked);
                player.sendMessage(Info.lootboxes("Opening lootbox."));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLootboxOpen(InventoryOpenEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(main.getLootboxName())) {
            Player player = (Player) event.getPlayer();
            Map<ItemStack[], ItemStack> map = getRandomLoot(10);
            ItemStack rare = map.values().iterator().next();
            lootboxStorage.put(player, new LootboxStorage(rare, map.keySet().iterator().next()));
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
                        item = storage.getRare();
                        addItem(player, event.getInventory(), item, event.getSlot());
                        Bukkit.getScheduler().runTaskLater(main, new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (ItemStack item : storage.getItems()) {
                                    giveItem(player, item);
                                }
                                giveItem(player, storage.getRare());
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
        player.sendMessage(Info.lootboxes("Received " + ChatColor.RED + item.getAmount() + "x " + name + ChatColor.RESET + "."));
    }

    // only removes one from an
    public void removeOne(Player player, ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().removeItem(item);
        }
    }

    public Map<ItemStack[], ItemStack> getRandomLoot(int amount) {
        List<ItemStack> loot = new ArrayList<>(main.getLootConfig().getItems("").keySet());
        List<ItemStack> rare = new ArrayList<>(main.getRareLootConfig().getItems("").keySet());
        Collections.shuffle(loot);
        Collections.shuffle(rare);
        ItemStack[] items = new ItemStack[amount];
        for (int i = 0; i < amount; i++)
            items[i] = loot.get(i);

        Map<ItemStack[], ItemStack> map = new HashMap<>();
        map.put(items, rare.get(0));
        return map;
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

    public void giveItem(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }
}
