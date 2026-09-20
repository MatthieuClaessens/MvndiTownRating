package com.nenfal;

import co.aikar.commands.PaperCommandManager;
import com.nenfal.commands.TownRatingCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MvndiTownRating extends JavaPlugin {


    static MvndiTownRating instance;

    @Override
    public void onEnable() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new TownRatingCommand());
        instance = this;
        saveDefaultConfig();
        reloadConfig();
    }
}