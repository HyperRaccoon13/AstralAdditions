package com.github.ethanicuss.astraladditions.entities.shimmerblaze;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.github.ethanicuss.astraladditions.registry.ModEntityModelLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(value=EnvType.CLIENT)
public class ShimmerBlazeEntityRenderer
        extends MobEntityRenderer<ShimmerBlazeEntity, ShimmerBlazeEntityModel<ShimmerBlazeEntity>> {
    private static final Identifier TEXTURE = new Identifier(AstralAdditions.MOD_ID, "textures/entity/shimmer_blaze/shimmer_blaze.png");

    public ShimmerBlazeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ShimmerBlazeEntityModel(context.getPart(ModEntityModelLayer.MODEL_SHIMMER_BLAZE_LAYER)), 0.5f);
    }

    @Override
    protected int getBlockLight(ShimmerBlazeEntity blazeEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    protected void scale(ShimmerBlazeEntity blazeEntity, MatrixStack matrixStack, float f) {
        float g = 1.5f;
        matrixStack.scale(g, g, g);
        super.scale(blazeEntity, matrixStack, f);
    }

    @Override
    public Identifier getTexture(ShimmerBlazeEntity blazeEntity) {
        return TEXTURE;
    }
}