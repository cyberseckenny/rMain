package me.kenny.galastic.util;

public class TimeUtils {
    public static String getFormattedTime(int time) {
        int hours = (time / 60 / 60) % 60;
        int minutes = (time / 60) % 60;
        int seconds = time % 60;

        String timeLeft = seconds + " seconds";
        if (minutes != 0)
            timeLeft = minutes + " minutes, " + timeLeft;
        if (hours != 0)
            timeLeft = hours + " hours, " + timeLeft;

        return timeLeft;
    }

    public static String getFormattedTimeWithoutZeroes(int time) {
        String cooldown = getFormattedTime(time);
        cooldown = cooldown.replace(", 0 seconds", "");
        cooldown = cooldown.replace(", 0 minutes", "");
        cooldown = cooldown.replace("0 hours", "");
        return cooldown;
    }
}
