package me.kenny.galastic.command;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.gui.RareLootTableGui;
import me.kenny.galastic.gui.SpecialItemGui;
import me.kenny.galastic.gui.LootTableGui;
import me.kenny.galastic.util.Info;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GalasticCommand implements CommandExecutor {
    private Galastic galastic;

    private String lootAddGuiName = "Adding loot";
    private String lootRemoveGuiName = "Removing loot";

    public GalasticCommand(Galastic galastic) {
        this.galastic = galastic;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
            return true;
        }

        Player player = (Player) commandSender;

        if (player.hasPermission("galastic.main")) {
            if (args.length == 0) {
                help(player);
            } else {
                switch (args[0].toLowerCase()) {
                    case "viewitems":
                        player.openInventory(new SpecialItemGui(galastic).getGui());
                        break;
//                    case "givelootbox":
//                        ItemStack lootbox = new ItemStack(Material.ENDER_CHEST);
//                        ItemMeta meta = lootbox.getItemMeta();
//                        meta.setDisplayName(main.getLootboxName());
//                        meta.setLore(Arrays.asList(main.getLootboxLore()));
//                        lootbox.setItemMeta(meta);
//                        player.getInventory().addItem(lootbox);
//                        player.sendMessage(Info.main("Gifted you a " + main.getLootboxName() + ChatColor.RESET + "."));
//                        break;
//                    case "addloot":
//                        if (player.getInventory().getItemInHand() == null || player.getInventory().getItemInHand().getType() == Material.AIR) {
//                            player.sendMessage(ChatColor.RED + "You must be holding an item in your hand to add loot!");
//                        } else {
//                            if (args.length == 1 || !isBoolean(args[1])) {
//                                player.sendMessage(ChatColor.RED + "You must specify if the item is rare (true or false)!");
//                            } else {
//                                boolean rare = Boolean.parseBoolean(args[1]);
//                                ItemStack item = player.getInventory().getItemInHand();
//
//                                if (galastic.getLootConfig().hasIdenticalItem(item, "")) {
//                                    player.sendMessage(ChatColor.RED + "You can not add duplicates of an item to the loot table!");
//                                } else {
//                                    int key = galastic.getLootConfig().addItem(item, rare, "");
//                                    String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
//                                    player.sendMessage(Info.main("Successfully added " + name + ChatColor.RESET + " to loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + ". (rare: " + ChatColor.RED + rare + ChatColor.RESET + ")"));
//                                }
//                            }
//                        }
//                        break;
//                    case "removeloot":
//                        if (player.getInventory().getItemInHand() == null || player.getInventory().getItemInHand().getType() == Material.AIR) {
//                            player.sendMessage(ChatColor.RED + "You must be holding an item in your hand to add loot!");
//                            return true;
//                        } else {
//                            ItemStack item = player.getInventory().getItemInHand();
//                            int key = galastic.getLootConfig().removeItem(item, "");
//                            if (key == -1) {
//                                player.sendMessage(ChatColor.RED + "The item in your hand is not in the loot table!");
//                            } else {
//                                String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
//                                player.sendMessage(Info.main("Successfully removed " + name + ChatColor.RESET + " from loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + "."));
//                            }
//                        }
//                        break;
//                    case "viewloot":
//                        player.openInventory(new LootTableGui(galastic).getGui());
//                        break;
//                    case "viewrareloot":
//                        player.openInventory(new RareLootTableGui(galastic).getGui());
//                        break;
                    case "reload":
                        galastic.setupConfig();
                        player.sendMessage(Info.main("Reloaded configs."));
                        break;
                    default:
                        help(player);
                        break;
                }
            }
        }
        return true;
    }

    // checks if the string equals true or false
    public boolean isBoolean(String string) {
        if (string.equals("true") || string.equals("false"))
            return true;
        return false;
    }

    public void help(Player player) {
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 40));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lGalastic Commands"));
        player.sendMessage(" ");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/galastic viewItems &eViews the special items."));
//        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/galastic giveLootbox &eGives you a lootbox."));
//        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/galastic addLoot <true/false> &eAdds your current held item to the loot table. If true, the item will be considered rare and if used in lootboxes it will only appear in the ender chest."));
//        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/galastic removeLoot &eRemoves your current held item from the loot table. NOTE: The item must be identical to the item in the loot table."));
//        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/galastic viewLoot &eViews the current loot table. You can also add and remove items from the loot table by dragging and dropping items from the gui."));
//        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/galastic viewRareLoot &eViews the current rare loot table."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/galastic reload &eReloads plugin config files."));
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 40));
    }

    public boolean isWearingFullSet(Player player) {
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item == null)
                return false;
        }
        return true;
    }


    public String getCommandString(String[] args, int start) {
        String string = "";
        for (int i = start; i < args.length; i++) {
            if (i == args.length - 1)
                string = string + args[i];
            else
                string = string + args[i] + " ";
        }
        return string;
    }
}
