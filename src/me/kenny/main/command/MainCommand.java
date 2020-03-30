package me.kenny.main.command;

import me.kenny.main.Main;
import me.kenny.main.util.Info;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MainCommand implements CommandExecutor  {
    private Main main;

    public MainCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can run this command!");
            return true;
        }

        Player player = (Player) commandSender;

        if (player.hasPermission("main.modify")) {
            if (args.length == 0) {
                help(player);
            } else {
                switch (args[0].toLowerCase()) {
                    case "givelootbox":
                        ItemStack lootbox = new ItemStack(Material.ENDER_CHEST);
                        ItemMeta meta = lootbox.getItemMeta();
                        meta.setDisplayName(main.getLootboxName());
                        meta.setLore(Arrays.asList(main.getLootboxLore()));
                        lootbox.setItemMeta(meta);

                        player.getInventory().addItem(lootbox);
                        player.sendMessage(Info.main(main, "Gifted you a " + main.getLootboxName() + ChatColor.RESET + "."));
                        break;
                    case "addloot":
                        if (player.getInventory().getItemInHand() == null || player.getInventory().getItemInHand().getType() == Material.AIR) {
                            player.sendMessage(ChatColor.RED + "You must be holding an item in your hand to add loot!");
                            return true;
                        } else {
                            ItemStack item = player.getInventory().getItemInHand();
                            int key = main.getLootConfig().addLoot(item);
                            String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                            player.sendMessage(Info.main(main, "Sucessfully added " + name + ChatColor.RESET + " to loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + "."));
                            break;
                        }
                    default:
                        help(player);
                        break;
                }
            }
        }
        return true;
    }

    public void help(Player player) {
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 40));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lMain Commands"));
        player.sendMessage(" ");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main giveLootbox &eGives you a lootbox."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main addLoot <true/false> &eAdds your current held item to the loot table. If true, the item will be considered rare and will only appear in the ender chest when used in lootboxes."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main removeLoot &eRemoves your current held item from the loot table. NOTE: The item must be identical to the item in the loot table."));
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 40));
    }
}
