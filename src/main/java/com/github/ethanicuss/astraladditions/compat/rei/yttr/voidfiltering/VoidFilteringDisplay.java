package com.github.ethanicuss.astraladditions.compat.rei.yttr.voidfiltering;

import com.github.ethanicuss.astraladditions.compat.rei.AstralAdditionsREIClientPlugin;
import com.unascribed.yttr.crafting.VoidFilteringRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public class VoidFilteringDisplay extends BasicDisplay {
	private final Identifier id;
	private final float chance;
	private final boolean hidden;

	private VoidFilteringDisplay(Identifier id, List<EntryIngredient> inputs, List<EntryIngredient> outputs, float chance, boolean hidden) {
		super(inputs, outputs);
		this.id = id;
		this.chance = chance;
		this.hidden = hidden;
	}

	public static VoidFilteringDisplay of(VoidFilteringRecipe recipe) {
		List<EntryIngredient> inputs = Collections.emptyList();

		List<EntryIngredient> outputs = Collections.singletonList(
				EntryIngredients.of(recipe.getOutput().copy())
		);

		return new VoidFilteringDisplay(recipe.getId(), inputs, outputs, recipe.getChance(), recipe.isHidden());
	}


	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return AstralAdditionsREIClientPlugin.VOID_FILTERING;
	}

	public float getChance() {
		return chance;
	}

	public boolean isHidden() {
		return hidden;
	}
}