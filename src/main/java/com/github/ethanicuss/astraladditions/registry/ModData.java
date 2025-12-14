package com.github.ethanicuss.astraladditions.registry;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModData {
    public static final TagKey<Block> BULBA_GROWABLE = registerBlockTag("bulba_growable");
    public static final TagKey<Block> LUNE_SHROOM_GROWABLE = registerBlockTag("lune_shroom_growable");
    public static final TagKey<Block> DESIZER_IGNORE_BLOCKS = registerBlockTag("desizer_ignore_blocks");
    public static final TagKey<Block> DESIZER_CASING_BLOCKS = registerBlockTag("desizer_casing_blocks");

    public static final TagKey<Item> INGORE_TRANSMUTATION = registerItemTag("ignore_shimmer_transmutation");

    public static final TagKey<Fluid> SHIMMER_TAG = registerFluidTag("shimmer");
    public static final TagKey<Fluid> SPUTUM_TAG = registerFluidTag("sputum");

    public static final Identifier FRAGILE_ITEM_PARTS = new Identifier(AstralAdditions.MOD_ID, "gameplay/fragile_items/fragile_item");
    public static final Identifier FRAGILE_ITEM_PARTS_2 = new Identifier(AstralAdditions.MOD_ID, "gameplay/fragile_items/fragile_item_2");
    public static final Identifier FRAGILE_ITEM_PARTS_3 = new Identifier(AstralAdditions.MOD_ID, "gameplay/fragile_items/fragile_item_3");

    private static TagKey<Block> registerBlockTag(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(AstralAdditions.MOD_ID, id));
    }
    private static TagKey<Item> registerItemTag(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(AstralAdditions.MOD_ID, id));
    }

    private static TagKey<Fluid> registerFluidTag(String id) {
        return TagKey.of(Registry.FLUID_KEY, new Identifier(AstralAdditions.MOD_ID, id));
    }
}