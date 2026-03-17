package com.Jakuny.killeffects.effects;

import com.Jakuny.killeffects.KillEffects;
import com.Jakuny.killeffects.utils.ParticleFont; // Импорт нашего нового шрифта
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectManager {

    private final KillEffects plugin;
    private final Map<String, FileConfiguration> loadedEffects = new HashMap<>();

    public EffectManager(KillEffects plugin) {
        this.plugin = plugin;
    }

    public void loadEffects() {
        File effectsFolder = new File(plugin.getDataFolder(), "effects");
        if (!effectsFolder.exists()) {
            effectsFolder.mkdirs();
            String[] defaults = {"lightning.yml", "blood_explosion.yml", "void_pull.yml", "angel_soul.yml", "ez_toxic.yml"};
            for (String fileName : defaults) {
                try {
                    plugin.saveResource("effects/" + fileName, false);
                } catch (Exception ignored) {}
            }
        }

        loadedEffects.clear();
        File[] effectFiles = effectsFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (effectFiles != null) {
            for (File file : effectFiles) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                String id = config.getString("id");
                if (id != null) {
                    loadedEffects.put(id, config);
                    plugin.getLogger().info("[KillEffects] Loaded effect: " + id);
                }
            }
        }
    }

    public void playEffect(Location location, String effectId, String victimName, boolean playSounds) {
        FileConfiguration config = loadedEffects.get(effectId);
        if (config == null) return;

        List<Map<?, ?>> actions = config.getMapList("actions");
        for (Map<?, ?> actionData : actions) {
            int delay = getInt(actionData, "delay", 0);

            new BukkitRunnable() {
                @Override
                public void run() {
                    String type = (String) actionData.get("type");
                    if (type != null && type.equalsIgnoreCase("PLAY_SOUND") && !playSounds) {
                        return;
                    }
                    executeAction(location, actionData, victimName);
                }
            }.runTaskLater(plugin, delay);
        }
    }

    private void executeAction(Location loc, Map<?, ?> data, String victimName) {
        Object typeObj = data.get("type");
        if (!(typeObj instanceof String type)) return;

        switch (type.toUpperCase()) {
            case "PLAY_SOUND":
                playSound(loc, data);
                break;
            case "STRIKE_LIGHTNING":
                loc.getWorld().strikeLightningEffect(loc);
                break;
            case "SPAWN_PARTICLE":
                spawnSimpleParticle(loc, data);
                break;
            case "PARTICLE_CIRCLE":
                drawCircle(loc, data);
                break;
            case "PARTICLE_SPIRAL":
                drawSpiral(loc, data);
                break;
            case "TEXT_DISPLAY":
                spawnTextDisplay(loc, data, victimName);
                break;
            case "PARTICLE_TEXT":
                drawParticleText(loc, data);
                break;
        }
    }


    private void playSound(Location loc, Map<?, ?> data) {
        try {
            Object soundObj = data.get("sound");
            if (!(soundObj instanceof String s)) return;

            Sound sound = Sound.valueOf(s.toUpperCase().replace("MINECRAFT:", "").replace(".", "_"));
            float volume = getFloat(data, "volume", 1.0f);
            float pitch = getFloat(data, "pitch", 1.0f);
            loc.getWorld().playSound(loc, sound, volume, pitch);
        } catch (Exception e) {
            plugin.getLogger().warning("Could not play sound: " + data.get("sound"));
        }
    }

    private void spawnSimpleParticle(Location loc, Map<?, ?> data) {
        try {
            Object partObj = data.get("particle");
            if (!(partObj instanceof String s)) return;

            Particle particle = Particle.valueOf(s.toUpperCase().replace("MINECRAFT:", "").replace(".", "_"));
            int count = getInt(data, "count", 15);
            double speed = getDouble(data, "speed", 0.1);
            loc.getWorld().spawnParticle(particle, loc.clone().add(0, 1, 0), count, 0.5, 0.5, 0.5, speed);
        } catch (Exception e) {
            plugin.getLogger().warning("Could not spawn particle: " + data.get("particle"));
        }
    }

    private void drawCircle(Location loc, Map<?, ?> data) {
        try {
            Object partObj = data.get("particle");
            if (!(partObj instanceof String s)) return;

            Particle p = Particle.valueOf(s.toUpperCase().replace("MINECRAFT:", "").replace(".", "_"));
            double radius = getDouble(data, "radius", 1.5);
            int points = getInt(data, "points", 20);

            for (int i = 0; i < points; i++) {
                double angle = 2 * Math.PI * i / points;
                double x = radius * Math.cos(angle);
                double z = radius * Math.sin(angle);
                loc.getWorld().spawnParticle(p, loc.clone().add(x, 0.2, z), 1, 0, 0, 0, 0);
            }
        } catch (Exception ignored) {}
    }

    private void drawSpiral(Location loc, Map<?, ?> data) {
        try {
            Object partObj = data.get("particle");
            if (!(partObj instanceof String s)) return;

            Particle p = Particle.valueOf(s.toUpperCase().replace("MINECRAFT:", "").replace(".", "_"));
            double radius = getDouble(data, "radius", 1.0);
            double height = getDouble(data, "height", 3.0);
            int points = getInt(data, "points", 50);

            for (int i = 0; i < points; i++) {
                double ratio = (double) i / points;
                double angle = ratio * Math.PI * 4;
                double x = Math.cos(angle) * radius * (1 - ratio);
                double z = Math.sin(angle) * radius * (1 - ratio);
                double y = ratio * height;
                loc.getWorld().spawnParticle(p, loc.clone().add(x, y, z), 1, 0, 0, 0, 0);
            }
        } catch (Exception ignored) {}
    }

    private void spawnTextDisplay(Location loc, Map<?, ?> data, String victimName) {
        Object textObj = data.get("text");
        String rawText = (textObj instanceof String s) ? s : "RIP %victim%";

        String text = ChatColor.translateAlternateColorCodes('&', rawText.replace("%victim%", victimName));
        int duration = getInt(data, "duration", 40);

        TextDisplay display = loc.getWorld().spawn(loc.clone().add(0, 1, 0), TextDisplay.class, (td) -> {
            td.setText(text);
            td.setBillboard(org.bukkit.entity.Display.Billboard.CENTER);
            td.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        });

        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                if (ticks >= duration) {
                    display.remove();
                    cancel();
                    return;
                }
                display.teleport(display.getLocation().add(0, 0.05, 0));
                ticks += 2;
            }
        }.runTaskTimer(plugin, 0, 2);
    }

    private void drawParticleText(Location loc, Map<?, ?> data) {
        try {
            Object textObj = data.get("text");
            if (!(textObj instanceof String text)) return;

            Object partObj = data.get("particle");
            if (!(partObj instanceof String s)) return;
            Particle p = Particle.valueOf(s.toUpperCase().replace("MINECRAFT:", "").replace(".", "_"));

            double scale = getDouble(data, "scale", 0.25);

            float yaw = loc.getYaw();
            double rad = Math.toRadians(-yaw);

            Location startLoc = loc.clone().add(0, 2.0, 0);

            double totalWidth = text.length() * 6 * scale;
            double offsetX = -totalWidth / 2;

            for (char c : text.toCharArray()) {
                boolean[][] matrix = ParticleFont.getMatrix(c);
                if (matrix == null) continue;

                for (int r = 0; r < 5; r++) {
                    for (int col = 0; col < 5; col++) {
                        if (matrix[r][col]) {
                            double localX = (col * scale) + offsetX;
                            double localY = (4 - r) * scale;

                            double rotX = localX * Math.cos(rad);
                            double rotZ = localX * Math.sin(rad);

                            startLoc.getWorld().spawnParticle(p, startLoc.clone().add(rotX, localY, rotZ), 1, 0, 0, 0, 0);
                        }
                    }
                }
                offsetX += 6 * scale;
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Error drawing text: " + e.getMessage());
        }
    }

    private int getInt(Map<?, ?> map, String key, int def) {
        Object obj = map.get(key);
        return (obj instanceof Number n) ? n.intValue() : def;
    }

    private double getDouble(Map<?, ?> map, String key, double def) {
        Object obj = map.get(key);
        return (obj instanceof Number n) ? n.doubleValue() : def;
    }

    private float getFloat(Map<?, ?> map, String key, float def) {
        Object obj = map.get(key);
        return (obj instanceof Number n) ? n.floatValue() : def;
    }

    public Map<String, FileConfiguration> getLoadedEffects() {
        return loadedEffects;
    }
}