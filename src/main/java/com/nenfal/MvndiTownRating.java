package com.nenfal;

import org.bukkit.plugin.java.JavaPlugin;

public class MvndiTownRating extends JavaPlugin {


    static MvndiTownRating instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig();
    }
}