package com.github.ethanicuss.astraladditions.registry;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.github.ethanicuss.astraladditions.util.ModUtils;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModPotion {
	private static final Map<Potion, Integer> COLORS = new HashMap<>();

	public static Potion SHIMMER;
	public static Potion SPUTUM;

	public static void registerPotions() {
		SHIMMER = register("shimmer", 0xffd6fa, List.of(
						new StatusEffectInstance(StatusEffects.GLOWING, 600),
						new StatusEffectInstance(StatusEffects.NIGHT_VISION, 600)),
				ModData.SHIMMER_TAG
		);

		SPUTUM = register("sputum", 0x4c0e5e, List.of(
						new StatusEffectInstance(StatusEffects.WEAKNESS, 600),
						new StatusEffectInstance(StatusEffects.WITHER, 600)),
				ModData.SPUTUM_TAG
		);


	}
	//? if you dont care about like custom fluid bottle interaction
	//? java doesnt have default arguments like python or kotlin, how smelly
	private static Potion register(String name, int color, List<StatusEffectInstance> effects) {
		return register(name, color, effects, null);
	}

	private static Potion register(String name, int color, List<StatusEffectInstance> effects, TagKey<Fluid> fluidForBottling ) {
		Potion potion = Registry.register(Registry.POTION,
				new Identifier(AstralAdditions.MOD_ID, name),
				new Potion(effects.toArray(new StatusEffectInstance[0]))
		);
		COLORS.put(potion, color);

		if (fluidForBottling != null) {
			ModUtils.addPotionBottlingHandler(fluidForBottling, potion);
		}
		return potion;
	}

	public static void registerClient() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			if (tintIndex != 0) return 0xFFFFFF;
			Potion potion = PotionUtil.getPotion(stack);
			Integer color = COLORS.get(potion);
			return color != null ? color : PotionUtil.getColor(stack);
		}, Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION, Items.TIPPED_ARROW);
	}


}