package me.kenny.main.config;

import me.kenny.main.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private Main main;
    private String name;
    private File file;
    private FileConfiguration fileConfiguration;

    public Config(Main main, String name) {
        this.main = main;
        this.name = name;

        getConfig();
    }

    private void getConfig() {
        file = new File(main.getDataFolder().getPath(), name + ".yml");
        fileConfiguration = new YamlConfiguration();

        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() throws IOException {
        fileConfiguration.save(file);
    }
}
