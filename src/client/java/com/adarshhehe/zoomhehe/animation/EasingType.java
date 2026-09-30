package com.adarshhehe.zoomhehe.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public enum EasingType {

    LINEAR("zoomhehe.easing.linear") {
        @Override public float apply(float t) { return t; }
    },
    EASE_IN("zoomhehe.easing.ease_in") {
        @Override public float apply(float t) { return t * t; }
    },
    EASE_OUT("zoomhehe.easing.ease_out") {
        @Override public float apply(float t) { return t * (2.0f - t); }
    },
    EASE_IN_OUT("zoomhehe.easing.ease_in_out") {
        @Override
        public float apply(float t) {
            return t < 0.5f ? 2.0f * t * t : -1.0f + (4.0f - 2.0f * t) * t;
        }
    },
    EXPONENTIAL("zoomhehe.easing.exponential") {
        @Override
        public float apply(float t) {
            if (t <= 0.0f) return 0.0f;
            if (t >= 1.0f) return 1.0f;
            return (float)(Math.pow(2.0, 10.0 * t - 10.0));
        }
    };

    private final String translationKey;

    EasingType(String translationKey) { this.translationKey = translationKey; }

    public abstract float apply(float t);

    public int getId() { return ordinal(); }

    public String getTranslationKey() { return translationKey; }

    public Component getDisplayName() {
        return Component.translatable(translationKey);
    }

    @Override
    public String toString() { return getDisplayName().getString(); }
}
