package me.kenny.main.lootbox;

import me.kenny.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class LootboxLootConstructor implements Listener {
    private Main main;

    public LootboxLootConstructor(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onLootboxGuiClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(main.getLootboxName()))
            event.setCancelled(true);

        if (event.getCurrentItem() != null) {
            ItemStack clicked = event.getCurrentItem();
            if (main.isLootbox(clicked.getItemMeta())) {

            }
        }
    }
}
