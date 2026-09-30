package com.adarshhehe.zoomhehe;

import com.adarshhehe.zoomhehe.config.ZoomConfig;
import com.adarshhehe.zoomhehe.input.KeybindManager;
import com.adarshhehe.zoomhehe.hud.SpyglassOverlay;
import com.adarshhehe.zoomhehe.zoom.ZoomController;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class ZoomHeheClient implements ClientModInitializer {

    public static final String MOD_ID = "zoomhehe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ZoomController zoomController;

    @Override
    public void onInitializeClient() {
        ZoomConfig.load();
        KeybindManager.register();
        zoomController = new ZoomController();
        zoomController.register();
        SpyglassOverlay.register(zoomController);
        LOGGER.info("ZoomHehe initialized.");
    }

    public static ZoomController getZoomController() {
        return zoomController;
    }
}
