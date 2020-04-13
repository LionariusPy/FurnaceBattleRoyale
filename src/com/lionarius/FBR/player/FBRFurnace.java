package com.lionarius.FBR.player;

import com.lionarius.FBR.team.FBRTeam;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.config.GameConfigManager;
import com.lionarius.FBR.tasks.FBRFurnaceTask;

import java.util.List;

public class FBRFurnace {

    private Block furnaceBlock;
    private FBRFurnaceTask updateTask;
    private List<FBRPlayer> players;
    private FBRTeam team;

    public FBRFurnace(World world, Location location, List<FBRPlayer> players)
    {
        setPlayers(players);
        Block block = world.getBlockAt(location);

        block.setType(GameConfigManager.FURNACE_TYPE);
        setFurnaceBlock(block);

        team = players.get(0).getTeam();

        Furnace furnaceState = getFurnaceState();

        furnaceState.setBurnTime((short)600);
        furnaceState.update();

        updateTask = new FBRFurnaceTask(this);
    }

    public Furnace getFurnaceState() { return ((Furnace) furnaceBlock.getState()); }

    public Block getFurnaceBlock() { return furnaceBlock; }

    public void setFurnaceBlock(Block furnaceBlock)
    {
        this.furnaceBlock = furnaceBlock;
    }

    public short getBurnTime() { return ((Furnace) furnaceBlock.getState()).getBurnTime(); }

    public short getCookTime()
    {
        return ((Furnace) furnaceBlock.getState()).getCookTime();
    }

    public List<FBRPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<FBRPlayer> players) {
        this.players = players;
    }

    public FBRFurnaceTask getUpdateTask() {
        return updateTask;
    }

    public FBRTeam getTeam() {
        return team;
    }
}
