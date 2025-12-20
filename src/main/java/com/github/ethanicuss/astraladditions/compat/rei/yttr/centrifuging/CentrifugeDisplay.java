package com.github.ethanicuss.astraladditions.compat.rei.yttr.centrifuging;

import com.github.ethanicuss.astraladditions.compat.rei.AstralAdditionsREIClientPlugin;
import com.unascribed.yttr.crafting.CentrifugingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CentrifugeDisplay extends BasicDisplay {
    private final Identifier id;

    public CentrifugeDisplay(Identifier id, List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
        this.id = id;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return AstralAdditionsREIClientPlugin.CENTRIFUGE;
    }

    public static CentrifugeDisplay of(CentrifugingRecipe recipe) {
        Ingredient ingredient = recipe.getInput();
        int count = recipe.getInputCount();

        List<EntryIngredient> inputs = Collections.singletonList(
                EntryIngredient.of(
                        Arrays.stream(ingredient.getMatchingStacks()).map(stack -> {
                            ItemStack copy = stack.copy();
                            copy.setCount(count);
                            return EntryStacks.of(copy);}).toList()
                )
        );

        List<EntryIngredient> outputs = recipe.getOutputs().stream()
                .map(stack -> EntryIngredient.of(EntryStacks.of(stack.copy())))
                .toList();

        return new CentrifugeDisplay(recipe.getId(), inputs, outputs);
    }
}