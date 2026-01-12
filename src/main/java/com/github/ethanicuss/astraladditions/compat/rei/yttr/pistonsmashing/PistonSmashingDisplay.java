package com.github.ethanicuss.astraladditions.compat.rei.yttr.pistonsmashing;

import com.github.ethanicuss.astraladditions.compat.rei.AstralAdditionsREIClientPlugin;
import com.unascribed.yttr.crafting.PistonSmashingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.List;

public class PistonSmashingDisplay extends BasicDisplay {
	private final PistonSmashingRecipe recipe;

	private final Identifier id;
	private final boolean hasCloud;
	private final int cloudColor;
	private final int cloudSize;
	private final EntryStack<?> cloudOutput;
	private final List<StatusEffectInstance> cloudEffects;

	private final List<Block> inputMatches;
	private final List<Block> catalystMatches;

	public PistonSmashingDisplay(Identifier id, List<EntryIngredient> inputs, List<EntryIngredient> outputs, boolean hasCloud, int cloudColor, int cloudSize, EntryStack<?> cloudOutput, List<StatusEffectInstance> cloudEffects, List<Block> inputMatches, List<Block> catalystMatches, PistonSmashingRecipe recipe) {
		super(inputs, outputs);
		this.id = id;
		this.hasCloud = hasCloud;
		this.cloudColor = cloudColor;
		this.cloudSize = cloudSize;
		this.cloudOutput = cloudOutput;
		this.cloudEffects = cloudEffects;
		this.inputMatches = inputMatches;
		this.catalystMatches = catalystMatches;
		this.recipe = recipe;
	}

	public Block getInputBlock() {
		if (inputMatches.isEmpty()) return Blocks.AIR;
		int index = (int) ((System.currentTimeMillis() / 1000) % inputMatches.size());
		return inputMatches.get(index);
	}

	public Block getCatalystBlock() {
		if (catalystMatches.isEmpty()) return Blocks.AIR;
		int index = (int) ((System.currentTimeMillis() / 1000) % catalystMatches.size());
		return catalystMatches.get(index);
	}

	public boolean hasCloud() { return hasCloud; }
	public int getCloudColor() { return cloudColor; }
	public int getCloudSize() { return cloudSize; }
	public EntryStack<?> getCloudOutput() { return cloudOutput; }
	public List<StatusEffectInstance> getCloudEffects() { return cloudEffects; }

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return AstralAdditionsREIClientPlugin.PISTON_SMASHING;
	}

	public static PistonSmashingDisplay of(PistonSmashingRecipe recipe) {
		List<Block> inputBlocks = Registry.BLOCK.stream()
				.filter(block -> recipe.getInput().test(block))
				.toList();

		List<Block> catalystBlocks = Registry.BLOCK.stream()
				.filter(block -> recipe.getCatalyst().test(block))
				.toList();

		EntryIngredient catalystIng = EntryIngredient.of(
				catalystBlocks.stream()
						.map(block -> EntryStacks.of(new ItemStack(block)))
						.toList()
		);

		EntryIngredient inputIng = EntryIngredient.of(
				inputBlocks.stream()
						.map(block -> EntryStacks.of(new ItemStack(block)))
						.toList()
		);

		List<EntryIngredient> inputs = List.of(catalystIng, inputIng);

		List<EntryIngredient> outputs = Collections.singletonList(
				EntryIngredients.of(recipe.getOutput())
		);

		EntryStack<?> cloudOut = EntryStacks.of(recipe.getCloudOutput());

		return new PistonSmashingDisplay(
				recipe.getId(),
				inputs,
				outputs,
				recipe.hasCloud(),
				recipe.getCloudColor(),
				recipe.getCloudSize(),
				cloudOut,
				recipe.getCloudEffects(),
				inputBlocks,
				catalystBlocks,
				recipe
		);
	}
}