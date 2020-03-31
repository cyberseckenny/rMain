package me.kenny.main.lootbox;

import me.kenny.main.Main;
import me.kenny.main.util.Info;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LootboxListener implements Listener {
    private Main main;

    public LootboxListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onLootboxOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack clicked = event.getItem();

            if (clicked != null && clicked.getItemMeta() != null && main.isLootbox(clicked.getItemMeta())) {
                new LootboxGui(main, player);
                removeOne(player, clicked);
                player.sendMessage(Info.lootboxes(main, "Opening lootbox."));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLootboxGuiClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(main.getLootboxName()))
            event.setCancelled(true);

        if (event.getCurrentItem() != null) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked.getItemMeta() != null && main.isLootbox(clicked.getItemMeta())) {

            }
        }
    }

    // only removes one from an
    public void removeOne(Player player, ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().removeItem(item);
        }
    }
}
