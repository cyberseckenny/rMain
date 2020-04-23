package me.kenny.main.gkit;

import me.kenny.main.Main;
import me.kenny.main.gui.GkitGui;
import me.kenny.main.gui.GkitMenuGui;
import me.kenny.main.util.Info;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GkitMenuGuiListener implements Listener {
    private List<Player> opened = new ArrayList<>();
    private Main main;

    public GkitMenuGuiListener(Main main) {
        this.main = main;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : opened) {
                    player.getOpenInventory().getTopInventory().setContents(new GkitMenuGui(main, player).getGui().getContents());
                }
            }
        }, 0L, 20L);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (!opened.contains(event.getPlayer()) && event.getInventory().getTitle().equals(GkitMenuGui.title))
            opened.add(player);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (opened.contains(player))
            opened.remove(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(GkitMenuGui.title))
            event.setCancelled(true);

        for (Gkit gkit : main.getGkitConfig().getGkits()) {
            if (event.getInventory().getTitle().equals(gkit.getDisplayName()))
                event.setCancelled(true);
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        if (clicked != null && clicked.getType() != Material.AIR && clicked.getItemMeta() != null && clicked.getItemMeta().getDisplayName() != null) {
            Gkit gkit = getGkitFromDisplayName(clicked.getItemMeta().getDisplayName());
            if (gkit != null) {
                if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.DOUBLE_CLICK) {
                    if (!main.getCooldownConfig().isOnCooldown(player, "gkit." + gkit.getName())) {
                        if (player.hasPermission("gkit." + gkit.getName())) ;
                        player.sendMessage(Info.gkit("Used gkit " + ChatColor.translateAlternateColorCodes('&', gkit.getDisplayName()) + ChatColor.RESET + "!"));
                        main.getCooldownConfig().addCooldown(player, "gkit." + gkit.getName(), 24 * 60 * 60);
                        gkit.giveGkit(player);
                    } else {
                        player.sendMessage(gkit.getDisplayName() + ChatColor.RESET + ChatColor.RED + " is currently on cooldown!");
                    }
                } else {
                    opened.remove(player);
                    player.closeInventory();
                    player.openInventory(new GkitGui(main, gkit).getGui());
                }
            }
        }
    }

    public Gkit getGkitFromDisplayName(String displayName) {
        for (Gkit gkit : main.getGkitConfig().getGkits()) {
            if (ChatColor.translateAlternateColorCodes('&', gkit.getDisplayName()).equals(displayName)) {
                return gkit;
            }
        }
        return null;
    }
}
