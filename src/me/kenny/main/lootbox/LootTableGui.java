package me.kenny.main.lootbox;

import me.kenny.main.Main;
import me.kenny.main.util.DoubleValue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LootTableGui {
    private Main main;
    public final static String inventoryTitle = "Viewing loot table";

    public LootTableGui(Main main, Player player) {
        this.main = main;
    }

    public Inventory getGui() {
        Inventory inventory = Bukkit.createInventory(null, 54, inventoryTitle);
        for (Map.Entry<ItemStack, DoubleValue> loot : main.getLootConfig().getLoot().entrySet()) {
            // inventories start 0, keys start at 1
            int key = loot.getValue().getInteger() - 1;
            boolean rare = loot.getValue().getBoolean();
            ItemStack item = loot.getKey();
//            ItemMeta meta = item.getItemMeta();
//
//            List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();
//            String rareString = rare == true ? ChatColor.GREEN + "" + rare : ChatColor.RED + "" + rare;
//            lore.add(ChatColor.AQUA + "rare: " + rareString);
//            meta.setLore(lore);
//            item.setItemMeta(meta);

            // TODO: rarity

            inventory.setItem(key, item);
        }
        return inventory;
    }
}
