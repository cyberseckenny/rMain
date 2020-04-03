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
        getFileConfiguration().set(cooldown, cooldown);
        save();
    }

    public Long getCooldown(Player player, String cooldown) {
        if (getFileConfiguration().get(player.getUniqueId() + "." + cooldown) != null)
            return getFileConfiguration().getLong(player.getUniqueId() + "." + cooldown);
        return null;
    }

    public boolean isOnCooldown(Player player, String cooldown) {
        for (Map.Entry<String, Object> entry : getFileConfiguration().getValues(false).entrySet()) {
            if (entry.getKey().equals(cooldown)) {
                Long value = (Long) entry.getValue();
                if (value < System.currentTimeMillis()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getFormattedCooldown(Player player, String cooldownName) {
        int cooldown = (int) TimeUnit.SECONDS.convert(getCooldown(player, cooldownName), TimeUnit.MILLISECONDS);
        int hours = (cooldown / 60 / 60) % 60;
        int minutes = (cooldown / 60) % 60;
        int seconds = cooldown % 60;

        String timeLeft = seconds + " seconds";
        if (minutes != 0)
            timeLeft = minutes + " minutes, " + timeLeft;
        if (hours != 0)
            timeLeft = hours + " hours, " + timeLeft;

        return timeLeft;
    }
}
