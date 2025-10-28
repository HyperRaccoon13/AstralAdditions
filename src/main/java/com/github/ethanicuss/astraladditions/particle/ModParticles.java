package com.github.ethanicuss.astraladditions.particle;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.github.ethanicuss.astraladditions.particle.shimmer.ShimmerBubbleParticle;
import com.github.ethanicuss.astraladditions.particle.shimmer.ShimmerSplashParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {

	public static final DefaultParticleType SHIMMER_BUBBLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SHIMMER_SPLASH = FabricParticleTypes.simple();

	public static void registerParticles() {
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(AstralAdditions.MOD_ID, "shimmer_bubble"), SHIMMER_BUBBLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(AstralAdditions.MOD_ID, "shimmer_splash"), SHIMMER_SPLASH);
	}

	public static void registerClient() {
		ParticleFactoryRegistry.getInstance().register(ModParticles.SHIMMER_BUBBLE, ShimmerBubbleParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.SHIMMER_SPLASH, ShimmerSplashParticle.Factory::new);
	}
}