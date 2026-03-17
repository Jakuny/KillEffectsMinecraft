package com.Jakuny.killeffects.listeners;

import com.Jakuny.killeffects.data.PlayerData;
import com.Jakuny.killeffects.data.PlayerDataManager;
import com.Jakuny.killeffects.effects.EffectManager;
import com.Jakuny.killeffects.gui.EffectsGUI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InventoryListener implements Listener {

    private final EffectManager effectManager;
    private final PlayerDataManager dataManager;

    public InventoryListener(EffectManager effectManager, PlayerDataManager dataManager) {
        this.effectManager = effectManager;
        this.dataManager = dataManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(EffectsGUI.GUI_NAME)) return;

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType().isAir()) return;

        PlayerData data = dataManager.getPlayerData(player);
        Map<String, FileConfiguration> effects = effectManager.getLoadedEffects();

        for (String id : effects.keySet()) {
            FileConfiguration config = effects.get(id);
            String effectName = ChatColor.translateAlternateColorCodes('&', config.getString("display-name", id));

            if (clicked.getItemMeta().getDisplayName().contains(ChatColor.stripColor(effectName))) {

                String permission = config.getString("permission");
                if (permission != null && !permission.isEmpty() && !player.hasPermission(permission)) {
                    player.sendMessage(ChatColor.RED + "This effect is locked! Purchase it to unlock.");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }

                data.setActiveEffect(id);
                player.closeInventory();

                String msg = ChatColor.GREEN + "Selected effect: " + ChatColor.GOLD + ChatColor.stripColor(effectName);
                player.sendMessage(msg);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.2f);
                return;
            }
        }
    }
}