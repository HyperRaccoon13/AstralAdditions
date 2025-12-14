package com.github.ethanicuss.astraladditions.mixin;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.github.ethanicuss.astraladditions.registry.ModData;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow @Final private MinecraftClient client;

	private static final Identifier shimmerBubbleTexture = new Identifier(AstralAdditions.MOD_ID, "textures/gui/shimmer_air_bubbles.png");

	private static boolean isBubbleQuad(int u, int v, int w, int h) {
		return w == 9 && h == 9 && v == 18 && (u == 16 || u == 25);
	}

	private boolean isEyeInShimmer(PlayerEntity player) {
		if (player == null) return false;
		Vec3d eye = player.getCameraPosVec(1.0f).add(0, -0.5, 0);
		BlockPos pos = new BlockPos(eye.x, eye.y, eye.z);
		return player.world.getFluidState(pos).isIn(ModData.SHIMMER_TAG);
	}

	@Redirect(method = "renderStatusBars",
			at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"),
			require = 0
		)
	private void renderShimmerBubbles(InGameHud self, MatrixStack m, int x, int y, int u, int v, int w, int h) {
		if (isEyeInShimmer(client.player) && isBubbleQuad(u, v, w, h)) {
			RenderSystem.setShaderTexture(0, shimmerBubbleTexture);
			self.drawTexture(m, x, y, u, v, w, h);
			RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
		} else {
			self.drawTexture(m, x, y, u, v, w, h);
		}
	}

}