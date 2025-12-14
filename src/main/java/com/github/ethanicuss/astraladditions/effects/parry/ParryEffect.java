package com.github.ethanicuss.astraladditions.effects.parry;

import com.github.ethanicuss.astraladditions.registry.ModEffects;
import com.github.ethanicuss.astraladditions.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.UseAction;
import org.jetbrains.annotations.Nullable;


public class ParryEffect extends StatusEffect{
    public ParryEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
//        Entity attacker = entity.getAttacker();
//        onUserDamaged(entity, attacker, amplifier);
        super.applyUpdateEffect(entity, amplifier);
    }

    public static void onUserDamaged (LivingEntity user, Entity attacker, int level) {
            if (attacker != null) {
                    attacker.damage(DamageSource.thorns(user), (float)getDamageAmount(level));
                    user.world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.NEUTRAL, 0.5f, 1.4f / (user.world.getRandom().nextFloat() * 0.4f + 0.8f));
                    ModUtils.spawnForcedParticles((ServerWorld)user.world, ParticleTypes.END_ROD, user.getX(), user.getY(), user.getZ(), 20, 0.8, 0.8, 0.8, 0.7);
                    user.heal(level+5);
            }
        user.setAttacker(null);
    }

    public static boolean parryWindow(LivingEntity user) {
        int lastAttacked = user.age-user.getLastAttackedTime();
        return user.getLastAttackedTime() >= user.age - 20;
    }

    public static int getDamageAmount(int amplifier) {
        return amplifier+5;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isInstant() {
        return false;
    }
}
