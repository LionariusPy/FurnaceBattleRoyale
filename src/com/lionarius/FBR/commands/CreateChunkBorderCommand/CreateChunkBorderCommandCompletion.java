package com.lionarius.FBR.commands.CreateChunkBorderCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateChunkBorderCommandCompletion implements TabCompleter
{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)
        {
            List<String> list = new ArrayList<String>();

            list.add("create");
            list.add("remove");

            return list;
        }
        return null;
    }
}
