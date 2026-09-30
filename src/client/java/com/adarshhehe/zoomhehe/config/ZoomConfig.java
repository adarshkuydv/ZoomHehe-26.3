package com.adarshhehe.zoomhehe.config;

import com.adarshhehe.zoomhehe.ZoomHeheClient;
import com.adarshhehe.zoomhehe.animation.EasingType;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class ZoomConfig {

    public static final ConfigClassHandler<ZoomConfig> HANDLER =
        ConfigClassHandler.createBuilder(ZoomConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                .setPath(Path.of("config", "zoomhehe.json"))
                .build())
            .build();

    @SerialEntry public boolean toggleMode = false;
    @SerialEntry public float defaultZoom = 4.0f;
    @SerialEntry public float minZoom = 1.0f;
    @SerialEntry public float maxZoom = 10.0f;
    @SerialEntry public boolean resetOnRelease = true;

    @SerialEntry public float animationSpeed = 8.0f;
    @SerialEntry public EasingType easingType = EasingType.EASE_OUT;

    @SerialEntry public float mouseSensitivity = 1.0f;
    @SerialEntry public float scrollSensitivity = 0.5f;
    @SerialEntry public boolean cinematicCamera = false;
    @SerialEntry public boolean smoothCamera = false;

    @SerialEntry public boolean spyglassOverlay = false;
    @SerialEntry public boolean zoomSound = true;

    public static ZoomConfig get() { return HANDLER.instance(); }
    public static void load() { HANDLER.load(); }
    public static void save() { HANDLER.save(); }
}
