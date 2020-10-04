package com.github.thelampgod.readonlycontainers;

import org.bukkit.plugin.java.JavaPlugin;

public final class ReadOnlyContainers extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

}
