package com.github.ethanicuss.astraladditions.compat.rei.yttr.shattering;

import com.github.ethanicuss.astraladditions.compat.rei.AstralAdditionsREIClientPlugin;
import com.unascribed.yttr.Yttr;
import com.unascribed.yttr.crafting.ShatteringRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.util.Identifier;

import java.util.List;

public class ShatteringDisplay extends BasicDisplay {
	private final Identifier id;
	private final boolean exclusive;

	private ShatteringDisplay(Identifier id, EntryIngredient input, EntryIngredient output, boolean exclusive) {
		super(List.of(input), List.of(output));
		this.id = id;
		this.exclusive = exclusive;
	}

	public static ShatteringDisplay of(ShatteringRecipe recipe) {
		EntryIngredient input = EntryIngredients.ofIngredient(recipe.getIngredients().get(0));
		EntryIngredient output = EntryIngredients.of(recipe.getOutput().copy());
		return new ShatteringDisplay(recipe.getId(), input, output, true);
	}

	public static ShatteringDisplay ofWrappedCrafting(Recipe<CraftingInventory> recipe) {
		Identifier id = Yttr.id("shattering/" + recipe.getId().getNamespace() + "/" + recipe.getId().getPath());

		if (recipe.getIngredients().isEmpty() || recipe.getIngredients().get(0).isEmpty()) return null;

		ItemStack out = recipe.getOutput();
		if (out.isEmpty()) return null;

		EntryIngredient input = EntryIngredients.ofIngredient(recipe.getIngredients().get(0));
		EntryIngredient output = EntryIngredients.of(out.copy());
		return new ShatteringDisplay(id, input, output, false);
	}

	public static ShatteringDisplay ofStonecutting(StonecuttingRecipe recipe) {
		Identifier id = Yttr.id("shattering/" + recipe.getId().getNamespace() + "/" + recipe.getId().getPath());

		if (recipe.getIngredients().isEmpty() || recipe.getIngredients().get(0).isEmpty()) return null;

		ItemStack stonecutOut = recipe.getOutput();
		if (stonecutOut.isEmpty()) return null;

		EntryIngredient input = EntryIngredient.of(EntryStacks.of(stonecutOut.copy()));

		ItemStack[] matches = recipe.getIngredients().get(0).getMatchingStacks();
		if (matches.length == 0) return null;

		EntryIngredient output = EntryIngredient.of(EntryStacks.of(matches[0].copy()));

		return new ShatteringDisplay(id, input, output, true);
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return AstralAdditionsREIClientPlugin.SHATTERING;
	}

	public boolean isExclusive() {
		return exclusive;
	}
}