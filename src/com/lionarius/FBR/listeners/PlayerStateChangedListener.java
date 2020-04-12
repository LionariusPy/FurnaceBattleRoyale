package com.lionarius.FBR.listeners;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.config.GameConfigManager;
import com.lionarius.FBR.events.PlayerStateChangedEvent;
import com.lionarius.FBR.utils.LocationUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerStateChangedListener implements Listener {

    @EventHandler
    public void onStateChanged(PlayerStateChangedEvent event)
    {
        Player player = event.getFBRPlayer().getPlayer();

        switch (event.getPlayerState())
        {
            case WAITING:
                player.teleport(new Location(FurnaceBattleRoyale.getWorld(), 0, 252, 0));
                player.setGameMode(GameMode.ADVENTURE);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 9999999, 100, false, false, false));

                player.getInventory().clear();

                ItemStack menuItem = new ItemStack(Material.COMPASS);
                ItemMeta meta = menuItem.getItemMeta();

                meta.setDisplayName(ChatColor.GREEN + "Командное меню");

                menuItem.setItemMeta(meta);

                player.getInventory().setItem(4, menuItem);
                break;
            case PLAYING:
                player.setGameMode(GameMode.SURVIVAL);
                player.removePotionEffect(PotionEffectType.SATURATION);
                player.getInventory().clear();

                player.teleport(LocationUtils.getDownBlock(player.getLocation().subtract(0, 5, 0)));
                break;
            case DEAD:
                if(player.isOnline()) {
                    ItemStack[] drops = player.getInventory().getContents();
                    player.getInventory().clear();
                    player.setGameMode(GameMode.SPECTATOR);

                    for (ItemStack item : drops) {
                        if (item == null) continue;
                        player.getWorld().dropItem(player.getLocation(), item);
                    }

                    event.getFBRPlayer().updateWorldBorder();
                    event.getFBRPlayer().getTeam().playerDied(event.getFBRPlayer());

                    if (!GameConfigManager.CAN_SPECTATE)
                        player.kickPlayer("Вы умерли");
                }
                break;
        }
    }
}
