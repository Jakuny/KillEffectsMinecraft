package com.Jakuny.killeffects;

import com.Jakuny.killeffects.commands.KECommand;
import com.Jakuny.killeffects.data.PlayerDataManager;
import com.Jakuny.killeffects.effects.EffectManager;
import com.Jakuny.killeffects.gui.EffectsGUI;
import com.Jakuny.killeffects.listeners.InventoryListener;
import com.Jakuny.killeffects.listeners.PlayerConnectionListener;
import com.Jakuny.killeffects.listeners.PlayerDeathListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class KillEffects extends JavaPlugin {

    private EffectManager effectManager;
    private PlayerDataManager dataManager;
    private EffectsGUI gui;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.effectManager = new EffectManager(this);
        this.dataManager = new PlayerDataManager(this);

        this.gui = new EffectsGUI(effectManager, dataManager);

        effectManager.loadEffects();
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(effectManager, dataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(dataManager), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(effectManager, dataManager), this);

        KECommand keCommand = new KECommand(this, dataManager, gui);
        if (getCommand("killeffects") != null) {
            Objects.requireNonNull(getCommand("killeffects")).setExecutor(keCommand);
            Objects.requireNonNull(getCommand("killeffects")).setTabCompleter(keCommand);
        }

        getLogger().info("=======================================");
        getLogger().info("KillEffects v" + getDescription().getVersion() + " by Jakuny enabled!");
        getLogger().info("Modern 1.21.1 Engine initialized.");
        getLogger().info("=======================================");
    }

    @Override
    public void onDisable() {
        if (dataManager != null) {
            for (Player player : getServer().getOnlinePlayers()) {
                dataManager.savePlayerData(player);
                dataManager.unloadPlayerData(player);
            }
        }
        getLogger().info("KillEffects disabled. All player data has been safely saved.");
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public PlayerDataManager getDataManager() {
        return dataManager;
    }

    public EffectsGUI getGui() {
        return gui;
    }
}