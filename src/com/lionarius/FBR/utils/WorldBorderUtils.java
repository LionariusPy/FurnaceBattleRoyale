package com.lionarius.FBR.utils;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_15_R1.WorldBorder;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class WorldBorderUtils {

    public static void SetWorldBorderLocation(Player player, Location location)
    {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();

        WorldBorder playerWorldBorder = entityPlayer.world.getWorldBorder();

        PacketPlayOutWorldBorder worldBorder = new PacketPlayOutWorldBorder(playerWorldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER);

        setField(worldBorder, "c", location.getX());
        setField(worldBorder, "d", location.getZ());

        entityPlayer.playerConnection.sendPacket(worldBorder);
    }

    public static void SetWorldBorderSize(Player player, double size)
    {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();

        WorldBorder playerWorldBorder = entityPlayer.world.getWorldBorder();

        PacketPlayOutWorldBorder worldBorder = new PacketPlayOutWorldBorder(playerWorldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE);

        setField(worldBorder, "e", size);

        entityPlayer.playerConnection.sendPacket(worldBorder);
    }

    public static void ResetWorldBorder(Player player)
    {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();

        WorldBorder playerWorldBorder = entityPlayer.world.getWorldBorder();

        PacketPlayOutWorldBorder worldBorder = new PacketPlayOutWorldBorder(playerWorldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);

        setField(worldBorder, "e", playerWorldBorder.getSize());
        setField(worldBorder, "c", playerWorldBorder.getCenterX());
        setField(worldBorder, "d", playerWorldBorder.getCenterZ());

        entityPlayer.playerConnection.sendPacket(worldBorder);
    }

    private static void setField(Object object, String fieldName, Object value)
    {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
