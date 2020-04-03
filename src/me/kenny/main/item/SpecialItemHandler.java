package me.kenny.main.item;

import me.kenny.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecialItemHandler implements Listener {
    private Main main;
    private List<SpecialItem> specialItems = new ArrayList<>();

    public SpecialItemHandler(Main main) {
        this.main = main;

        for (String path : main.getConfig().getKeys(false)) {
            // checks if the path is actually an item
            if (path.equalsIgnoreCase("items")) {
                ConfigurationSection configuration = main.getConfig().getConfigurationSection(path);
                for (String section : configuration.getKeys(false)) {
                    String name = configuration.getString(section + ".name");
                    List<String> description = configuration.getStringList(section + ".description");
                    System.out.println(configuration.getStringList(section + ".description"));
                    int cooldown = configuration.getInt(section + ".cooldown");
                    int uses = configuration.getInt(section + ".uses");
                    Material material = Material.getMaterial(configuration.getString(section + ".material"));
                    SpecialItem specialItem = new SpecialItem(name, description, cooldown, uses, material);
                    specialItems.add(specialItem);
                }
            }
        }
    }

    public List<SpecialItem> getSpecialItems() {
        return specialItems;
    }
}
