package com.adarshhehe.zoomhehe.mixin;

import com.adarshhehe.zoomhehe.ZoomHeheClient;
import com.adarshhehe.zoomhehe.zoom.ZoomController;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MouseHandler.class)
public abstract class MouseMixin {

    @Inject(
        method = "onScroll(JDD)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        ZoomController controller = ZoomHeheClient.getZoomController();
        if (controller == null) return;
        if (controller.onScroll(vertical)) {
            ci.cancel();
        }
    }

    @ModifyArg(
        method = "turnPlayer(D)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
        ),
        index = 0
    )
    private double modifyLookX(double deltaX) {
        ZoomController controller = ZoomHeheClient.getZoomController();
        if (controller == null) return deltaX;
        return deltaX * controller.getMouseSensitivityMultiplier();
    }

    @ModifyArg(
        method = "turnPlayer(D)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
        ),
        index = 1
    )
    private double modifyLookY(double deltaY) {
        ZoomController controller = ZoomHeheClient.getZoomController();
        if (controller == null) return deltaY;
        return deltaY * controller.getMouseSensitivityMultiplier();
    }
}
