package com.github.ethanicuss.astraladditions.mixin;


import net.minecraft.entity.projectile.ShulkerBulletEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

//Nerfs shulker bullets to only grant levitation for 2 seconds
@Mixin(ShulkerBulletEntity.class)
public class NerfedShulkers {
    @ModifyArg( method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;<init>(Lnet/minecraft/entity/effect/StatusEffect;I)V"), index = 1)
    public int duration(int duration) {
        return 40;
    }
}
