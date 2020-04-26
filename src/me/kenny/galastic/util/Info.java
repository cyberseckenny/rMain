package me.kenny.galastic.util;

import org.bukkit.ChatColor;

public class Info {
    public static String lootboxes(String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Lootboxes" + ChatColor.RESET + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String main(String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Main" + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String shortcut(String message) {
        return ChatColor.GRAY + "[" + ChatColor.GOLD + "Shortcuts" + ChatColor.GRAY + "] " + ChatColor.GRAY + message;
    }

}
