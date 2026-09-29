package com.nenfal.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.nenfal.database.TownRatingDAO;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

@CommandAlias("mtr")
public class TownRatingCommand extends BaseCommand {

    private final TownRatingDAO townRatingDAO;

    public TownRatingCommand(TownRatingDAO townRatingDAO) {
        this.townRatingDAO = townRatingDAO;
    }

    private static final String PREFIX = "§6§lᴛᴏᴡɴʀᴀᴛɪɴɢ §8§l» §r";

    public boolean ensureTownExists(CommandSender sender, String townName) {
        com.palmergames.bukkit.towny.object.Town townObj = TownyAPI.getInstance().getTown(townName);
        if (townObj == null) {
            sender.sendMessage(PREFIX + "§cTown not found §4✘");
            return false;
        }
        return true;
    }

    @Default
    public void onDefault(CommandSender sender) {
        sendHelp(sender);
    }

    @Subcommand("help|h")
    public void onHelp(CommandSender sender) {
        sendHelp(sender);
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§7§l« §a§lTOWNRATING §7§l»");
        if (sender.hasPermission("mvndi.townrating.info")) {
            sender.sendMessage("§6● §6/mtr info [town] §e: get rating of a town");
        }
        if (sender.hasPermission("mvndi.townrating.set")) {
            sender.sendMessage("§6● §6/mtr set <town> <value> §e: set rating to a town");
        }
        if (sender.hasPermission("mvndi.townrating.remove")) {
            sender.sendMessage("§6● §6/mtr remove <town> §e: remove rating from a town");
        }
    }

    @CommandPermission("mvndi.townrating.set")
    @Subcommand("set")
    public void onSet(CommandSender sender, String townName, BigDecimal townRate) {
        if (!ensureTownExists(sender, townName)) {
            return;
        }

        if (townRate.compareTo(BigDecimal.ZERO) < 0 || townRate.compareTo(BigDecimal.ONE) > 0) {
            sender.sendMessage(PREFIX + "§cError: Rating must be between 0 and 1");
            return;
        }

        try {
            townRatingDAO.saveRating(townName, townRate);
            sender.sendMessage(PREFIX + "§aRating §e" + townRate + " §aset to town §e" + townName + " §2✔");
        } catch (IllegalArgumentException e) {
            sender.sendMessage(PREFIX + "§c" + e.getMessage());
        }
    }

    @CommandPermission("mvndi.townrating.info")
    @Subcommand("info|i")
    public void onGetRating(CommandSender sender, @Optional String townName) {
        if (townName == null || townName.isEmpty()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(PREFIX + "§cConsole must specify a town name!");
                return;
            }

            Player player = (Player) sender;
            Resident resident = TownyAPI.getInstance().getResident(player);

            if (resident == null || !resident.hasTown()) {
                sender.sendMessage(PREFIX + "§cYou are not a member of any town!");
                return;
            }

            try {
                townName = resident.getTown().getName();
            } catch (Exception e) {
                sender.sendMessage(PREFIX + "§cCould not find your town.");
                return;
            }
        }

        if (!ensureTownExists(sender, townName)) {
            return;
        }

        BigDecimal rating = townRatingDAO.getRating(townName);
        sender.sendMessage(PREFIX + "§aRating for town §e" + townName + " §ais §e" + rating);
    }

    @CommandPermission("mvndi.townrating.remove")
    @Subcommand("remove|r")
    public void onRemoveRating(CommandSender sender, String townName) {
        if (!ensureTownExists(sender, townName)) {
            return;
        }
        townRatingDAO.removeRating(townName);
        sender.sendMessage(PREFIX + "§aSuccessfully removed rating for §e" + townName + " §2✔");
    }
}