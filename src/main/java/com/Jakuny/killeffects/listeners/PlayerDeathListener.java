package com.Jakuny.killeffects.listeners;

import com.Jakuny.killeffects.data.PlayerData;
import com.Jakuny.killeffects.data.PlayerDataManager;
import com.Jakuny.killeffects.effects.EffectManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final EffectManager effectManager;
    private final PlayerDataManager dataManager;

    public PlayerDeathListener(EffectManager effectManager, PlayerDataManager dataManager) {
        this.effectManager = effectManager;
        this.dataManager = dataManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        Location spawnLoc = victim.getLocation();
        spawnLoc.setYaw(killer.getLocation().getYaw());

        if (killer != null) {
            PlayerData killerData = dataManager.getPlayerData(killer);

            if (killerData == null || !killerData.isEffectsEnabled()) {
                return;
            }

            String activeEffect = killerData.getActiveEffect();
            if (activeEffect != null && !activeEffect.isEmpty()) {

                effectManager.playEffect(spawnLoc, activeEffect, victim.getName(), killerData.isSoundsEnabled());
            }
        }
    }
}