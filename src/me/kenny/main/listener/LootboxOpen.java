package me.kenny.main.listener;

import me.kenny.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LootboxOpen implements Listener {
    @EventHandler
    public void onLootboxOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack clicked = event.getItem();

            if (clicked.getItemMeta() != null && clicked.getItemMeta().getLore() != null && !clicked.getItemMeta().getLore().isEmpty()) {
                ItemMeta meta = clicked.getItemMeta();
                if (meta.getDisplayName().equals(Main.getInstance().getLootboxName())) {
                    if (meta.getLore().get(0).equals(Main.getInstance().getLootboxLore())) {

                    }
                }
            }
        }
    }
}
