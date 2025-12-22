package com.github.ethanicuss.astraladditions.compat.rei.yttr.soaking;

import com.github.ethanicuss.astraladditions.compat.rei.AstralAdditionsREIClientPlugin;
import com.unascribed.yttr.crafting.SoakingRecipe;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoakingDisplay extends BasicDisplay {
	private final Identifier id;
	private final List<EntryIngredient> catalystEntries;

	private SoakingDisplay(Identifier id, List<EntryIngredient> inputs, List<EntryIngredient> outputs, List<EntryIngredient> catalystEntries) {
		super(inputs, outputs);
		this.id = id;
		this.catalystEntries = catalystEntries;
	}

	public static SoakingDisplay of(SoakingRecipe recipe) {
		List<EntryIngredient> inputs = new ArrayList<>();

		recipe.getSoakingIngredients()
				.ifLeft(stack -> inputs.add(EntryIngredients.of(stack)))
				.ifRight(list -> {
					for (Ingredient ingredient : list) {
						inputs.add(EntryIngredients.ofIngredient(ingredient));
					}
				});

		ItemStack output = recipe.getOutput().copy();
		List<EntryIngredient> outputs = Collections.singletonList(EntryIngredients.of(output));

		List<EntryIngredient> catalystEntries = Collections.singletonList(
				EntryIngredient.of(recipe.getCatalyst().getMatchingFluids().stream().map(EntryStacks::of).toList())
		);

		return new SoakingDisplay(recipe.getId(), inputs, outputs, catalystEntries);
	}

	@Override
	public me.shedaniel.rei.api.common.category.CategoryIdentifier<?> getCategoryIdentifier() {
		return AstralAdditionsREIClientPlugin.SOAKING;
	}

	public List<EntryIngredient> getCatalystEntries() {
		return catalystEntries;
	}
}