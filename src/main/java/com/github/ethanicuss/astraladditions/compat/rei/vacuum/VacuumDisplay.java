package com.github.ethanicuss.astraladditions.compat.rei.vacuum;

import com.github.ethanicuss.astraladditions.compat.rei.AstralAdditionsREIClientPlugin;
import com.github.ethanicuss.astraladditions.recipes.ChromaticVacuumRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
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
		super(inputs, outputs, Optional.ofNullable(id));
		this.remainderEntries = (remainder == null) ? Collections.emptyList() : remainder;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return AstralAdditionsREIClientPlugin.VACUUM;
	}

	public List<EntryIngredient> getRemainderEntries() {
		return remainderEntries;
	}

	public static VacuumDisplay of(ChromaticVacuumRecipe chromatic) {
		Ingredient ing = chromatic.getIngredients().isEmpty() ? Ingredient.EMPTY : chromatic.getIngredients().get(0);
		if (ing.isEmpty()) return null;

		ItemStack out = chromatic.getOutput();
		if (out.isEmpty()) return null;

		List<EntryIngredient> inputs  = Collections.singletonList(EntryIngredients.ofIngredient(ing));
		List<EntryIngredient> outputs = Collections.singletonList(EntryIngredients.of(out.copy()));

		ItemStack rem = chromatic.getRemainder();
		List<EntryIngredient> remainders =
				chromatic.hasRemainder() && !rem.isEmpty()
						? Collections.singletonList(EntryIngredients.of(rem.copy()))
						: Collections.emptyList();

		return new VacuumDisplay(chromatic.getId(), inputs, outputs, remainders);
	}
}