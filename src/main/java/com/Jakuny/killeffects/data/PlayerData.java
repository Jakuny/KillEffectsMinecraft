package com.Jakuny.killeffects.data;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    private boolean effectsEnabled = true;
    private boolean soundsEnabled = true;
    private String activeEffect = "lightning";
    private List<String> unlockedEffects = new ArrayList<>();

    public boolean isEffectsEnabled() {
        return effectsEnabled;
    }

    public void setEffectsEnabled(boolean effectsEnabled) {
        this.effectsEnabled = effectsEnabled;
    }

    public boolean isSoundsEnabled() {
        return soundsEnabled;
    }

    public void setSoundsEnabled(boolean soundsEnabled) {
        this.soundsEnabled = soundsEnabled;
    }

    public String getActiveEffect() {
        return activeEffect;
    }

    public void setActiveEffect(String activeEffect) {
        this.activeEffect = activeEffect;
    }

    public List<String> getUnlockedEffects() {
        return unlockedEffects;
    }

    public void setUnlockedEffects(List<String> unlockedEffects) {
        this.unlockedEffects = unlockedEffects;
    }
}