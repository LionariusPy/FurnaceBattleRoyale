package com.lionarius.FBR.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerManager;

import java.util.List;

public class TeamCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if(sender instanceof Player)
        {
            player = (Player) sender;
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Only players can use this command");
            return true;
        }

        FBRPlayer fbrPlayer = PlayerManager.getFBRPlayer(player);

        FBRPlayer argsFbrPlayer = PlayerManager.getFBRPlayer(args[1]);
        if(argsFbrPlayer == null)
        {
            fbrPlayer.getPlayer().sendMessage(ChatColor.RED + "Player doesn't exists");

            return true;
        }

        if(fbrPlayer.isLeader()) {
            if (args[0].equalsIgnoreCase("invite")) {
                if (fbrPlayer == argsFbrPlayer) {
                    fbrPlayer.getPlayer().sendMessage(ChatColor.RED + "You cannot invite yourself");
                }

                fbrPlayer.getTeam().addPlayer(argsFbrPlayer);
            } else if (args[0].equalsIgnoreCase("kick")) {
                fbrPlayer.getTeam().removePlayer(argsFbrPlayer, true);
            }
        }
        else fbrPlayer.getPlayer().sendMessage(ChatColor.RED + "You are not leader of your team");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
