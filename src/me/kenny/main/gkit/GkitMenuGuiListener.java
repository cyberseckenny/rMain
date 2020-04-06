package me.kenny.main.gkit;

import me.kenny.main.Main;
import me.kenny.main.gui.GkitMenuGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
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
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (opened.contains(player))
            opened.remove(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(GkitMenuGui.title))
            event.setCancelled(true);
    }

}
