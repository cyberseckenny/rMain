package me.kenny.main.command;

import me.kenny.main.Main;
import me.kenny.main.crate.Crate;
import me.kenny.main.gkit.Gkit;
import me.kenny.main.gui.RareLootTableGui;
import me.kenny.main.gui.SpecialItemGui;
import me.kenny.main.gui.LootTableGui;
import me.kenny.main.util.Info;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MainCommand implements CommandExecutor {
    private Main main;

    private String lootAddGuiName = "Adding loot";
    private String lootRemoveGuiName = "Removing loot";

    public MainCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
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
                    case "givekey":
                        boolean exist = false;
                        if (args.length == 2) {
                            for (Crate crate : main.getCrateConfig().getCrates()) {
                                if (args[1].equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', crate.getText())))) {
                                    if (args.length == 2) {
                                        crate.giveKey(player);
                                        player.sendMessage(Info.crate("Gave you a " + ChatColor.translateAlternateColorCodes('&', crate.getText()) + ChatColor.RESET + " crate key!"));
                                    }

                                    else if (args.length >= 3) {
                                        Player p = Bukkit.getPlayer(args[2]);
                                        if (p != null) {
                                            crate.giveKey(p);
                                        } else {
                                            player.sendMessage(ChatColor.RED + "That player doesn't exist!");
                                        }
                                    }
                                    return true;
                                } else {
                                    exist = false;
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You must specify a crate!");
                        }
                        if (exist == false)
                            player.sendMessage(ChatColor.RED + "That crate doesn't exist!");
                        break;
                    case "givelootbox":
                        ItemStack lootbox = new ItemStack(Material.ENDER_CHEST);
                        ItemMeta meta = lootbox.getItemMeta();
                        meta.setDisplayName(main.getLootboxName());
                        meta.setLore(Arrays.asList(main.getLootboxLore()));
                        lootbox.setItemMeta(meta);
                        player.getInventory().addItem(lootbox);
                        player.sendMessage(Info.main("Gifted you a " + main.getLootboxName() + ChatColor.RESET + "."));
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

                                if (main.getLootConfig().hasIdenticalItem(item, "")) {
                                    player.sendMessage(ChatColor.RED + "You can not add duplicates of an item to the loot table!");
                                } else {
                                    int key = main.getLootConfig().addItem(item, rare, "");
                                    String name = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : ChatColor.RED + item.getType().toString();
                                    player.sendMessage(Info.main("Successfully added " + name + ChatColor.RESET + " to loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + ". (rare: " + ChatColor.RED + rare + ChatColor.RESET + ")"));
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
                                player.sendMessage(Info.main("Successfully removed " + name + ChatColor.RESET + " from loot.yml as key " + ChatColor.RED + key + ChatColor.WHITE + "."));
                            }
                        }
                        break;
                    case "viewloot":
                        player.openInventory(new LootTableGui(main).getGui());
                        break;
                    case "viewrareloot":
                        player.openInventory(new RareLootTableGui(main).getGui());
                        break;
                    case "addcrate":
                        if (args.length >= 2) {
                            Block block = player.getTargetBlock(null, 5);
                            if (block != null && block.getType() == Material.CHEST || block.getType() == Material.ENDER_CHEST) {
                                String name = getCommandString(args, 1);
                                if (!main.getCrateConfig().crateHasLocation(block.getLocation())) {
                                    if (main.getCrateConfig().addCrate(name, block.getLocation(), false)) {
                                        player.sendMessage(Info.crate("Sucessfully added " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', name) + ChatColor.RESET + ChatColor.WHITE + " crate!"));
                                    } else {
                                        player.sendMessage(ChatColor.RED + "A crate with that name already exists!");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "A crate with that location already exists!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You must be facing a chest or enderchest!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You must specify a name for the crate!");
                        }
                        break;
//                    case "addgkit":
//                        if (args.length == 1) {
//                            player.sendMessage(ChatColor.RED + "You must specify a name!");
//                        } else if (args.length == 2) {
//                            player.sendMessage(ChatColor.RED + "You must specify a kit display name!");
//                        } else {
//                            if (player.getInventory().firstEmpty() == -1) {
//                                if (isWearingFullSet(player)) {
//                                    Gkit gkit = new Gkit(args[1], args[2], player.getInventory().getContents(), player.getInventory().getArmorContents());
//                                    if (main.getGkitConfig().addGkit(gkit)) {
//                                        player.sendMessage(Info.gkit("Successfully added kit " + ChatColor.RED + gkit.getName() + ChatColor.RESET + " (" + ChatColor.translateAlternateColorCodes('&', gkit.getDisplayName()) + ChatColor.RESET + ")!"));
//                                    } else {
//                                        player.sendMessage(ChatColor.RED + "That kit already exists!");
//                                    }
//                                } else {
//                                    player.sendMessage(ChatColor.RED + "You must be wearing a full set of armor.");
//                                }
//                            } else {
//                                player.sendMessage(ChatColor.RED + "Your inventory must be full;");
//                            }
//                        }
//                        break;
                    case "setgkit":
                        if (args.length == 2) {
                            if (main.getGkitConfig().getFileConfiguration().getConfigurationSection(args[1]) != null) {
                                ConfigurationSection path = main.getGkitConfig().getFileConfiguration().getConfigurationSection(args[1]);
                                Gkit gkit = new Gkit(args[1], path.getString("description"), path.getString("displayName"), Material.valueOf(path.getString("material")), player.getInventory().getContents(), player.getInventory().getArmorContents());
                                main.getGkitConfig().setGkit(gkit);
                                player.sendMessage(Info.gkit("Set the inventory of " + ChatColor.translateAlternateColorCodes('&', gkit.getDisplayName())));
                            } else {
                                player.sendMessage(ChatColor.RED + "That gkit does not exist!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You must specify a gkit!");
                        }
                        break;
                    case "setgkitmaterial":
                        if (args.length == 2) {
                            if (main.getGkitConfig().getFileConfiguration().getConfigurationSection(args[1]) != null) {
                                if (player.getInventory().getItemInHand() != null && player.getInventory().getItemInHand().getType() != Material.AIR) {
                                    ConfigurationSection path = main.getGkitConfig().getFileConfiguration().getConfigurationSection(args[1]);
                                    Gkit gkit = new Gkit(args[1], path.getString("displayName"), path.getString("description"), player.getInventory().getItemInHand().getType(), null, null);
                                    main.getGkitConfig().setGkit(gkit);
                                    player.sendMessage(Info.gkit("Set the material of " + gkit.getDisplayName() + ChatColor.RESET + " to " + ChatColor.RED + player.getInventory().getItemInHand().getType()));
                                } else {
                                    player.sendMessage(ChatColor.RED + "You must be holding an item in your hand!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "That gkit does not exist!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You must specify a gkit!");
                        }
                        break;
                    case "setgkitdescription":
                        if (args.length > 2) {
                            if (main.getGkitConfig().getFileConfiguration().getConfigurationSection(args[1]) != null) {
                                if (args.length >= 3) {
                                    ConfigurationSection path = main.getGkitConfig().getFileConfiguration().getConfigurationSection(args[1]);
                                    String description = "";
                                    for (int i = 2; i < args.length; i++) {
                                        if (i == args.length - 1)
                                            description = description + args[i];
                                        else
                                            description = description + args[i] + " ";
                                    }
                                    Gkit gkit = new Gkit(args[1], path.getString("displayName"), description, Material.valueOf(path.getString("material")), null, null);
                                    main.getGkitConfig().setGkit(gkit);
                                    player.sendMessage(Info.gkit("Set the description of " + gkit.getDisplayName() + ChatColor.RESET + " to " + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', description)));

                                } else {
                                    player.sendMessage(ChatColor.RED + "You must input a description!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "That gkit does not exist!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You must specify a gkit!");
                        }
                        break;
                    case "setdisplayname":
                        if (args.length > 2) {
                            if (main.getGkitConfig().getFileConfiguration().getConfigurationSection(args[1]) != null) {
                                if (args.length >= 3) {
                                    ConfigurationSection path = main.getGkitConfig().getFileConfiguration().getConfigurationSection(args[1]);
                                    String displayName = "";
                                    for (int i = 2; i < args.length; i++) {
                                        if (i == args.length - 1)
                                            displayName = displayName + args[i];
                                        else
                                            displayName = displayName + args[i] + " ";
                                    }
                                    Gkit gkit = new Gkit(args[1], displayName, path.getString("description"), Material.valueOf(path.getString("material")), null, null);
                                    main.getGkitConfig().setGkit(gkit);
                                    player.sendMessage(Info.gkit("Set the display name of " + gkit.getName() + ChatColor.RESET + " to " + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', displayName)));

                                } else {
                                    player.sendMessage(ChatColor.RED + "You must input a display name!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "That gkit does not exist!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You must specify a gkit!");
                        }
                        break;
                    case "listpermissions":
                        player.sendMessage(Info.gkit("Listing permissions..."));
                        for (Gkit gkit : main.getGkitConfig().getGkits()) {
                            player.sendMessage("gkit." + gkit.getName() + " ");
                        }
                        break;
                    case "reload":
                        main.setupConfig();
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
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lMain Commands"));
        player.sendMessage(" ");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main viewItems &eViews the special items."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main giveKey <crate> <player> &eGives a player a key, if no player is specified it will give you the key."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main giveLootbox &eGives you a lootbox."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main addLoot <true/false> &eAdds your current held item to the loot table. If true, the item will be considered rare and if used in lootboxes it will only appear in the ender chest."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main removeLoot &eRemoves your current held item from the loot table. NOTE: The item must be identical to the item in the loot table."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main viewLoot &eViews the current loot table. You can also add and remove items from the loot table by dragging and dropping items from the gui."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main viewRareLoot &eViews the current rare loot table."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main addCrate <name> &eCreates a new crate at the chest you are facing."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main editCrate &eModifies the crate you are currently facing."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main removeCrate &eRemoves the crate you are currently facing."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main reload &eReloads plugin config files."));
//      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main addGkit <name> <displayName> &eAdds a new Gkit with your current armor and item contents. Name is what will be used when editing the gkit, and display name is the text that will be presented in the gui."));
//      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main removeGkit <name> &eRemoves a gkit."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main setGkit <name> &eSets the item and armor contents of the gkit to you inventory."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main setGkitMaterial <name> &eSets your in-hand item to the item presented in the gkit gui."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main setGkitDescription <name> &eSets the description of the gkit presented in the gui."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main setGkitDisplayName <name> <displayName> &eSets the display name of a gkit."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/main listPermissions &eLists all gkit permissions."));
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
