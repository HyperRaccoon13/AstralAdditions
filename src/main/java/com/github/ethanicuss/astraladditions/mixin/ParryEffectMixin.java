package com.github.ethanicuss.astraladditions.mixin;

import com.github.ethanicuss.astraladditions.effects.parry.ParryEffect;
import com.github.ethanicuss.astraladditions.registry.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
//Lets effects have a start effect
public abstract class ParryEffectMixin {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow @Nullable public abstract LivingEntity getAttacker();

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Inject(method = "damage", at = @At("HEAD"))
    public void damage(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(ModEffects.PARRY)) {
            Entity attacker = (LivingEntity) this.getAttacker();
            int level = this.getStatusEffect(ModEffects.PARRY).getAmplifier();
            ParryEffect.onUserDamaged((LivingEntity) (Object) this, attacker, level);
        }
    }
//    @Inject(method = "applyUpdateEffect", at = @At("HEAD"))
//    public void applyUpdateEffect(LivingEntity entity, int amplifier, CallbackInfo ci) {
//        entity.getAttacker();
//        ThreadLocal<Boolean> threadLocalValue = new ThreadLocal<>();
//        threadLocalValue.set(true);
//        if (threadLocalValue.get()) {
//
//        }
//
}
