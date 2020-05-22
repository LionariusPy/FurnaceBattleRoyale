package com.lionarius.FBR.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.lionarius.FBR.FurnaceBattleRoyale;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class WorldBorderUtils {

    public static void setWorldBorderLocation(Player player, Location location) {

        PacketContainer packetContainer = FurnaceBattleRoyale.protocolManager.createPacket(PacketType.Play.Server.WORLD_BORDER);

        packetContainer.getWorldBorderActions().writeDefaults().writeSafely(0, EnumWrappers.WorldBorderAction.SET_CENTER);

        packetContainer.getDoubles().writeDefaults().
                write(0, location.getX()).
                write(1, location.getZ());

        try {
            FurnaceBattleRoyale.protocolManager.sendServerPacket(player, packetContainer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void setWorldBorderSize(Player player, double size) {

        PacketContainer packetContainer = FurnaceBattleRoyale.protocolManager.createPacket(PacketType.Play.Server.WORLD_BORDER);

        packetContainer.getWorldBorderActions().writeDefaults().writeSafely(0, EnumWrappers.WorldBorderAction.SET_SIZE);

        packetContainer.getDoubles().writeDefaults().write(2, size);

        try {
            FurnaceBattleRoyale.protocolManager.sendServerPacket(player, packetContainer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void resetWorldBorder(Player player) {

        WorldBorder worldBorder = FurnaceBattleRoyale.getWorld().getWorldBorder();
        setWorldBorderLocation(player, worldBorder.getCenter());
        setWorldBorderSize(player, worldBorder.getSize());
    }
}
