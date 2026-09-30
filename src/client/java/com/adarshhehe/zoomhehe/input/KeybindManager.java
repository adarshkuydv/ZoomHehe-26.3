package com.adarshhehe.zoomhehe.input;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;

@Environment(EnvType.CLIENT)
public class KeybindManager {

    public static final KeyMapping.Category ZOOM_CATEGORY =
        KeyMapping.Category.register(Identifier.fromNamespaceAndPath("zoomhehe", "main"));

    public static KeyMapping ZOOM_KEY;

    public static void register() {
        ZOOM_KEY = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.zoomhehe.zoom",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_C,
            ZOOM_CATEGORY
        ));
    }
}
