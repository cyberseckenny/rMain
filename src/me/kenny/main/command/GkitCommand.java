package me.kenny.main.command;

import me.kenny.main.Main;
import me.kenny.main.gui.GkitGui;
import me.kenny.main.gui.GkitMenuGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GkitCommand implements CommandExecutor {
    private Main main;

    public GkitCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(new GkitMenuGui(main, player).getGui());
        }
        return true;
    }
}
