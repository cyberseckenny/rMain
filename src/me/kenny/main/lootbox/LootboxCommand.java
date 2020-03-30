package me.kenny.main.lootbox;

import me.kenny.main.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LootboxCommand implements CommandExecutor {
    private Main main;

    public LootboxCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ItemStack lootbox = new ItemStack(Material.ENDER_CHEST);
            ItemMeta meta = lootbox.getItemMeta();
            meta.setDisplayName(main.getLootboxName());
            meta.setLore(Arrays.asList(main.getLootboxLore()));
            lootbox.setItemMeta(meta);

            Player player = (Player) sender;
            player.getInventory().addItem(lootbox);
        }
        return true;
    }
}
