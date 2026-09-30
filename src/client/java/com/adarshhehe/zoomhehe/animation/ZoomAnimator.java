package com.adarshhehe.zoomhehe.animation;

import com.adarshhehe.zoomhehe.config.ZoomConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Handles smooth interpolation of the current FOV multiplier toward a target.
 * Uses delta-time based exponential smoothing combined with a configurable easing curve.
 */
@Environment(EnvType.CLIENT)
public class ZoomAnimator {

    private float currentFovMultiplier = 1.0f;
    private float targetFovMultiplier = 1.0f;
    private long lastNanos = -1L;

    public void setTarget(float target) {
        this.targetFovMultiplier = target;
    }

    public float getCurrent() {
        return currentFovMultiplier;
    }

    /**
     * Steps the animation forward using wall-clock delta time.
     * Returns the new current FOV multiplier.
     */
    public float step() {
        long now = System.nanoTime();
        float deltaTime;
        if (lastNanos < 0L) {
            deltaTime = 0.016f; // assume ~60fps on first frame
        } else {
            deltaTime = (now - lastNanos) / 1_000_000_000.0f;
            // Clamp to avoid huge jumps after pauses
            if (deltaTime > 0.1f) deltaTime = 0.1f;
        }
        lastNanos = now;

        float speed = ZoomConfig.get().animationSpeed;
        float diff = targetFovMultiplier - currentFovMultiplier;

        if (Math.abs(diff) < 0.0001f) {
            currentFovMultiplier = targetFovMultiplier;
            return currentFovMultiplier;
        }

        // Exponential decay: frame-rate independent smoothing
        float decay = 1.0f - (float) Math.exp(-speed * deltaTime);
        float rawT = Math.min(1.0f, decay);

        // Apply easing curve to the per-frame progress
        EasingType easing = ZoomConfig.get().easingType;
        float easedT = easing.apply(rawT);

        currentFovMultiplier += diff * easedT;
        return currentFovMultiplier;
    }

    public void snapTo(float value) {
        currentFovMultiplier = value;
        targetFovMultiplier = value;
        lastNanos = -1L;
    }

    public boolean isAtTarget() {
        return Math.abs(currentFovMultiplier - targetFovMultiplier) < 0.0001f;
    }
}