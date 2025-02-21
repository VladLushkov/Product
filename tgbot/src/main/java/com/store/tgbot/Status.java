package com.store.tgbot;

public enum Status {
    UP("UP"),
    DOWN("DOWN"),
    OUT_OF_SERVICE("OUT_OF_SERVICE"),
    UNKNOWN("UNKNOWN");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public boolean isUp(String status) {
        return status.equals(UP.status);
    }
}
