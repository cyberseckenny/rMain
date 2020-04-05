package me.kenny.main.gkit;

import me.kenny.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
}
