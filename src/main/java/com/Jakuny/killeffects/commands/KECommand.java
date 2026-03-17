package com.Jakuny.killeffects.commands;

import com.Jakuny.killeffects.KillEffects;
import com.Jakuny.killeffects.data.PlayerData;
import com.Jakuny.killeffects.data.PlayerDataManager;
import com.Jakuny.killeffects.gui.EffectsGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KECommand implements CommandExecutor, TabCompleter {

    private final KillEffects plugin;
    private final PlayerDataManager dataManager;
    private final EffectsGUI gui;

    public KECommand(KillEffects plugin, PlayerDataManager dataManager, EffectsGUI gui) {
        this.plugin = plugin;
        this.dataManager = dataManager;
        this.gui = gui;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("killeffects.admin")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to reload the plugin!");
                return true;
            }

            plugin.reloadConfig();
            plugin.getEffectManager().loadEffects();
            sender.sendMessage(ChatColor.GOLD + "[KillEffects] " + ChatColor.GREEN + "Configuration and effects reloaded!");
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        if (args.length == 0) {
            gui.openInventory(player);
            return true;
        }

        PlayerData data = dataManager.getPlayerData(player);
        if (data == null) {
            player.sendMessage(ChatColor.RED + "Data is still loading, please wait...");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "toggle":
                data.setEffectsEnabled(!data.isEffectsEnabled());
                player.sendMessage(data.isEffectsEnabled() ? ChatColor.GREEN + "✔ Effects enabled" : ChatColor.RED + "✘ Effects disabled");
                break;

            case "sound":
                data.setSoundsEnabled(!data.isSoundsEnabled());
                player.sendMessage(data.isSoundsEnabled() ? ChatColor.GREEN + "✔ Sounds enabled" : ChatColor.RED + "✘ Sounds disabled");
                break;

            case "help":
            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("toggle");
            completions.add("sound");
            completions.add("help");
            if (sender.hasPermission("killeffects.admin")) {
                completions.add("reload");
            }

            String currentArg = args[0].toLowerCase();
            return completions.stream()
                    .filter(s -> s.startsWith(currentArg))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    private void sendHelp(Player player) {
        player.sendMessage(ChatColor.GRAY + "------ " + ChatColor.GOLD + "KillEffects Help" + ChatColor.GRAY + " ------");
        player.sendMessage(ChatColor.YELLOW + "/ke" + ChatColor.WHITE + " - Open menu");
        player.sendMessage(ChatColor.YELLOW + "/ke toggle" + ChatColor.WHITE + " - Toggle visuals");
        player.sendMessage(ChatColor.YELLOW + "/ke sound" + ChatColor.WHITE + " - Toggle sounds");
        if (player.hasPermission("killeffects.admin")) {
            player.sendMessage(ChatColor.RED + "/ke reload" + ChatColor.WHITE + " - Reload files");
        }
        player.sendMessage(ChatColor.GRAY + "-------------------------------");
    }
}