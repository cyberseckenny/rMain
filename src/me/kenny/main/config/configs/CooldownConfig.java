package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CooldownConfig extends Config {
    public CooldownConfig(Main main) {
        super(main, "cooldowns");
    }

    public void addCooldown(Player player, String cooldown, long length) {
        Map<String, Long> map = new HashMap<>();
        getFileConfiguration().set(player.getUniqueId() + "." + cooldown, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(length));
        save();
    }

    public Long getCooldown(Player player, String cooldown) {
        if (getFileConfiguration().get(player.getUniqueId() + "." + cooldown) != null)
            return getFileConfiguration().getLong(player.getUniqueId() + "." + cooldown);
        return null;
    }

    public boolean isOnCooldown(Player player, String cooldown) {
        for (Map.Entry<String, Object> entry : getFileConfiguration().getValues(false).entrySet()) {
            System.out.println(entry.getKey());
            if (entry.getKey().equals(player.getUniqueId().toString())) {
                long value = getFileConfiguration().getLong(player.getUniqueId().toString() + "." + cooldown);
                System.out.println(value - System.currentTimeMillis());
                if (value > System.currentTimeMillis()) {
                    return true;
                }
            }
        }
        return false;
    }
}
