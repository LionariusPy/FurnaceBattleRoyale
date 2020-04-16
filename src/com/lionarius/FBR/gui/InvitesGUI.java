package com.lionarius.FBR.gui;

import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.invites.Invite;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InvitesGUI extends AbstractGUI{

    public int page;

    public InvitesGUI(FBRPlayer fbrPlayer, int page) {
        super("Приглашения", 9*5);
        this.page = page;

        int maxPages = (int) Math.ceil((float) fbrPlayer.getInvites().size() / 36);

        ExecutableGUIAction inviteAction = (player, action, slot) ->
        {
            InvitesGUI currentGUI = (InvitesGUI) player.getCurrentGUI();

            Invite clickedInvite = player.getInvites().get(slot + currentGUI.page * 36);
            if(action == InventoryAction.PICKUP_ALL) {
                player.acceptInvite(clickedInvite);
                player.closeInventory();
                player.getPlayer().sendMessage("Вы приняли приглашение игрока " + clickedInvite.getFromTeam().getLeader().getName());
            }
            else if(action == InventoryAction.PICKUP_HALF) {
                player.declineInvite(clickedInvite);
                new InvitesGUI(player, currentGUI.page);
            }
        };

        for(int i = 36*page; i < Math.min(fbrPlayer.getInvites().size(), 36*(page + 1)); i++)
        {
            setItem(getInviteItem(fbrPlayer.getInvites().get(i)), i - (36 * page), inviteAction);
        }

        ExecutableGUIAction backAction = (player, action, slot) ->
        {
            new PlayerListGUI(player, page - 1);
        };

        ExecutableGUIAction nextAction = (player, action, slot) ->
        {
            new PlayerListGUI(player, page + 1);
        };

        if(page > 0) setItem(getBackItem(), 36, backAction);
        if(page < maxPages - 1) setItem(getNextItem(), 44, nextAction);

        ExecutableGUIAction menuAction = (player, action, slot) ->
        {
            new MenuGUI(player);
        };
        setItem(getMenuItem(), 40, menuAction);

        fbrPlayer.openInventory(this);
    }

    private ItemStack getInviteItem(Invite invite)
    {
        ItemStack inviteItem = new ItemStack(Material.PAPER);
        ItemMeta inviteItemMeta = inviteItem.getItemMeta();
//        inviteItemMeta.setOwningPlayer(fbrPlayer.getPlayer());
        inviteItemMeta.setDisplayName("Приглашение от " + invite.getFromTeam().getLeader().getName());
        List<String> lore = new ArrayList<>();
        lore.add("ЛКМ - принять");
        lore.add("ПКМ - отклонить");
        inviteItemMeta.setLore(lore);
        inviteItem.setItemMeta(inviteItemMeta);
        return inviteItem;
    }

    private ItemStack getBackItem()
    {
        ItemStack backItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta backItemMeta = backItem.getItemMeta();
        backItemMeta.setDisplayName("Назад");
        backItem.setItemMeta(backItemMeta);
        return backItem;
    }

    private ItemStack getNextItem()
    {
        ItemStack nextItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta nextItemMeta = nextItem.getItemMeta();
        nextItemMeta.setDisplayName("Вперед");
        nextItem.setItemMeta(nextItemMeta);
        return nextItem;
    }

    private ItemStack getMenuItem()
    {
        ItemStack menuItem = new ItemStack(Material.COMPASS);
        ItemMeta menuItemMeta = menuItem.getItemMeta();
        menuItemMeta.setDisplayName("В меню");
        menuItem.setItemMeta(menuItemMeta);
        return menuItem;
    }
}
