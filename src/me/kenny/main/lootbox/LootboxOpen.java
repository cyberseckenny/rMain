package me.kenny.main.lootbox;

import me.kenny.main.Main;
import org.bukkit.ChatColor;
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

            if (main.isLootbox(clicked.getItemMeta())) {
                new LootboxGUI(main, player);
                player.getInventory().removeItem(clicked);
                player.sendMessage(ChatColor.GRAY + "[" + main.getLootboxName() + ChatColor.GRAY + "]" + ChatColor.WHITE + " Opening lootbox.");
                event.setCancelled(true);
            }
        }
    }
}
