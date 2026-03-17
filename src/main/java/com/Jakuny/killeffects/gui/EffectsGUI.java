package com.Jakuny.killeffects.gui;

import com.Jakuny.killeffects.data.PlayerData;
import com.Jakuny.killeffects.data.PlayerDataManager;
import com.Jakuny.killeffects.effects.EffectManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EffectsGUI {

    private final EffectManager effectManager;
    private final PlayerDataManager dataManager;
    public static final String GUI_NAME = ChatColor.BLACK + "Choosing Kill Effects";

    public EffectsGUI(EffectManager effectManager, PlayerDataManager dataManager) {
        this.effectManager = effectManager;
        this.dataManager = dataManager;
    }

    public void openInventory(Player player) {
        Map<String, FileConfiguration> effects = effectManager.getLoadedEffects();
        PlayerData data = dataManager.getPlayerData(player);

        Inventory inv = Bukkit.createInventory(null, 54, GUI_NAME);

        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        if (fillerMeta != null) {
            fillerMeta.setDisplayName(" ");
            filler.setItemMeta(fillerMeta);
        }
        for (int i = 0; i < 54; i++) inv.setItem(i, filler);

        int slot = 10;

        for (String id : effects.keySet()) {
            FileConfiguration config = effects.get(id);

            String permission = config.getString("permission");
            boolean hasPermission = (permission == null || permission.isEmpty() || player.hasPermission(permission));

            Material material;
            if (!hasPermission) {
                material = Material.BARRIER;
            } else {
                String matName = config.getString("gui-item", "PAPER");
                material = Material.matchMaterial(matName);
                if (material == null) material = Material.PAPER;
            }

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;

            String displayName = ChatColor.translateAlternateColorCodes('&', config.getString("display-name", id));
            boolean isActive = id.equals(data.getActiveEffect());

            if (!hasPermission) {
                meta.setDisplayName(ChatColor.RED + "🔒 " + ChatColor.stripColor(displayName));
            } else {
                meta.setDisplayName(displayName + (isActive ? ChatColor.GREEN + " [SELECTED]" : ""));
            }

            List<String> lore = new ArrayList<>();
            for (String line : config.getStringList("description")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            lore.add("");

            if (!hasPermission) {
                lore.add(ChatColor.RED + "✕ Blocked");
                lore.add(ChatColor.GRAY + "Required: " + ChatColor.WHITE + permission);
                lore.add(ChatColor.GRAY + "Buy in the donation shop!");
            } else if (isActive) {
                lore.add(ChatColor.GREEN + "✔ This effect is already active.");
            } else {
                lore.add(ChatColor.YELLOW + "» Click to select");
            }

            meta.setLore(lore);
            item.setItemMeta(meta);

            inv.setItem(slot, item);

            slot++;
            if ((slot + 1) % 9 == 0) slot += 2;
            if (slot >= 44) break;
        }

        player.openInventory(inv);
    }
}