package com.lionarius.FBR;

import com.lionarius.FBR.commands.CreateChunkBorderCommand.CreateChunkBorderCommand;
import com.lionarius.FBR.commands.CreateChunkBorderCommand.CreateChunkBorderCommandCompletion;
import com.lionarius.FBR.commands.CreateTimerCommand.CreateTimerCommand;
import com.lionarius.FBR.commands.TeamCommand.TeamCommand;
import com.lionarius.FBR.commands.TeamCommand.TeamCommandCompletion;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.listeners.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import com.lionarius.FBR.config.GameConfigManager;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class FurnaceBattleRoyale extends JavaPlugin {

    public static FurnaceBattleRoyale instance;

    public static FurnaceBattleRoyale getInstance() {return instance;};

    public static World getWorld()
    {
        return Bukkit.getWorld("world");
    }
    public static World getNether()
    {
        return Bukkit.getWorld("world_nether");
    }
    public static World getEnd()
    {
        return Bukkit.getWorld("world_the_end");
    }

    @Override
    public void onLoad()
    {
        try {
            FileUtils.deleteDirectory(new File("." + File.separator + "world"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.deleteDirectory(new File("." + File.separator + "world_nether"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.deleteDirectory(new File("." + File.separator + "world_the_end"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable()
    {
        instance = this;

        new GameConfigManager();

        this.getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        this.getServer().getPluginManager().registerEvents(new FurnaceBurnedOutListener(), this);
        this.getServer().getPluginManager().registerEvents(new GUIListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new GameStateChangedListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerStateChangedListener(), this);
        this.getServer().getPluginManager().registerEvents(new EnterPortalListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);

        GameManager.setGameState(GameState.WAITING);
        setupCommands();
    }

    public void setupCommands()
    {
        this.getCommand("customborder").setExecutor(new CreateChunkBorderCommand());
        this.getCommand("customborder").setTabCompleter(new CreateChunkBorderCommandCompletion());

        this.getCommand("timer").setExecutor(new CreateTimerCommand());

        this.getCommand("fbrteam").setExecutor(new TeamCommand());
        this.getCommand("fbrteam").setTabCompleter(new TeamCommandCompletion());
    }
}
