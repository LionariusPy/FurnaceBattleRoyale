package com.lionarius.FBR.player;

import com.lionarius.FBR.config.ConfigManager;
import com.lionarius.FBR.tasks.FBRFurnaceTask;
import com.lionarius.FBR.team.FBRTeam;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;

import java.util.List;

public class FBRFurnace {

    private final Block furnaceBlock;
    private final FBRFurnaceTask updateTask;
    private final List<FBRPlayer> players;
    private final FBRTeam team;

    public FBRFurnace(World world, Location location, List<FBRPlayer> players)
    {
        this.players = players;
        Block block = world.getBlockAt(location);

        block.setType(ConfigManager.FURNACE_TYPE);
        furnaceBlock = block;

        team = players.get(0).getTeam();

        Furnace furnaceState = getFurnaceState();

        furnaceState.setBurnTime((short) 600);
        furnaceState.update();

        updateTask = new FBRFurnaceTask(this);
    }

    public Furnace getFurnaceState() {
        return ((Furnace) furnaceBlock.getState());
    }

    public Block getFurnaceBlock() {
        return furnaceBlock;
    }

    public short getBurnTime() {
        return ((Furnace) furnaceBlock.getState()).getBurnTime();
    }

    public short getCookTime() {
        return ((Furnace) furnaceBlock.getState()).getCookTime();
    }

    public List<FBRPlayer> getPlayers() {
        return players;
    }

    public FBRFurnaceTask getUpdateTask() {
        return updateTask;
    }

    public FBRTeam getTeam() {
        return team;
    }
}
