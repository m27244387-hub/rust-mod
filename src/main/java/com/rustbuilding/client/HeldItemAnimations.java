package com.rustbuilding.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * First-person Rust-style held-item motion.
 * Called by Forge's IClientItemExtensions hand-transform hook.
 */
public final class HeldItemAnimations {
    private HeldItemAnimations() {}

    public static boolean applyPlan(PoseStack pose, Player player, HumanoidArm arm,
                                    ItemStack stack, float partialTick,
                                    float equip, float swing) {
        float time = player.tickCount + partialTick;
        float side = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        float idle = Mth.sin(time * 0.075F);
        float breathe = Mth.sin(time * 0.045F);

        // Blueprint is held forward and slightly open, with a gentle paper sway.
        pose.translate(side * 0.035F, -0.045F + idle * 0.008F, -0.12F);
        pose.mulPose(Axis.YP.rotationDegrees(side * (8.0F + idle * 2.2F)));
        pose.mulPose(Axis.XP.rotationDegrees(-13.0F + breathe * 1.8F));
        pose.mulPose(Axis.ZP.rotationDegrees(side * (7.0F + idle * 2.0F)));

        // Raise the plan into view as the equip animation completes.
        float raise = 1.0F - Mth.clamp(equip, 0.0F, 1.0F);
        pose.translate(0.0F, raise * 0.28F, raise * 0.12F);

        // Small snap when the player places a building piece.
        if (swing > 0.0F) {
            float hit = Mth.sin(Mth.sqrt(swing) * Mth.PI);
            pose.mulPose(Axis.XP.rotationDegrees(-hit * 11.0F));
            pose.translate(0.0F, -hit * 0.035F, hit * 0.045F);
        }
        return true;
    }

    public static boolean applyHammer(PoseStack pose, Player player, HumanoidArm arm,
                                      ItemStack stack, float partialTick,
                                      float equip, float swing) {
        float side = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        float time = player.tickCount + partialTick;
        float idle = Mth.sin(time * 0.11F);

        // Resting pose: hammer head sits high, ready for a downward strike.
        pose.translate(side * 0.055F, -0.075F + idle * 0.004F, -0.10F);
        pose.mulPose(Axis.YP.rotationDegrees(side * 13.0F));
        pose.mulPose(Axis.XP.rotationDegrees(-18.0F));
        pose.mulPose(Axis.ZP.rotationDegrees(side * (9.0F + idle * 1.4F)));

        float raise = 1.0F - Mth.clamp(equip, 0.0F, 1.0F);
        pose.translate(0.0F, raise * 0.32F, raise * 0.10F);

        // Smooth wind-up, strike and recovery driven by Minecraft's swing progress.
        if (swing > 0.0F) {
            float root = Mth.sqrt(swing);
            float windup = Mth.sin(root * Mth.PI);
            float strike = Mth.sin(swing * Mth.PI);
            pose.mulPose(Axis.XP.rotationDegrees(-windup * 48.0F + strike * 18.0F));
            pose.mulPose(Axis.ZP.rotationDegrees(side * windup * 13.0F));
            pose.translate(0.0F, windup * 0.075F - strike * 0.035F, strike * 0.11F);
        }
        return true;
    }
}
