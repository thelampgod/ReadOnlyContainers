package com.github.thelampgod.readonlycontainers;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReadOnlyContainers extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

}
