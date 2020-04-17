package com.lionarius.FBR.commands;

import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CreateTimerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        GameManager.setGameState(GameState.PLAYING_1);

        return true;
    }
}
