package com.nenfal.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.palmergames.bukkit.towny.TownyAPI;
import org.bukkit.command.CommandSender;

@CommandAlias("townrating | tr")
public class TownRatingCommand extends BaseCommand {

    private static final String PREFIX = "&#f1c40f&lᴛᴏᴡɴʀᴀᴛɪɴɢ &7&l» &r";

    public boolean ensureTownExists(CommandSender sender, String town) {
        com.palmergames.bukkit.towny.object.Town townObj = TownyAPI.getInstance().getTown(town);
        if (townObj == null) {
            sender.sendMessage(PREFIX + "&cTown not found &#c0392b✘");
            return false;
        }
        sender.sendMessage(PREFIX + "&aTown &e" + townObj.getName() + " &afound! &#2ecc71✔");
        return true;
    }

    @Default
    @Subcommand("help | h")
    public void onDefault(CommandSender sender) {
        sender.sendMessage("&8&l«« &6&lᴍᴠɴᴅɪ &#f1c40f&lᴛᴏᴡɴʀᴀᴛɪɴɢ &8&l»»");
        sender.sendMessage("&6● &#f1c40f/ᴛᴏᴡɴʀᴀᴛᴇ ᴀᴅᴅ <ᴛᴏᴡɴ> <ᴠᴀʟᴜᴇ> &7: ᴀᴅᴅ ʀᴀᴛɪɴɢ ᴛᴏ ᴀ ᴛᴏᴡɴ");
        sender.sendMessage("&6● &#f1c40f/ᴛᴏᴡɴʀᴀᴛᴇ remove <ᴛᴏᴡɴ> &7: ʀᴇᴍᴏᴠᴇ ʀᴀᴛɪɴɢ ꜰʀᴏᴍ ᴀ ᴛᴏᴡɴ");
        sender.sendMessage("&6● &#f1c40f/ᴛᴏᴡɴʀᴀᴛᴇ remove <ᴛᴏᴡɴ> &7: ʀᴇᴍᴏᴠᴇ ʀᴀᴛɪɴɢ ꜰʀᴏᴍ ᴀ ᴛᴏᴡɴ");
    }

    @CommandPermission("mvndi.townrating.add")
    @Subcommand("add")
    public void onAdd(CommandSender sender, String town, int value) {
        if (!ensureTownExists(sender, town)) {
            return;
        }
        sender.sendMessage(PREFIX + "&aRating added to town &#2ecc71✔");

    }
}
