package me.kenny.main.gui;

import me.kenny.main.Main;
import me.kenny.main.gkit.Gkit;
import me.kenny.main.util.InventoryUtils;
import me.kenny.main.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GkitMenuGui implements Gui {
    private Main main;
    private Player player;
    public static String title = ChatColor.RED + "Gkits";

    public GkitMenuGui(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    @Override
    public Inventory getGui() {
        int[] center = {13, 21, 22, 23, 29, 30, 31, 32, 33, 39, 40, 41};
        Inventory inventory = Bukkit.createInventory(null, 54, title);
        InventoryUtils.setBorder(inventory, 54, center);

        for (Gkit gkit : main.getGkitConfig().getGkits()) {
            ItemStack helmet = new ItemStack(gkit.getMaterial());
            ItemMeta meta = helmet.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', gkit.getDisplayName()));

            int cooldown;
            if (main.getCooldownConfig().getCooldown(player, "gkit." + gkit.getName()) != null) {
                cooldown = (int) TimeUnit.MILLISECONDS.toSeconds(main.getCooldownConfig().getCooldown(player, "gkit." + gkit.getName()) - System.currentTimeMillis());
            } else {
                cooldown = 24 * 60 * 60;
            }

            meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', gkit.getDescription()), " ", ChatColor.GRAY + "Purchase at: " + ChatColor.RED + "store.hcruins.net", " ", ChatColor.GRAY + "Cooldown: " + TimeUtils.getFormattedTimeWithoutZeroes(cooldown) + ""));
            helmet.setItemMeta(meta);
            inventory.addItem(helmet);
        }

        return inventory;
    }
}
