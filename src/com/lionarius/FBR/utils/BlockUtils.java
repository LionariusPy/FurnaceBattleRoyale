package com.lionarius.FBR.utils;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class BlockUtils {

    private static final Map<Location, LivingEntity> glowingBlocks = new HashMap<>();

    public static void setGlow(Location blockLocation, boolean isGlowing) {
        if (isGlowing) {
            if (!glowingBlocks.containsKey(blockLocation)) {
                LivingEntity shulker = (LivingEntity) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.SHULKER);
                shulker.setAI(false);
                shulker.setGlowing(true);
                shulker.setGravity(false);
                shulker.setSilent(true);
                shulker.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 2, false, false));

                glowingBlocks.put(blockLocation, shulker);
            }
        } else {
            if (glowingBlocks.containsKey(blockLocation)) {
                glowingBlocks.get(blockLocation).remove();

                glowingBlocks.remove(blockLocation);
            }
        }
    }
}