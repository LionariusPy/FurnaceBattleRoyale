package com.lionarius.FBR.commands.CreateTimerCommand;

import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerManager;
import com.lionarius.FBR.player.PlayerState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateTimerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FBRPlayer fbrPlayer = PlayerManager.getFBRPlayer((Player) sender);

        fbrPlayer.setPlayerState(PlayerState.DEAD);

        return true;
    }
}
