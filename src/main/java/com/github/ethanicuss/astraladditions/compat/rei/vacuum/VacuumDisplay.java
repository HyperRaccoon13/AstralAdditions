package com.github.ethanicuss.astraladditions.compat.rei.vacuum;

import com.github.ethanicuss.astraladditions.compat.rei.AstralAdditionsREIClientPlugin;
import com.github.ethanicuss.astraladditions.recipes.ChromaticVacuumRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VacuumDisplay extends BasicDisplay {

	private final List<EntryIngredient> remainderEntries;

	public VacuumDisplay(Identifier id,
						 List<EntryIngredient> inputs,
						 List<EntryIngredient> outputs,
						 List<EntryIngredient> remainder) {
		super(inputs, outputs, Optional.ofNullable(id)); // REI 8.x ctor
		this.remainderEntries = (remainder == null) ? Collections.emptyList() : remainder;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return AstralAdditionsREIClientPlugin.VACUUM;
	}

	public List<EntryIngredient> getRemainderEntries() {
		return remainderEntries;
	}

	/** Return null to let REI skip invalid/unsynced recipes. */
	public static VacuumDisplay of(Recipe<?> recipe) {
		if (!(recipe instanceof ChromaticVacuumRecipe chromatic)) return null;

		ItemStack ing = chromatic.getIngredient();      // if yours actually returns Ingredient, change type + EntryIngredients.of(ing)
		if (ing == null || ing.isEmpty()) return null;

		ItemStack out = chromatic.getOutput();
		if (out == null || out.isEmpty()) return null;

		List<EntryIngredient> inputs    = Collections.singletonList(EntryIngredients.of(ing));
		List<EntryIngredient> outputs   = Collections.singletonList(EntryIngredients.of(out));
		List<EntryIngredient> remainders =
				chromatic.hasRemainder() && chromatic.getRemainder() != null && !chromatic.getRemainder().isEmpty()
						? Collections.singletonList(EntryIngredients.of(chromatic.getRemainder()))
						: Collections.emptyList();

		return new VacuumDisplay(recipe.getId(), inputs, outputs, remainders);
	}
}