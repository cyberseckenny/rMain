package me.kenny.main.util;

import me.kenny.main.Main;
import org.bukkit.ChatColor;

public class Info {
    public static String lootboxes(String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Lootboxes" + ChatColor.RESET + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String crate(String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Crates" + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String main(String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Main" + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String gkit(String message) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Gkits" + ChatColor.GRAY + "] " + ChatColor.RESET + message;
    }

    public static String shortcut(String message) {
        return ChatColor.GRAY + "[" + ChatColor.GOLD + "Shortcuts" + ChatColor.GRAY + "] " + ChatColor.GRAY + message;
    }

}
