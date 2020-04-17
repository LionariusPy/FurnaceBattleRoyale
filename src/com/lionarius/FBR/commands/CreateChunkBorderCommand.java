package com.lionarius.FBR.commands;

import com.lionarius.FBR.utils.WorldBorderUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateChunkBorderCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command");
            return true;
        }

        if (args.length == 1) {
            System.out.println(args[0]);
            if (args[0].equalsIgnoreCase("create")) {
                WorldBorderUtils.setWorldBorderSize(player, 16);

                Location chunkLocation = new Location(player.getWorld(), player.getLocation().getChunk().getX() * 16 + 8, 0, (player.getLocation().getChunk().getZ() * 16) + 8);
                WorldBorderUtils.setWorldBorderLocation(player, chunkLocation);
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                WorldBorderUtils.resetWorldBorder(player);
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            List<String> list = new ArrayList<String>();

            list.add("create");
            list.add("remove");

            return list;
        }
        return null;
    }
}

