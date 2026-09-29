package com.nenfal;

import co.aikar.commands.PaperCommandManager;
import com.nenfal.commands.TownRatingCommand;
import com.nenfal.database.TownRatingDAO;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MvndiTownRating extends JavaPlugin {

    @Override
    public void onEnable() {
        PaperCommandManager manager = new PaperCommandManager(this);
        Bukkit.getPluginManager().getPlugin("MvndiCore");
        TownRatingDAO townRatingDAO = new TownRatingDAO();
        manager.registerCommand(new TownRatingCommand(townRatingDAO));
        saveDefaultConfig();
        reloadConfig();
    }
}