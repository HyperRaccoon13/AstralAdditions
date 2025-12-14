package com.github.ethanicuss.astraladditions.registry;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.github.ethanicuss.astraladditions.effects.parry.ParryEffect;
import com.github.ethanicuss.astraladditions.effects.frost.FrostEffect;
import com.github.ethanicuss.astraladditions.effects.sinkeffect.SinkEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModEffects {

    public static StatusEffect SINK;
    public static StatusEffect FROST;
    public static StatusEffect PARRY;

    public static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(AstralAdditions.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        SINK = registerStatusEffect("sink", new SinkEffect(StatusEffectCategory.NEUTRAL, 16770815));
        FROST = registerStatusEffect("frost", new FrostEffect(StatusEffectCategory.NEUTRAL, 2014687));
        PARRY = registerStatusEffect("parry", new ParryEffect(StatusEffectCategory.BENEFICIAL, 15720802));
    }
}
