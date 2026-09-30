package com.adarshhehe.zoomhehe.mixin;

import com.adarshhehe.zoomhehe.ZoomHeheClient;
import com.adarshhehe.zoomhehe.zoom.ZoomController;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public abstract class GameRendererMixin {

    @Inject(
        method = "calculateFov(F)F",
        at = @At("RETURN"),
        cancellable = true
    )
    private void onCalculateFov(float partialTick, CallbackInfoReturnable<Float> cir) {
        ZoomController controller = ZoomHeheClient.getZoomController();
        if (controller == null) return;
        float fovMultiplier = controller.getFovMultiplier();
        if (Math.abs(fovMultiplier - 1.0f) > 0.0001f) {
            cir.setReturnValue(cir.getReturnValue() * fovMultiplier);
        }
    }
}
