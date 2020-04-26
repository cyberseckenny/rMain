package me.kenny.galastic.util;

// stores an integer and a boolean
public class DoubleValue {
    private int integer;
    private boolean bool;

    public DoubleValue(int integer, boolean bool) {
        this.integer = integer;
        this.bool = bool;
    }

    public Boolean getBoolean() {
        return bool;
    }

    public int getInteger() {
        return integer;
    }
}
