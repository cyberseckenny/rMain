package me.kenny.galastic.config;

import me.kenny.galastic.Galastic;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Config {
    public Galastic galastic;
    private String name;
    private File file;
    private FileConfiguration fileConfiguration;

    public Config(Galastic galastic, String name) {
        this.galastic = galastic;
        this.name = name;

        getConfig();
    }

    private void getConfig() {
        file = new File(galastic.getDataFolder(), name + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void save() {
        try {
            fileConfiguration.save(file);
            fileConfiguration.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            fileConfiguration.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
