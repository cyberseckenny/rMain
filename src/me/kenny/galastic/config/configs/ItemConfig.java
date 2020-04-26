package me.kenny.galastic.config.configs;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.config.Config;
import me.kenny.galastic.gui.Gui;
import me.kenny.galastic.util.DoubleValue;
import me.kenny.galastic.util.EditingPlayerHandler;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ItemConfig extends Config {
    public EditingPlayerHandler.EditingType editingType;
    public Gui gui;

    public ItemConfig(Galastic galastic, String name, EditingPlayerHandler.EditingType editingType, Gui gui) {
        super(galastic, name);

        this.editingType = editingType;
        this.gui = gui;
    }

    public Integer addItem(ItemStack item, boolean rare, String path) {
        int nextKey = getFirstAvailableKey(path);
        if (item != null && item.getType() != Material.AIR) {
            getFileConfiguration().set(path + "." + Integer.valueOf(nextKey).toString() + ".item", item.serialize());
            getFileConfiguration().set(path + "." + Integer.valueOf(nextKey).toString() + ".rare", rare);
            save();

            reorganize(path);
            updateGui(editingType, gui);

            return nextKey;
        }
        return -1;
    }

    public Integer removeItem(ItemStack item, String path) {
        for (String key : getFileConfiguration().getConfigurationSection(path).getKeys(false)) {
            String section = path.equals("") ? key : path + "." + key;
            ItemStack configItem = ItemStack.deserialize(getFileConfiguration().getConfigurationSection(section + ".item").getValues(true));
            if (configItem.isSimilar(item)) {
                updateGui(editingType, gui);
                getFileConfiguration().set(section, null);
                save();
                return Integer.parseInt(key);
            }
        }

        return -1;
    }

    public Map<ItemStack, DoubleValue> getItems(String path) {
        Map<ItemStack, DoubleValue> loot = new HashMap<>();

        if (getFileConfiguration().getConfigurationSection(path) == null)
            return loot;

        for (String key : getFileConfiguration().getConfigurationSection(path).getKeys(false)) {
            String p = path.equals("") ? key : path + "." + key;
            FileConfiguration configuration = getFileConfiguration();
            ConfigurationSection configurationSection = getFileConfiguration().getConfigurationSection(p + ".item");
            Map<String, Object> section = getFileConfiguration().getConfigurationSection(p + ".item").getValues(false);
            ItemStack item = ItemStack.deserialize(section);
            boolean rare = getFileConfiguration().getBoolean(p + ".rare");
            String[] split = p.split("\\.");
            loot.put(item, new DoubleValue(Integer.parseInt(split[split.length - 1]), rare));
        }
        return loot;
    }

    public ItemStack getItem(String path) {
        ConfigurationSection configurationSection = getFileConfiguration().getConfigurationSection(path + ".item");
        Map<String, Object> section = getFileConfiguration().getConfigurationSection(path + ".item").getValues(false);
        ItemStack item = ItemStack.deserialize(section);
        return item;
    }

    public boolean hasIdenticalItem(ItemStack item, String path) {
        if (getItems(path) == null)
            return false;
        for (Map.Entry<ItemStack, DoubleValue> loot : getItems(path).entrySet()) {
            if (loot.getKey().isSimilar(item)) {
                return true;
            }
        }
        return false;
    }

    // gets the first available key in the config
    public Integer getFirstAvailableKey(String path) {
        int lastKey = 0;

        if (getFileConfiguration().getConfigurationSection(path) == null || getFileConfiguration().getConfigurationSection(path).getValues(false).isEmpty())
            return 1;

        Set<String> paths = path.equals("") ? getFileConfiguration().getKeys(false) : getFileConfiguration().getConfigurationSection(path).getKeys(false);

        int lastValue = Integer.parseInt((String) paths.toArray()[paths.size() - 1]);
        for (int i = 1; i < lastValue + 1; i++) {
            if (!getFileConfiguration().contains(path + "." + String.valueOf(i))) {
                return i;
            }
            lastKey = i;
        }
        return lastKey + 1;
    }

    // reorganizes the config from least to greatest.
    public void reorganize(String path) {
        Map<Integer, Map<String, Object>> sections = new HashMap<>();

        for (String key : getFileConfiguration().getConfigurationSection(path).getKeys(false)) {
            sections.put(Integer.parseInt(key), getFileConfiguration().getConfigurationSection(path + "." + key).getValues(true));
        }

        sections = sections.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Map<String, Object>>comparingByKey())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        clearConfig(path);

        for (Map.Entry<Integer, Map<String, Object>> entry : sections.entrySet()) {
            for (Map.Entry<String, Object> value : entry.getValue().entrySet()) {
                getFileConfiguration().set(path + "." + entry.getKey() + "." + value.getKey(), value.getValue());
            }
        }

        save();
    }

    public void updateGui(EditingPlayerHandler.EditingType editingType, Gui gui) {
        if (editingType == null)
            return;

        for (Player player : galastic.getEditingPlayerHandler().getEditing(editingType)) {
            player.getOpenInventory().getTopInventory().setContents(gui.getGui().getContents());
        }
    }

    public void clearConfig(String path) {
        for (String key : getFileConfiguration().getConfigurationSection(path).getKeys(false)) {
            getFileConfiguration().set(key, null);
        }
    }
}
