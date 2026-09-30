package com.adarshhehe.zoomhehe.hud;

import com.adarshhehe.zoomhehe.zoom.ZoomController;
import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SpyglassOverlay {

    private static final Identifier SCOPE =
            Identifier.withDefaultNamespace("textures/misc/spyglass_scope.png");
    private static final int BLACK = 0xFF000000;

    private static ZoomController controller;

    public static void register(ZoomController zoomController) {
        controller = zoomController;
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.HOTBAR,
                Identifier.fromNamespaceAndPath("zoomhehe", "spyglass_overlay"),
                SpyglassOverlay::extract);
    }

    private static void extract(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        if (controller == null || !controller.shouldShowSpyglassOverlay()) return;

        Minecraft mc = Minecraft.getInstance();
        if (!mc.options.getCameraType().isFirstPerson()) return;

        Window window = mc.getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();

        // Vanilla spyglass jaisa square scope, screen ke beech me
        int size = Math.min(width, height);
        int left = (width - size) / 2;
        int top = (height - size) / 2;
        int right = left + size;
        int bottom = top + size;

        graphics.blit(RenderPipelines.GUI_TEXTURED, SCOPE,
                left, top, 0.0f, 0.0f, size, size, size, size);

        // Scope ke bahar ka hissa kaala
        graphics.fill(0, bottom, width, height, BLACK);
        graphics.fill(0, 0, width, top, BLACK);
        graphics.fill(0, top, left, bottom, BLACK);
        graphics.fill(right, top, width, bottom, BLACK);
    }
}