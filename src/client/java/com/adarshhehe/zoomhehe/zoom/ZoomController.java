package com.adarshhehe.zoomhehe.zoom;

import com.adarshhehe.zoomhehe.ZoomHeheClient;
import com.adarshhehe.zoomhehe.animation.ZoomAnimator;
import com.adarshhehe.zoomhehe.config.ZoomConfig;
import com.adarshhehe.zoomhehe.input.KeybindManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;

@Environment(EnvType.CLIENT)
public class ZoomController {

    private final ZoomAnimator animator = new ZoomAnimator();

    private boolean zoomActive = false;
    private boolean wasKeyDown = false;
    private float scrollZoomLevel = -1.0f;

    private boolean savedSmoothCamera = false;
    private boolean cinematicSaved = false;

    public void register() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private void onClientTick(Minecraft client) {
        if (client.player == null || client.level == null) return;

        ZoomConfig cfg = ZoomConfig.get();
        boolean keyDown = KeybindManager.ZOOM_KEY.isDown();

        if (cfg.toggleMode) {
            if (keyDown && !wasKeyDown) {
                setZoomActive(!zoomActive, client, cfg);
            }
        } else {
            if (keyDown != zoomActive) {
                setZoomActive(keyDown, client, cfg);
            }
        }

        wasKeyDown = keyDown;

        if (zoomActive) {
            animator.setTarget(1.0f / getTargetZoomLevel(cfg));
        } else {
            animator.setTarget(1.0f);
        }
    }

    private void setZoomActive(boolean active, Minecraft client, ZoomConfig cfg) {
        zoomActive = active;

        if (active) {
            if (!cinematicSaved) {
                savedSmoothCamera = client.options.smoothCamera;
                cinematicSaved = true;
            }
            if (cfg.cinematicCamera || cfg.smoothCamera) {
                client.options.smoothCamera = true;
            }
            if (cfg.zoomSound && client.player != null) {
                client.player.playSound(SoundEvents.SPYGLASS_USE, 0.5f, 1.0f);
            }
        } else {
            if (cinematicSaved) {
                client.options.smoothCamera = savedSmoothCamera;
                cinematicSaved = false;
            }
            if (cfg.resetOnRelease) {
                scrollZoomLevel = -1.0f;
            }
            if (cfg.zoomSound && client.player != null) {
                client.player.playSound(SoundEvents.SPYGLASS_STOP_USING, 0.5f, 1.0f);
            }
        }
    }

    public boolean onScroll(double scrollDelta) {
        if (!zoomActive) return false;
        ZoomConfig cfg = ZoomConfig.get();
        float current = scrollZoomLevel < 0 ? cfg.defaultZoom : scrollZoomLevel;
        float delta = (float) (scrollDelta * cfg.scrollSensitivity);
        scrollZoomLevel = Math.max(cfg.minZoom, Math.min(cfg.maxZoom, current + delta));
        return true;
    }

    private float getTargetZoomLevel(ZoomConfig cfg) {
        float zoom = scrollZoomLevel < 0 ? cfg.defaultZoom : scrollZoomLevel;
        return Math.max(cfg.minZoom, Math.min(cfg.maxZoom, zoom));
    }

    public float getFovMultiplier() {
        return animator.step();
    }

    public boolean isZoomActive() {
        return zoomActive;
    }

    public float getMouseSensitivityMultiplier() {
        if (!zoomActive) return 1.0f;
        ZoomConfig cfg = ZoomConfig.get();
        float fovMult = animator.getCurrent();
        return fovMult * cfg.mouseSensitivity;
    }

    public boolean shouldShowSpyglassOverlay() {
        return zoomActive && ZoomConfig.get().spyglassOverlay;
    }
}
