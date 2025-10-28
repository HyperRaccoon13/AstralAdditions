package com.github.ethanicuss.astraladditions;

import com.github.ethanicuss.astraladditions.registry.ModParticles;
import com.github.ethanicuss.astraladditions.registry.ModEntities;
import com.github.ethanicuss.astraladditions.registry.ModFluids;
import com.github.ethanicuss.astraladditions.playertracker.PlayerTracker;
import com.github.ethanicuss.astraladditions.registry.ModBlocks;
import com.github.ethanicuss.astraladditions.registry.ModItemProperties;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstralAdditionsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(AstralAdditions.MOD_ID);
    public static PlayerTracker playerTracker = new PlayerTracker();


    @Override
    public void onInitializeClient() {
        ModEntities.registerClient();
        ModBlocks.registerClient();
        ModFluids.registerFluidRenderersClient();
        ModParticles.registerClient();
        ModItemProperties.registerClient();

        LOGGER.info("Astral Additions client is active!");
    }
}