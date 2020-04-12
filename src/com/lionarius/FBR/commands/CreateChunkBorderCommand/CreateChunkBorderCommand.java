package com.lionarius.FBR.commands.CreateChunkBorderCommand;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.lionarius.FBR.utils.WorldBorderUtils;

public class CreateChunkBorderCommand implements CommandExecutor {

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

        if(args.length == 1)
        {
            System.out.println(args[0]);
            if(args[0].equalsIgnoreCase("create"))
            {
                WorldBorderUtils.SetWorldBorderSize(player, 16);

                Location chunkLocation = new Location(player.getWorld(), player.getLocation().getChunk().getX() * 16 +8, 0, (player.getLocation().getChunk().getZ() * 16) + 8);
                WorldBorderUtils.SetWorldBorderLocation(player, chunkLocation);
                return true;
            }
            else if(args[0].equalsIgnoreCase("remove"))
            {
                WorldBorderUtils.ResetWorldBorder(player);
                return true;
            }
            else return false;
        }
        else return false;
    }
}

