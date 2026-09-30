package com.adarshhehe.zoomhehe.config;

import com.adarshhehe.zoomhehe.animation.EasingType;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class ZoomConfigScreen {

    public static Screen create(Screen parent) {
        ZoomConfig cfg = ZoomConfig.get();

        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("zoomhehe.config.title"))

            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("zoomhehe.config.category.general"))

                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("zoomhehe.config.toggle_mode"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.toggle_mode.desc")))
                    .binding(false, () -> cfg.toggleMode, v -> cfg.toggleMode = v)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                .option(Option.<Float>createBuilder()
                    .name(Component.translatable("zoomhehe.config.default_zoom"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.default_zoom.desc")))
                    .binding(4.0f, () -> cfg.defaultZoom, v -> cfg.defaultZoom = v)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).range(1.0f, 10.0f).step(0.25f))
                    .build())

                .option(Option.<Float>createBuilder()
                    .name(Component.translatable("zoomhehe.config.min_zoom"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.min_zoom.desc")))
                    .binding(1.0f, () -> cfg.minZoom, v -> cfg.minZoom = v)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).range(1.0f, 10.0f).step(0.25f))
                    .build())

                .option(Option.<Float>createBuilder()
                    .name(Component.translatable("zoomhehe.config.max_zoom"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.max_zoom.desc")))
                    .binding(10.0f, () -> cfg.maxZoom, v -> cfg.maxZoom = v)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).range(1.0f, 20.0f).step(0.5f))
                    .build())

                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("zoomhehe.config.reset_on_release"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.reset_on_release.desc")))
                    .binding(true, () -> cfg.resetOnRelease, v -> cfg.resetOnRelease = v)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                .build())

            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("zoomhehe.config.category.animation"))

                .option(Option.<Float>createBuilder()
                    .name(Component.translatable("zoomhehe.config.animation_speed"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.animation_speed.desc")))
                    .binding(8.0f, () -> cfg.animationSpeed, v -> cfg.animationSpeed = v)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).range(1.0f, 20.0f).step(0.5f))
                    .build())

                .option(Option.<EasingType>createBuilder()
                    .name(Component.translatable("zoomhehe.config.easing_type"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.easing_type.desc")))
                    .binding(EasingType.EASE_OUT, () -> cfg.easingType, v -> cfg.easingType = v)
                    .controller(opt -> EnumControllerBuilder.create(opt).enumClass(EasingType.class))
                    .build())

                .build())

            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("zoomhehe.config.category.camera"))

                .option(Option.<Float>createBuilder()
                    .name(Component.translatable("zoomhehe.config.mouse_sensitivity"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.mouse_sensitivity.desc")))
                    .binding(1.0f, () -> cfg.mouseSensitivity, v -> cfg.mouseSensitivity = v)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.1f, 1.0f).step(0.05f))
                    .build())

                .option(Option.<Float>createBuilder()
                    .name(Component.translatable("zoomhehe.config.scroll_sensitivity"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.scroll_sensitivity.desc")))
                    .binding(0.5f, () -> cfg.scrollSensitivity, v -> cfg.scrollSensitivity = v)
                    .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.1f, 2.0f).step(0.1f))
                    .build())

                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("zoomhehe.config.cinematic_camera"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.cinematic_camera.desc")))
                    .binding(false, () -> cfg.cinematicCamera, v -> cfg.cinematicCamera = v)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("zoomhehe.config.smooth_camera"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.smooth_camera.desc")))
                    .binding(false, () -> cfg.smoothCamera, v -> cfg.smoothCamera = v)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                .build())

            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("zoomhehe.config.category.misc"))

                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("zoomhehe.config.spyglass_overlay"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.spyglass_overlay.desc")))
                    .binding(false, () -> cfg.spyglassOverlay, v -> cfg.spyglassOverlay = v)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                .option(Option.<Boolean>createBuilder()
                    .name(Component.translatable("zoomhehe.config.zoom_sound"))
                    .description(OptionDescription.of(Component.translatable("zoomhehe.config.zoom_sound.desc")))
                    .binding(false, () -> cfg.zoomSound, v -> cfg.zoomSound = v)
                    .controller(TickBoxControllerBuilder::create)
                    .build())

                .build())

            .save(ZoomConfig::save)
            .build()
            .generateScreen(parent);
    }
}
