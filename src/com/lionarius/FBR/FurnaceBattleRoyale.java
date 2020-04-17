package com.lionarius.FBR;

import com.lionarius.FBR.commands.CreateChunkBorderCommand;
import com.lionarius.FBR.commands.CreateTimerCommand;
import com.lionarius.FBR.commands.TeamCommand;
import com.lionarius.FBR.config.ConfigManager;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.listeners.*;
import com.lionarius.FBR.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FurnaceBattleRoyale extends JavaPlugin {

    private static FurnaceBattleRoyale instance;

    public static FurnaceBattleRoyale getInstance() {
        return instance;
    }

    public static World getWorld() {
        return Bukkit.getWorld("world");
    }

    public static World getNether() {
        return Bukkit.getWorld("world_nether");
    }

    public static World getEnd() {
        return Bukkit.getWorld("world_the_end");
    }

    @Override
    public void onLoad() {
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
    public void onEnable() {
        instance = this;

        new ConfigManager();
        //create spiral pattern
        LocationUtils.spiralPattern = LocationUtils.spiral(ConfigManager.MAP_SIZE_IN_CHUNKS);

        this.getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        this.getServer().getPluginManager().registerEvents(new FurnaceBurnedOutListener(), this);
        this.getServer().getPluginManager().registerEvents(new GUIListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new GameStateChangedListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerStateChangedListener(), this);
        this.getServer().getPluginManager().registerEvents(new EnterPortalListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);

        GameManager.setGameState(GameState.WAITING);
        setupCommands();
    }

    public void setupCommands() {
        this.getCommand("customborder").setExecutor(new CreateChunkBorderCommand());
        this.getCommand("customborder").setTabCompleter(new CreateChunkBorderCommand());

        this.getCommand("timer").setExecutor(new CreateTimerCommand());

        this.getCommand("fbrteam").setExecutor(new TeamCommand());
        this.getCommand("fbrteam").setTabCompleter(new TeamCommand());
    }
}
