package com.github.ethanicuss.astraladditions.registry;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.github.ethanicuss.astraladditions.fluids.ShimmerFluid;
import com.github.ethanicuss.astraladditions.fluids.SputumFluid;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModFluids {
    public static final String ASTRAL_ID = "kubejs";

    public static FlowableFluid STILL_SHIMMER;
    public static FlowableFluid FLOWING_SHIMMER;
    public static Item SHIMMER_BUCKET;
    public static Block SHIMMER;

    public static FlowableFluid STILL_SPUTUM;
    public static FlowableFluid FLOWING_SPUTUM;
    public static Item SPUTUM_BUCKET;
    public static Block SPUTUM;

    public static void registerFluids(){
        STILL_SHIMMER = Registry.register(Registry.FLUID, new Identifier(ASTRAL_ID, "shimmer"), new ShimmerFluid.Still());
        FLOWING_SHIMMER = Registry.register(Registry.FLUID, new Identifier(ASTRAL_ID, "flowing_shimmer"), new ShimmerFluid.Flowing());
        SHIMMER_BUCKET = Registry.register(Registry.ITEM, new Identifier(ASTRAL_ID, "shimmer_bucket"),
                new BucketItem(STILL_SHIMMER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        SHIMMER = Registry.register(Registry.BLOCK, new Identifier(ASTRAL_ID, "shimmer"),
                new FluidBlock(STILL_SHIMMER, FabricBlockSettings.of(Material.WATER).noCollision().ticksRandomly().strength(1.0F).luminance((state) -> 10).dropsNothing()));

        STILL_SPUTUM = Registry.register(Registry.FLUID, new Identifier(AstralAdditions.MOD_ID, "sputum"), new SputumFluid.Still());
        FLOWING_SPUTUM = Registry.register(Registry.FLUID, new Identifier(AstralAdditions.MOD_ID, "flowing_sputum"), new SputumFluid.Flowing());
        SPUTUM_BUCKET = Registry.register(Registry.ITEM, new Identifier(AstralAdditions.MOD_ID, "sputum_bucket"),
                new BucketItem(STILL_SPUTUM, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        SPUTUM = Registry.register(Registry.BLOCK, new Identifier(AstralAdditions.MOD_ID, "sputum"),
                new FluidBlock(STILL_SPUTUM, FabricBlockSettings.of(Material.WATER).noCollision().ticksRandomly().strength(1.0F).luminance((state) -> 5).dropsNothing()));

    }


    public static void registerFluidRenderersClient() {
        registerHandler(ModFluids.STILL_SHIMMER, ModFluids.FLOWING_SHIMMER, new Identifier(AstralAdditions.MOD_ID, "block/shimmer/shimmer"), 0xffd6fa);
        registerHandler(ModFluids.STILL_SPUTUM, ModFluids.FLOWING_SPUTUM, new Identifier(AstralAdditions.MOD_ID, "block/sputum/sputum"), 0x4c0e5e);


        final Fluid[] TRANSLUCENT_FLUIDS = {
                ModFluids.STILL_SPUTUM, ModFluids.FLOWING_SPUTUM,
                ModFluids.STILL_SHIMMER, ModFluids.FLOWING_SHIMMER
        };
        final Block[] TRANSPARENT_FLUID_BLOCKS = {
                SPUTUM,
        };

        for (Block block : TRANSPARENT_FLUID_BLOCKS) {
            FluidRenderHandlerRegistry.INSTANCE.setBlockTransparency(block, true);
        }
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), TRANSLUCENT_FLUIDS);

    }
    private static void registerHandler(Fluid still, Fluid flowing, Identifier id, int tint) {
        FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, new SimpleFluidRenderHandler(id, id, tint));
    }
}