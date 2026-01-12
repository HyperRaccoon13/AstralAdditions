package com.github.ethanicuss.astraladditions.compat.rei;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.github.ethanicuss.astraladditions.compat.rei.create.BulkShimmeringCategory;
import com.github.ethanicuss.astraladditions.compat.rei.desizer.DesizerCategory;
import com.github.ethanicuss.astraladditions.compat.rei.desizer.DesizerDisplay;
import com.github.ethanicuss.astraladditions.compat.rei.transmute.TransmuteCategory;
import com.github.ethanicuss.astraladditions.compat.rei.transmute.TransmuteDisplay;
import com.github.ethanicuss.astraladditions.compat.rei.vacuum.VacuumCategory;
import com.github.ethanicuss.astraladditions.compat.rei.vacuum.VacuumDisplay;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.centrifuging.CentrifugeCategory;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.centrifuging.CentrifugeDisplay;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.pistonsmashing.PistonSmashingCategory;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.pistonsmashing.PistonSmashingDisplay;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.shattering.ShatteringCategory;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.shattering.ShatteringDisplay;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.soaking.SoakingCategory;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.soaking.SoakingDisplay;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.voidfiltering.VoidFilteringCategory;
import com.github.ethanicuss.astraladditions.compat.rei.yttr.voidfiltering.VoidFilteringDisplay;
import com.github.ethanicuss.astraladditions.registry.ModFluids;
import com.github.ethanicuss.astraladditions.recipes.DesizerRecipe;
import com.github.ethanicuss.astraladditions.recipes.TransmuteRecipe;
import com.github.ethanicuss.astraladditions.recipes.ChromaticVacuumRecipe;
import com.github.ethanicuss.astraladditions.registry.ModBlocks;
import com.github.ethanicuss.astraladditions.registry.ModItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.rei.DoubleItemIcon;
import com.simibubi.create.compat.rei.EmptyBackground;
import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.unascribed.yttr.crafting.*;
import com.unascribed.yttr.init.YEnchantments;
import com.unascribed.yttr.init.YItems;
import com.unascribed.yttr.init.YRecipeTypes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class AstralAdditionsREIClientPlugin implements REIClientPlugin {
	public static final CategoryIdentifier<DesizerDisplay> DESIZER = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "desizer"));

	public static final CategoryIdentifier<TransmuteDisplay> TRANSMUTE = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "transmute"));

	public static final CategoryIdentifier<VacuumDisplay> VACUUM = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "vacuum"));

	//* YTTR
	public static final CategoryIdentifier<CentrifugeDisplay> CENTRIFUGE = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "centrifuge"));
	public static final CategoryIdentifier<PistonSmashingDisplay> PISTON_SMASHING = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "piston_smashing"));
	public static final CategoryIdentifier<SoakingDisplay> SOAKING = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "soaking"));
	public static final CategoryIdentifier<VoidFilteringDisplay> VOID_FILTERING = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "void_filtering"));
	public static final CategoryIdentifier<ShatteringDisplay> SHATTERING = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "shattering"));

	//* Create
	public static final CategoryIdentifier<CreateDisplay<TransmuteRecipe>> BULK_SHIMMERING = CategoryIdentifier.of(new Identifier(AstralAdditions.MOD_ID, "bulk_shimmering"));

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new DesizerCategory());
		registry.addWorkstations(DESIZER, EntryStacks.of(ModBlocks.DESIZER_CONTROLLER));

		registry.add(new TransmuteCategory());
		registry.addWorkstations(TRANSMUTE, EntryStacks.of(ModFluids.SHIMMER_BUCKET));

		registry.add(new VacuumCategory());
		registry.addWorkstations(VACUUM, EntryStacks.of(ModItems.CHROMATIC_VACUUM));

		//* YTTR
		registry.add(new CentrifugeCategory());
		registry.addWorkstations(CENTRIFUGE, EntryStacks.of(YItems.CENTRIFUGE));

		registry.add(new PistonSmashingCategory());
		registry.addWorkstations(PISTON_SMASHING, EntryStacks.of(Items.PISTON));
		registry.addWorkstations(PISTON_SMASHING, EntryStacks.of(Items.STICKY_PISTON));

		registry.add(new SoakingCategory());

		registry.add(new VoidFilteringCategory());
		registry.addWorkstations(VOID_FILTERING, EntryStacks.of(YItems.VOID_FILTER));


		registry.add(new ShatteringCategory());
		ItemStack shatteringBook = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentHelper.set(Map.of(YEnchantments.SHATTERING_CURSE, 1), shatteringBook);

		NbtList lore = new NbtList();
		lore.add(NbtString.of(Text.Serializer.toJson(
				new TranslatableText("category.astraladditions.shattering.workstations").setStyle(Style.EMPTY.withItalic(false).withColor(Formatting.YELLOW))
		)));
		NbtCompound displayTag = shatteringBook.getOrCreateSubNbt("display");
		displayTag.put("Lore", lore);

		registry.addWorkstations(SHATTERING, EntryStacks.of(shatteringBook));

		CreateRecipeCategory.Info<TransmuteRecipe> shimmerInfo = new CreateRecipeCategory.Info<>(BULK_SHIMMERING, new TranslatableText("category.astraladditions.bulk_shimmering"), new EmptyBackground(178, 110),
				new DoubleItemIcon(() -> new ItemStack(AllItems.PROPELLER.get()), () -> new ItemStack(ModFluids.SHIMMER_BUCKET)),
				Collections::emptyList,
				List.of(() -> new ItemStack(AllItems.PROPELLER.get()), () -> new ItemStack(ModFluids.SHIMMER_BUCKET)),
				178, 110, recipe -> new CreateDisplay<>(recipe, BULK_SHIMMERING));

		BulkShimmeringCategory bulkShimmeringCategory = new BulkShimmeringCategory(shimmerInfo);
		registry.add(bulkShimmeringCategory);

		registry.addWorkstations(BULK_SHIMMERING, EntryStacks.of(AllBlocks.ENCASED_FAN.get()));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		registry.registerRecipeFiller(DesizerRecipe.class, DesizerRecipe.Type.INSTANCE, DesizerDisplay::of);

		registry.registerRecipeFiller(TransmuteRecipe.class, TransmuteRecipe.Type.INSTANCE, TransmuteDisplay::of);

		registry.registerRecipeFiller(ChromaticVacuumRecipe.class, ChromaticVacuumRecipe.Type.INSTANCE, VacuumDisplay::of);

		//* YTTR
		registry.registerRecipeFiller(CentrifugingRecipe.class, YRecipeTypes.CENTRIFUGING, CentrifugeDisplay::of);

		registry.registerRecipeFiller(PistonSmashingRecipe.class, YRecipeTypes.PISTON_SMASHING, PistonSmashingDisplay::of);

		registry.registerRecipeFiller(SoakingRecipe.class, YRecipeTypes.SOAKING, SoakingDisplay::of);

		registry.registerRecipeFiller(VoidFilteringRecipe.class, YRecipeTypes.VOID_FILTERING, VoidFilteringDisplay::of);

		registry.registerRecipeFiller(ShatteringRecipe.class, YRecipeTypes.SHATTERING, ShatteringDisplay::of);

		registry.registerRecipeFiller(StonecuttingRecipe.class, RecipeType.STONECUTTING, ShatteringDisplay::ofStonecutting);

		registry.registerRecipeFiller(
				net.minecraft.recipe.CraftingRecipe.class,
				RecipeType.CRAFTING,
				recipe -> {
					if (recipe instanceof ShatteringRecipe) return null;

					if (recipe.getIngredients().size() != 1) return null;
					if (recipe.getIngredients().get(0).isEmpty()) return null;

					ItemStack out = recipe.getOutput();
					if (out.isEmpty()) return null;

					return ShatteringDisplay.ofWrappedCrafting(recipe);
				}
		);

		registry.registerRecipeFiller(
				TransmuteRecipe.class,
				TransmuteRecipe.Type.INSTANCE,
				recipe -> {
					ItemStack out = recipe.getOutput();
					if (out == null || out.isEmpty()) {
						return null;
					}
					return new CreateDisplay<>(recipe, BULK_SHIMMERING);
				}
		);
	}



	@Override
	public void registerScreens(ScreenRegistry registry) {
		REIClientPlugin.super.registerScreens(registry);
	}
}