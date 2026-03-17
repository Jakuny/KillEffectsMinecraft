package com.Jakuny.killeffects.listeners;

import com.Jakuny.killeffects.data.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    private final PlayerDataManager dataManager;

    public PlayerConnectionListener(PlayerDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        dataManager.loadPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        dataManager.unloadPlayerData(event.getPlayer());
    }
}