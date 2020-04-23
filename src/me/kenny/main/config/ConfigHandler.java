package me.kenny.main.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    private List<Config> configs = new ArrayList<>();

    public void addConfig(Config config) {
        configs.add(config);
    }

    public List<Config> getConfigs() {
        return configs;
    }
}
