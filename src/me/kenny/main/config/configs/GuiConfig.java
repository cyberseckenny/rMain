package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import me.kenny.main.gui.Gui;
import me.kenny.main.util.DoubleValue;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class GuiConfig extends Config {
    private EditingPlayerHandler.EditingType editingType;
    private Gui gui;

    public GuiConfig(Main main, String name, EditingPlayerHandler.EditingType editingType, Gui gui) {
        super(main, name);

        this.editingType = editingType;
        this.gui = gui;
    }

    public Integer addItem(ItemStack item, boolean rare, String path) {
        int nextKey = getFirstAvailableKey(path);
        getFileConfiguration().set(Integer.valueOf(nextKey).toString() + ".item", item.serialize());
        getFileConfiguration().set(Integer.valueOf(nextKey).toString() + ".rare", rare);
        save();

        reorganize(path);
        updateGui(editingType, gui);

        return nextKey;
    }

    public Integer removeItem(ItemStack item, String path) {
        for (String key : getFileConfiguration().getConfigurationSection(path).getKeys(false)) {
            String section = null;
            if (path == "") {
                section = key;
            } else {
                section = path + "." + key;
            }
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

    // gets the first available key in the config
    public Integer getFirstAvailableKey(String path) {
        int lastKey = 0;
        Set<String> paths = getFileConfiguration().getKeys(false);

        if (getFileConfiguration().getConfigurationSection(path).getKeys(false).isEmpty())
            return 1;

        int lastValue = Integer.parseInt((String) paths.toArray()[paths.size() - 1]);
        for (int i = 1; i < lastValue + 1; i++) {
            if (!getFileConfiguration().contains(String.valueOf(i))) {
                return i;
            }
            lastKey = i;
        }
        return lastKey + 1;
    }

    public Map<ItemStack, DoubleValue> getLoot() {
        Map<ItemStack, DoubleValue> loot = new HashMap<ItemStack, DoubleValue>();
        for (String path : getFileConfiguration().getKeys(false)) {
            FileConfiguration configuration = getFileConfiguration();
            ConfigurationSection configurationSection = getFileConfiguration().getConfigurationSection(path + ".item");
            Map<String, Object> section = getFileConfiguration().getConfigurationSection(path + ".item").getValues(false);
            ItemStack item = ItemStack.deserialize(section);
            boolean rare = getFileConfiguration().getBoolean(path + ".rare");
            loot.put(item, new DoubleValue(Integer.parseInt(path), rare));
        }
        return loot;
    }

    public boolean hasIdenticalItem(ItemStack item) {
        for (Map.Entry<ItemStack, DoubleValue> loot : getLoot().entrySet()) {
            if (loot.getKey().isSimilar(item)) {
                return true;
            }
        }
        return false;
    }

    // reorganizes the config from least to greatest.
    private void reorganize(String path) {
        Map<Integer, Map<String, Object>> sections = new HashMap<>();

        for (String key : getFileConfiguration().getConfigurationSection(path).getKeys(false)) {
            sections.put(Integer.parseInt(key), getFileConfiguration().getConfigurationSection(key).getValues(true));
        }

        sections = sections.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Map<String, Object>>comparingByKey())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        clearConfig(path);

        for (Map.Entry<Integer, Map<String, Object>> entry : sections.entrySet()) {
            for (Map.Entry<String, Object> value : entry.getValue().entrySet()) {
                getFileConfiguration().set(entry.getKey() + "." + value.getKey(), value.getValue());
            }
        }

        save();
    }

    private void updateGui(EditingPlayerHandler.EditingType editingType, Gui gui) {
        for (Player player : main.getEditingPlayerHandler().getEditing(editingType)) {
            player.getOpenInventory().getTopInventory().setContents(gui.getGui().getContents());
        }
    }

    private void clearConfig(String path) {
        for (String key : getFileConfiguration().getConfigurationSection(path).getKeys(false)) {
            getFileConfiguration().set(key, null);
        }
    }
}
