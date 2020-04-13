package com.lionarius.FBR.utils;

import com.lionarius.FBR.config.GameConfigManager;
import com.lionarius.FBR.team.FBRTeam;
import com.lionarius.FBR.team.TeamManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationUtils {

    public static final List<Material> leaves = new ArrayList<Material>(Arrays.asList(Material.ACACIA_LEAVES, Material.BIRCH_LEAVES,
                                                                                  Material.DARK_OAK_LEAVES, Material.JUNGLE_LEAVES,
                                                                                  Material.OAK_LEAVES, Material.SPRUCE_LEAVES));

    public static Location getDownBlock(Location location)
    {
        Location down = location;

        while(!down.getBlock().getType().isSolid() && down.getBlock().getType() != Material.WATER)
        {
            down = down.subtract(0, 1, 0);
        }
        return down.add(0, 1, 0);
    }

    public static boolean isChunkInBorder(Chunk chunk)
    {
        int maxChunkLoc = Math.floorDiv(GameConfigManager.MAP_SIZE_IN_CHUNKS, 2);

        return (Math.abs(chunk.getX()) <= maxChunkLoc) && (Math.abs(chunk.getZ()) <= maxChunkLoc);
    }

    public static boolean isUniqueChunkForTeam(FBRTeam fbrTeam)
    {
        List<Chunk> chunkList = new ArrayList<Chunk>();
        for(FBRTeam team : TeamManager.getFBRTeams())
        {
            if(team == fbrTeam) continue;
            chunkList.add(team.getTeamChunk());
        }

        return !chunkList.contains(fbrTeam.getTeamChunk());
    }

    public static Chunk getClosestFreeChunk(Chunk target)
    {
        Chunk closestChunk = null;
        int searchRadius = Math.floorDiv(GameConfigManager.MAP_SIZE_IN_CHUNKS, 2);

        int targetX = target.getX();
        int targetZ = target.getZ();

        List<Vector> spiral = spiral(searchRadius);

        List<Chunk> chunkList = new ArrayList<Chunk>();
        for(FBRTeam team : TeamManager.getFBRTeams())
        {
            chunkList.add(team.getTeamChunk());
        }

        for(Vector vector : spiral)
        {
            int x = (int) vector.getX() + targetX;
            int z = (int) vector.getZ() + targetZ;

            Chunk currentChunk = target.getWorld().getChunkAt(x,z);

            if(!isChunkInBorder(currentChunk)) continue;

            if(chunkList.contains(currentChunk)) continue;

            closestChunk = currentChunk;
            return closestChunk;
        }

        return null;
    }

    public static List<Vector> spiral(int radius)
    {
        List<Vector> output = new ArrayList<Vector>();

        int x = 0;
        int y = 0;
        int dx = 0;
        int dy = -1;
        int temp;

        for(int i = 0; i < radius*radius; i++)
        {
            Vector tempVector = new Vector(x, 0, y);

            if((-radius/2 <= x  && x < radius/2) && (-radius/2 <= y  && y < radius/2)) output.add(tempVector);

            if(x == y || (x < 0 && x == -y) || (x > 0 && x == 1-y))
            {
                temp = dx;
                dx = -dy;
                dy = temp;
            }
            x += dx;
            y += dy;
        }

        return output;
    }
}
