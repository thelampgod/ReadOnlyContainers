package com.github.thelampgod.readonlycontainers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class InventoryListener implements Listener {

    private final ArrayList<Inventory> ignoredInv = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getPlayer().isSneaking()
                || event.useInteractedBlock() == Event.Result.DENY || event.getClickedBlock() == null) {
            return;
        }

        Player p = event.getPlayer();
        Block block = event.getClickedBlock();
        if (block.getState() instanceof Container) {
            Inventory realInventory = ((Container) block.getState()).getInventory();
            p.openInventory(realInventory);
        }
    }

    @EventHandler
    public void onContainerOpen(InventoryOpenEvent event) {
        if (ignoredInv.contains(event.getInventory())
                || event.getInventory().getLocation().getBlock().getType() == Material.ENDER_CHEST) {
            return;
        }

        HumanEntity p = event.getPlayer();
        Inventory realInventory = event.getInventory();
        Inventory fakeInventory;
        // this is really stupid i hate bukkit
        if (realInventory.getType() == InventoryType.CHEST) {
            fakeInventory = Bukkit.createInventory(p, realInventory.getSize(), realInventory.getType().getDefaultTitle());
        } else {
            fakeInventory = Bukkit.createInventory(p, realInventory.getType(), realInventory.getType().getDefaultTitle());
        }
        fakeInventory.setContents(realInventory.getContents());


        ignoredInv.add(fakeInventory);
        p.openInventory(fakeInventory);

        if (!ignoredInv.contains(event.getInventory())) {
            event.setCancelled(true);
        }
    }

}
