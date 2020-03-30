package me.kenny.main.lootbox;

import me.kenny.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LootboxOpen implements Listener {
    private Main main;

    public LootboxOpen(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onLootboxOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack clicked = event.getItem();

            if (clicked.getItemMeta() != null && clicked.getItemMeta().getLore() != null && !clicked.getItemMeta().getLore().isEmpty()) {
                ItemMeta meta = clicked.getItemMeta();
                if (meta.getDisplayName().equals(main.getLootboxName())) {
                    if (meta.getLore().get(0).equals(main.getLootboxLore())) {
                        new LootboxGUI(player);
                        player.getInventory().removeItem(clicked);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
