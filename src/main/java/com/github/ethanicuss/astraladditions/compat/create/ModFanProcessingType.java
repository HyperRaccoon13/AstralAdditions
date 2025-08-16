package com.github.ethanicuss.astraladditions.compat.create;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingTypeRegistry;
import net.minecraft.util.Identifier;

public class ModFanProcessingType {
	public static final ShimmerTransmuteType SHIMMER_TRANSMUTE =
			register("shimmer_transmute", new ShimmerTransmuteType());

	private static <T extends FanProcessingType> T register(String name, T type) {
		FanProcessingTypeRegistry.register(new Identifier(AstralAdditions.MOD_ID, name), type);
		return type;
	}

	public static void register() {}
}