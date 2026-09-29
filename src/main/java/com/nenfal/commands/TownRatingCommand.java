package com.nenfal.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.nenfal.database.TownRatingDAO;
import com.palmergames.bukkit.towny.TownyAPI;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

@CommandAlias("townrating | tr")
public class TownRatingCommand extends BaseCommand {

    private final TownRatingDAO townRatingDAO;

    public TownRatingCommand(TownRatingDAO townRatingDAO) {
        this.townRatingDAO = townRatingDAO;
    }

    private static final String PREFIX = "§#f1c40f§lᴛᴏᴡɴʀᴀᴛɪɴɢ §7§l» §r";

    public boolean ensureTownExists(CommandSender sender, String townName) {
        com.palmergames.bukkit.towny.object.Town townObj = TownyAPI.getInstance().getTown(townName);
        if (townObj == null) {
            sender.sendMessage(PREFIX + "§cTown not found §#c0392b✘");
            return false;
        }
        sender.sendMessage(PREFIX + "§aTown §e" + townObj.getName() + " §afound! §#2ecc71✔");
        return true;
    }

    @Default
    @Subcommand("help | h")
    public void onDefault(CommandSender sender) {
        sender.sendMessage("§8§l«« §6§lᴍᴠɴᴅɪ §#f1c40f§lᴛᴏᴡɴʀᴀᴛɪɴɢ §8§l»»");
        sender.sendMessage("§6● §#f1c40f/ᴛᴏᴡɴʀᴀᴛɪɴɢ ᴀᴅᴅ <ᴛᴏᴡɴ> <ᴠᴀʟᴜᴇ> §7: ᴀᴅᴅ ʀᴀᴛɪɴɢ ᴛᴏ ᴀ ᴛᴏᴡɴ");
        sender.sendMessage("§6● §#f1c40f/ᴛᴏᴡɴʀᴀᴛɪɴɢ info <ᴛᴏᴡɴ> §7: ɢᴇᴛ ʀᴀᴛɪɴɢ ᴏꜰ ᴀ ᴛᴏᴡɴ");
        sender.sendMessage("§6● §#f1c40f/ᴛᴏᴡɴʀᴀᴛɪɴɢ remove <ᴛᴏᴡɴ> §7: ʀᴇᴍᴏᴠᴇ ʀᴀᴛɪɴɢ ꜰʀᴏᴍ ᴀ ᴛᴏᴡɴ");
    }

    @CommandPermission("mvndi.townrating.add")
    @Subcommand("add")
    public void onAdd(CommandSender sender, String townName, BigDecimal townRate) {
        if (!ensureTownExists(sender, townName)) {
            return;
        }
        townRatingDAO.saveRating(townName, townRate);

        sender.sendMessage(PREFIX + "§aRating added to town §#2ecc71✔");

    }

    @CommandPermission("mvndi.townrating.info")
    @Subcommand("info | i")
    public void onGetRating(CommandSender sender, String townName) {
        if (!ensureTownExists(sender, townName)) {
            return;
        }
        BigDecimal rating = townRatingDAO.getRating(townName);
        sender.sendMessage(PREFIX + "§aRating for town §e" + townName + " §ais: §e" + rating);
    }

    @CommandPermission("mvndi.townrating.remove")
    @Subcommand("remove | r")
    public void onRemoveRating(CommandSender sender, String townName) {
        if (!ensureTownExists(sender, townName)) {
            return;
        }
        BigDecimal rating = townRatingDAO.getRating(townName);
        townRatingDAO.removeRating(townName);
        sender.sendMessage(PREFIX + "§aSuccessfully removed §e" + townName);
    }
}
