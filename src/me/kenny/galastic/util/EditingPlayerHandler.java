package me.kenny.galastic.util;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.gui.LootTableGui;
import me.kenny.galastic.gui.RareLootTableGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// stores who is editing in a gui
public class EditingPlayerHandler implements Listener {
    private Map<EditingType, List<Player>> editing = new HashMap<>();
    private Galastic galastic;

    public EditingPlayerHandler(Galastic galastic) {
        for (EditingType type : EditingType.values()) {
            editing.put(type, new ArrayList<Player>());
        }
    }

    public List<Player> getEditing(EditingType editingType) {
        return editing.get(editingType);
    }

    public boolean isEditing(Player player, EditingType editingType) {
        for (Player p : editing.get(editingType)) {
            if (player == p)
                return true;
        }
        return false;
    }

    public enum EditingType {
        LOOT_TABLE_GUI(LootTableGui.inventoryTitle),
        RARE_LOOT_TABLE_GUI(RareLootTableGui.inventoryTitle);

        private String inventoryTitle;

        EditingType(String inventoryTitle) {
            this.inventoryTitle = inventoryTitle;
        }

        public String getInventoryTitle() {
            return inventoryTitle;
        }
    }

    // removes players from their editing list if they close its specific inventory
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        for (EditingType editingType : EditingType.values()) {
            if (editingType.getInventoryTitle().endsWith(inventory.getName())) {
                Player player = (Player) event.getPlayer();
                if (editing.get(editingType).contains(player)) {
                    editing.get(editingType).remove(player);
                }
            }
        }
    }

    // adds players to their editing list if they open its specific inventory
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        for (EditingType editingType : EditingType.values()) {
            if (editingType.getInventoryTitle().endsWith(inventory.getName())) {
                Player player = (Player) event.getPlayer();
                editing.get(editingType).add(player);
            }
        }
    }
}

