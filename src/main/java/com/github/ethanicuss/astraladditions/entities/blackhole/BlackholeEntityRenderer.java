package com.github.ethanicuss.astraladditions.entities.blackhole;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlackholeEntityRenderer extends GeoEntityRenderer<BlackholeEntity> {
    public BlackholeEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new BlackholeEntityModel());
    }

    @Override
    public Identifier getTexture(BlackholeEntity entity) {
        return new Identifier(AstralAdditions.MOD_ID, "textures/entity/blackhole/blackhole_texture.png");
    }
}
