package com.lionarius.FBR.config;

import com.lionarius.FBR.FurnaceBattleRoyale;
import org.bukkit.Material;

import java.io.File;
import java.util.Objects;

public class ConfigManager {

    public static int MAX_PLAYERS_TEAM;
    public static Material FURNACE_TYPE;
    public static long TASK_UPDATE_TIME;
    public static int FURNACE_TIME_WITHOUT_SMELTING;
    public static boolean CAN_SPECTATE;
    public static int MAP_SIZE_IN_CHUNKS;
    public static boolean IS_PORTALS_ENABLED;
    public static int MAX_OFFLINE_TIME;

    public ConfigManager() {
        FurnaceBattleRoyale plugin = FurnaceBattleRoyale.getInstance();

        File configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            createConfig(plugin);

            plugin.getConfig().set("game.team.max_players", 4);
            plugin.getConfig().set("game.furnace.type", "default");
            plugin.getConfig().set("game.furnace.time_without_smelting", 10);
            plugin.getConfig().set("game.task_update_time_ticks", 5L);
            plugin.getConfig().set("game.player.can_spectate", true);
            plugin.getConfig().set("game.player.max_offline_time", 15);
            plugin.getConfig().set("game.map_size", 2016);
            plugin.getConfig().set("game.is_portals_enabled", true);
        }
        plugin.saveConfig();

        loadConfig(plugin);
    }

    private void createConfig(FurnaceBattleRoyale plugin) {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }

    private void loadConfig(FurnaceBattleRoyale plugin) {
        MAX_PLAYERS_TEAM = plugin.getConfig().getInt("game.team.max_players");
        if (MAX_PLAYERS_TEAM > 8) MAX_PLAYERS_TEAM = 8;

        switch (Objects.requireNonNull(plugin.getConfig().getString("game.furnace.type")).toLowerCase()) {
            case "blast":
                FURNACE_TYPE = Material.BLAST_FURNACE;
                break;
            case "smoker":
                FURNACE_TYPE = Material.SMOKER;
                break;
            default:
                FURNACE_TYPE = Material.FURNACE;
        }

        TASK_UPDATE_TIME = plugin.getConfig().getLong("game.task_update_time_ticks");
        FURNACE_TIME_WITHOUT_SMELTING = plugin.getConfig().getInt("game.furnace.time_without_smelting");
        CAN_SPECTATE = plugin.getConfig().getBoolean("game.player.can_spectate");
        MAP_SIZE_IN_CHUNKS = (int) Math.ceil(plugin.getConfig().getDouble("game.map_size") / 16);
        IS_PORTALS_ENABLED = plugin.getConfig().getBoolean("game.is_portals_enabled");
        MAX_OFFLINE_TIME = plugin.getConfig().getInt("game.player.max_offline_time");
    }
}
