package me.kenny.main.command;

import me.kenny.main.Main;
import me.kenny.main.gui.SpecialItemGui;
import me.kenny.main.gui.LootTableGui;
import me.kenny.main.util.Info;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MainCommand implements CommandExecutor  {
    private Main main;

    private String lootAddGuiName = "Adding loot";
    private String lootRemoveGuiName = "Removing loot";

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
                    case "viewitems":
                        player.openInventory(new SpecialItemGui(main).getGui());
                        break;
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
                        } else {
                            if (args.length == 1 || !isBoolean(args[1])) {
                                player.sendMessage(ChatColor.RED + "You must specify if the item is rare (true or false)!");
                            } else {
                                boolean rare = Boolean.parseBoolean(args[1]);
                                ItemStack item = player.getInventory().getItemInHand();

                                if (main.getLootConfig().hasIdenticalItem(item)) {
                                    player.sendMessage(ChatColor.RED + "You can not add duplicates of an item to the loot table!");
                                } else {
                                    int key = main.getLootConfig().addItem(item, rare, "");
                                    String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                                    player.sendMessage(Info.main(main, "Successfully added " + name + ChatColor.RESET + " to loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + ". (rare: " + ChatColor.RED + rare + ChatColor.RESET + ")"));
                                }
                            }
                        }
                        break;
                    case "removeloot":
                        if (player.getInventory().getItemInHand() == null || player.getInventory().getItemInHand().getType() == Material.AIR) {
                            player.sendMessage(ChatColor.RED + "You must be holding an item in your hand to add loot!");
                            return true;
                        } else {
                            ItemStack item = player.getInventory().getItemInHand();
                            int key = main.getLootConfig().removeItem(item, "");
                            if (key == -1) {
                                player.sendMessage(ChatColor.RED + "The item in your hand is not in the loot table!");
                            } else {
                                String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                                player.sendMessage(Info.main(main, "Successfully removed " + name + ChatColor.RESET + " from loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + "."));
                            }
                        }
                        break;
                    case "viewloot":
                        player.openInventory(new LootTableGui(main).getGui());
                        break;
                    case "reload":
                        main.setupConfig();
                        player.sendMessage(Info.main(main, "Reloaded configs."));
                        break;
                    default:
                        help(player);
                        break;
                }
            }
        }
        return true;
    }

    public void openLootGui(Player player, String title) {
        Inventory inventory = Bukkit.createInventory(null, 36, title);
        player.openInventory(inventory);
    }

    // checks if the string equals true or false
    public boolean isBoolean(String string) {
        if (string.equals("true") || string.equalsIgnoreCase("false"))
            return true;
        return false;
    }

    public void help(Player player) {
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 40));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lMain Commands"));
        player.sendMessage(" ");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main viewItems &eViews the special items."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main giveLootbox &eGives you a lootbox."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main addLoot <true/false> &eAdds your current held item to the loot table. If true, the item will be considered rare and if used in lootboxes it will only appear in the ender chest."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main removeLoot &eRemoves your current held item from the loot table. NOTE: The item must be identical to the item in the loot table."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main viewLoot &eViews the current loot table. You can also add and remove items from the loot table by dragging and dropping items from the gui."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main reload &eReloads plugin config files."));
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 40));
    }
}
