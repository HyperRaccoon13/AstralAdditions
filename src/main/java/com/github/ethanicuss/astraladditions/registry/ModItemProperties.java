package com.github.ethanicuss.astraladditions.registry;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.Identifier;


public class ModItemProperties {

	public static void register() {
		registerFishingRodProperties();
		registerShimmerBlowerVariant();

	}

	private static void registerFishingRodProperties() {
		ModelPredicateProviderRegistry.register(ModItems.SHIMMER_FISHING_ROD, new Identifier("cast"), (stack, world, entity, seed) -> {
			if (entity instanceof PlayerEntity player) {
				boolean usingMainHand = player.getMainHandStack() == stack;
				boolean usingOffHand = player.getOffHandStack() == stack;
				if (player.getMainHandStack().getItem() instanceof FishingRodItem) {
					usingOffHand = false;
				}
				return (usingMainHand || usingOffHand) && player.fishHook != null ? 1.0F : 0.0F;
			}
			return 0.0F;
		});
	}

	private static void registerShimmerBlowerVariant() {
		ModelPredicateProviderRegistry.register(
				ModItems.SHIMMER_BLOWER,
				new Identifier("variant"),
				(stack, world, entity, seed) -> {
					if (stack.hasNbt() && stack.getNbt()
							.contains("Variant")) {
						String variant = stack.getNbt().getString("Variant").trim();
						return switch (variant) {
							case "Zooming" -> 1.0f;
							case "Fuming" -> 0.95f;
							case "Pin point" -> 0.65f;
							default -> 0.0f;
						};
					}
					return 0.0f;
				}
		);
	}
}