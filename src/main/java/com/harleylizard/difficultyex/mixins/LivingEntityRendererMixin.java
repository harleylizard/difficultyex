package com.harleylizard.difficultyex.mixins;

import com.harleylizard.difficultyex.common.DifficultyExComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> extends EntityRenderer<T> {

    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("TAIL"))
    public void difficultyex$render(T livingEntity, float dontUse, float dontUse2, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        var component = livingEntity.getComponent(DifficultyExComponents.Companion.getEntityLevel());
        var translation = Component.translatable("difficultyex.entity.level", component.getLevel());

        poseStack.pushPose();
        var f = livingEntity.getNameTagOffsetY();
        poseStack.translate(0.0f, f, 0.0f);
        poseStack.mulPose(entityRenderDispatcher.cameraOrientation());
        var scale = 0.025f;
        poseStack.scale(-scale, -scale, scale);
        var matrix4f = poseStack.last().pose();
        var g = Minecraft.getInstance().options.getBackgroundOpacity(0.25f);
        var k = (int)(g * 255.0f) << 24;
        var font = this.getFont();
        var h = -font.width(translation) / 2;
        font.drawInBatch(translation, h, 0.0f, 553648127, false, matrix4f, multiBufferSource, Font.DisplayMode.NORMAL, k, i);
        poseStack.popPose();
    }
}
