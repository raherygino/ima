package com.gsoft.ima.utils;

public enum NavIcon {
    HOME("ic_home_filled", "ic_home_outline"),
    TRANSACTION("ic_scan", "ic_scan"),
    PROJECT("ic_setting_filled", "ic_setting_outline"),
    PROFILE("ic_user_filled", "ic_user_outline");
    public final String active;
    public final String inactive;
    private NavIcon(String active, String inactive) {
        this.active = active;
        this.inactive = inactive;
    }
}
