# KillEffects

**KillEffects** is a high-performance, fully customizable cosmetic system for Minecraft servers (Paper/Spigot 1.21.1+). It allows players to collect and play spectacular visual and sound effects after defeating their opponents.

## 🌟 Features
*   **Custom Effect Engine:** Create unlimited unique effects using simple YAML files.
*   **Advanced Visuals:** Supports particles, sounds, geometric shapes (circles, spirals), and 1.21 `TextDisplay` holograms.
*   **Monetization Ready:** Built-in permission system allows you to sell effects in your store.
*   **Interactive GUI:** Intuitive menu (`/ke`) for players to preview and select effects.
*   **Optimized:** Lightweight code designed for modern Paper/Spigot performance.

## 🎮 Commands
| Command | Description | Permission |
| :--- | :--- | :--- |
| `/ke` | Opens the effects selection menu | `killeffects.gui` |
| `/ke toggle` | Toggle visual effects | `killeffects.gui` |
| `/ke sound` | Toggle effect sounds | `killeffects.gui` |
| `/ke reload` | Reload all configs and effects | `killeffects.admin` |

## 🛠 Creating Custom Effects
Create a new `.yml` file in `/plugins/KillEffects/effects/`. 

### Available Actions:
- `PLAY_SOUND`: `sound` (e.g., `entity.witch.celebrate`), `volume`, `pitch`
- `STRIKE_LIGHTNING`: Spawns lightning.
- `SPAWN_PARTICLE`: `particle`, `count`, `speed`
- `PARTICLE_CIRCLE`: `particle`, `radius`, `points`
- `PARTICLE_SPIRAL`: `particle`, `radius`, `height`, `points`
- `PARTICLE_TEXT`: `text`, `particle`, `scale`
- `TEXT_DISPLAY`: `text`, `duration`

### Example `ez_toxic.yml`:
```yaml
id: "ez_toxic"
display-name: "&6🔥 EZ Toxic"
gui-item: "minecraft:wither_skeleton_skull"
permission: "killeffects.effect.ez"
actions:
  - type: PLAY_SOUND
    sound: "minecraft:entity.witch.celebrate"
    volume: 1.0
    pitch: 1.2
    delay: 0
  - type: PARTICLE_TEXT
    text: "EZ"
    particle: "minecraft:flame"
    scale: 0.25
    delay: 5Ч
```

---
*Developed by Jakuny*
