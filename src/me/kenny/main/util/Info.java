package me.kenny.main.util;

import me.kenny.main.Main;
import org.bukkit.ChatColor;

public class Info {
    public static String lootboxes(Main main, String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Lootboxes" + ChatColor.RESET + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String crate(Main main, String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Crates" + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String main(Main main, String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Main" + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String gkit(Main main, String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Gkits" + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }
}
