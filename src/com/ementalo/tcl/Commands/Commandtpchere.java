package com.ementalo.tcl.Commands;

import com.ementalo.tcl.Config;
import com.ementalo.tcl.TeleConfirmLite;
import com.ementalo.tcl.TpAction;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Commandtpchere implements ITclCommand {

    public void execute(CommandSender sender, Command command, String commandLabel, String[] args, TeleConfirmLite parent) {
        sender.sendMessage(Config.playerCommand);
    }

    public void execute(Player player, Command command, String commandLabel, String[] args, TeleConfirmLite parent) {

        if (args.length != 1) {
            help(player, commandLabel);
            return;
        }
        else {
        TpAction req = null;
        Player other = null;

        List<Player> targets = parent.getServer().matchPlayer(args[0]);

        if (targets.size() >= 1) {
            other = targets.get(0);
        } else {
            player.sendMessage(Config.playerNotFound.replace("%p", args[0]));
            return;
        }

        if(Config.preventCrossWorldTp && !parent.tclUserHandler.worldCompare(player.getWorld(), other.getWorld()))
        {
            player.sendMessage(Config.requestWorlds);
            return;
        }

        if (parent.tclUserHandler.hasToggled(other)) {
            player.sendMessage(Config.toggledMsg.replace("%p", other.getDisplayName()));
            return;
        }

        req = new TpAction(
                player,
                player,
                other,
                TpAction.Actions.TELEPORT_PLAYER_TO,
                parent);

        parent.tclUserHandler.processRequest(player, req);

    }
    }

    public void help(CommandSender sender, String commandLabel) {
        sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " playername");
    }
}
