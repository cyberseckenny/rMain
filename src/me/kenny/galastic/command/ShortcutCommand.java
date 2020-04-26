package me.kenny.galastic.command;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.util.Info;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class ShortcutCommand implements CommandExecutor {
    private Galastic galastic;

    public ShortcutCommand(Galastic galastic) {
        this.galastic = galastic;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length >= 2) {
                        if (args.length >= 3) {
                            String commandString = getCommandString(args, 2);
                            player.sendMessage(Info.shortcut("Set shortcut " + ChatColor.GOLD + args[1] + ChatColor.GRAY + " to " + ChatColor.GOLD + commandString + ChatColor.GRAY + "."));
                            galastic.getShortcutConfig().setShortcut(args[1], commandString);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You must include a command to set this shortcut to!");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    for (Map.Entry<String, String> entry : galastic.getShortcutConfig().getShortcuts().entrySet()) {
                        if (entry.getKey().equalsIgnoreCase(args[1])) {
                            if (galastic.getShortcutConfig().removeShortcut(entry.getKey())) {
                                player.sendMessage(Info.shortcut("Removed shortcut " + ChatColor.GOLD + entry.getKey() + ChatColor.GRAY + "."));
                            } else {
                                player.sendMessage(ChatColor.RED + "That shortcut does not exist! Do '/sc list' to check existing shortcuts.");
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    if (galastic.getShortcutConfig().getShortcuts().size() != 0) {
                        player.sendMessage(Info.shortcut("Listing shortcuts..."));
                        for (Map.Entry<String, String> entry : galastic.getShortcutConfig().getShortcuts().entrySet()) {
                            player.sendMessage(ChatColor.GRAY + entry.getKey() + ": " + ChatColor.GOLD + entry.getValue());
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "There are no existing shortcuts! Do '/sc set' to set a shortcut!");
                    }
                } else {
                    for (Map.Entry<String, String> entry : galastic.getShortcutConfig().getShortcuts().entrySet()) {
                        if (entry.getKey().equalsIgnoreCase(args[0])) {
                            galastic.getShortcutConfig().doShortcut(player, entry.getKey());
                            player.sendMessage(Info.shortcut("Used shortcut " + ChatColor.GOLD + entry.getKey() + ChatColor.GRAY + "."));
                        }
                    }
                }
            } else {
                help(player);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
        }
        return true;
    }

    public void help(Player player) {
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 40));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lShortcut Commands"));
        player.sendMessage(" ");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/sc set <shortcut> <command> &7Sets a shortcut for a command"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/sc remove <shortcut> &7Removes a shortcut."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/sc list &7Lists all shortcuts."));
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 40));
    }

    public String getCommandString(String[] args, int start) {
        String string = "";
        for (int i = start; i < args.length; i++) {
            string = string + args[i] + " ";
        }
        return string;
    }
}
