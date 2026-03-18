package com.Jakuny.killeffects.data;

import com.Jakuny.killeffects.KillEffects;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final KillEffects plugin;
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private final File dataFolder;

    public PlayerDataManager(KillEffects plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public void loadPlayerData(Player player) {
        File playerFile = new File(dataFolder, player.getUniqueId() + ".yml");
        if (!playerFile.exists()) {
            PlayerData data = new PlayerData();
            playerDataMap.put(player.getUniqueId(), data);
            savePlayerData(player);
            return;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        PlayerData data = new PlayerData();
        data.setEffectsEnabled(config.getBoolean("effects-enabled", true));
        data.setSoundsEnabled(config.getBoolean("sounds-enabled", true));
        data.setActiveEffect(config.getString("active-effect", "lightning"));
        data.setUnlockedEffects(config.getStringList("unlocked-effects"));

        playerDataMap.put(player.getUniqueId(), data);
    }

    public void savePlayerData(Player player) {
        PlayerData data = playerDataMap.get(player.getUniqueId());
        if (data == null) return;

        File playerFile = new File(dataFolder, player.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        config.set("effects-enabled", data.isEffectsEnabled());
        config.set("sounds-enabled", data.isSoundsEnabled());
        config.set("active-effect", data.getActiveEffect());
        config.set("unlocked-effects", data.getUnlockedEffects());

        try {
            config.save(playerFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save data for player" + player.getName());
            e.printStackTrace();
        }
    }

    public void unloadPlayerData(Player player) {
        savePlayerData(player);
        playerDataMap.remove(player.getUniqueId());
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }
}
