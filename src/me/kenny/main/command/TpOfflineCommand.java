package me.kenny.main.command;

import me.kenny.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpOfflineCommand implements CommandExecutor {
    private Main main;

    public TpOfflineCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You must specify a player!");
            } else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                Location location = main.getPlayerLocationConfig().getLocation(offlinePlayer);
                if (location != null) {
                    player.sendMessage(ChatColor.GREEN + "Teleporting you to " + offlinePlayer.getName() + "!");
                    player.teleport(location);
                } else {
                    player.sendMessage(ChatColor.RED + args[0] + " has never logged onto the server before!");
                }
            }
        }
        return true;
    }
}
