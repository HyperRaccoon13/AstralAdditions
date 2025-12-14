package com.github.ethanicuss.astraladditions.entities.blackhole;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BlackholeEntityModel extends AnimatedGeoModel<BlackholeEntity> {
    @Override
    public Identifier getModelLocation(BlackholeEntity object) {
        return new Identifier(AstralAdditions.MOD_ID, "geo/blackhole.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BlackholeEntity object) {
        return new Identifier(AstralAdditions.MOD_ID, "textures/entity/blackhole/blackhole_texture.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BlackholeEntity animatable) {
        return new Identifier(AstralAdditions.MOD_ID, "animations/blackhole.animation.json");
    }

}
