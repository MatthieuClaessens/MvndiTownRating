package com.nenfal;

import co.aikar.commands.PaperCommandManager;
import com.mongodb.client.MongoDatabase;
import com.nenfal.commands.TownRatingCommand;
import com.nenfal.database.TownRatingDAO;
import net.mvndicraft.mvndicore.MvndiCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MvndiTownRating extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("MvndiCore") == null) {
            getLogger().severe("MvndiCore is required for MvndiTownRating to work!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MongoDatabase database = MvndiCore.getInstance().getDatabases().mongoDatabase();
        TownRatingDAO townRatingDAO = new TownRatingDAO(database);

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new TownRatingCommand(townRatingDAO));

        getLogger().info("MvndiTownRating has been successfully enabled!");
    }
}