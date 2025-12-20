package com.github.ethanicuss.astraladditions.compat.rei.create;

import com.github.ethanicuss.astraladditions.recipes.TransmuteRecipe;
import com.github.ethanicuss.astraladditions.registry.ModFluids;
import com.simibubi.create.compat.rei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.rei.category.animations.AnimatedKinetics;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class BulkShimmeringCategory extends ProcessingViaFanCategory<TransmuteRecipe> {

	public BulkShimmeringCategory(Info<TransmuteRecipe> info) {
		super(info);
	}

	@Override
	protected void renderAttachedBlock(MatrixStack matrices) {
		GuiGameElement.of(ModFluids.STILL_SHIMMER)
				.scale(SCALE)
				.atLocal(0, 0, 2)
				.lighting(AnimatedKinetics.DEFAULT_LIGHTING)
				.render(matrices);
	}

	@Override
	public void addWidgets(CreateDisplay<TransmuteRecipe> display, List<Widget> widgets, Point origin) {
		List<ItemStack> outputs = display.getRecipe().getOutputItems();
		if (outputs == null || outputs.isEmpty()) {
			super.addWidgets(display, widgets, origin);
			return;
		}

		final int pageSize = 4;
		final int total = outputs.size();
		final int pageCount = (total + pageSize - 1) / pageSize;
		final int[] page = {0};

		widgets.add(Widgets.createSlot(new Point(origin.x + 21, origin.y + 48)).entries(display.getInputEntries().get(0)).markInput());

		final int baseXLocal = 130;
		final int baseYLocal = 48;
		final int cell = 19;

		List<Slot> pageSlots = new ArrayList<>();
		Widget[] pageLabel = new Widget[1];

		Runnable reloadPage = () -> {
			widgets.removeAll(pageSlots);
			pageSlots.clear();

			if (pageLabel[0] != null) {
				widgets.remove(pageLabel[0]);
				pageLabel[0] = null;
			}

			int baseIndex = page[0] * pageSize;
			int countOnPage = Math.min(pageSize, total - baseIndex);

			for (int index = 0; index < countOnPage; index++) {
				int outIndex = baseIndex + index;

				int col = index % 2;
				int row = index / 2;

				int xLocal = baseXLocal + col * cell;
				int yLocal = baseYLocal + row * cell;

				Slot slot = Widgets.createSlot(new Point(origin.x + xLocal, origin.y + yLocal))
						.entries(EntryIngredients.of(outputs.get(outIndex)))
						.markOutput();

				pageSlots.add(slot);
				widgets.add(slot);
			}

			if (pageCount > 1) {
				int labelX = origin.x + baseXLocal + cell;
				int labelY = origin.y + 33;

				Text text = Text.of((page[0] + 1) + "/" + pageCount);
				pageLabel[0] = Widgets.createLabel(new Point(labelX, labelY), text)
						.noShadow()
						.color(0xFFFFFF, 0xAAAAAA)
						.centered();

				widgets.add(pageLabel[0]);
			}
		};

		reloadPage.run();

		if (pageCount > 1) {
			int labelX = origin.x + baseXLocal + cell;
			int buttonY = origin.y + 30;

			Rectangle backBounds = new Rectangle(labelX - 24, buttonY, 12, 12);
			Widget backButton = Widgets.createButton(backBounds, Text.of("<")).onClick(btn -> {
				page[0] = (page[0] - 1 + pageCount) % pageCount;
				reloadPage.run();
			});
			widgets.add(backButton);

			Rectangle nextBounds = new Rectangle(labelX + 12, buttonY, 12, 12);
			Widget nextButton = Widgets.createButton(nextBounds, Text.of(">")).onClick(btn -> {
				page[0] = (page[0] + 1) % pageCount;
				reloadPage.run();
			});
			widgets.add(nextButton);
		}
	}

	@Override
	public void addWidgets(CreateDisplay<TransmuteRecipe> display, List<Widget> widgets, Point origin, Rectangle bounds) {
		TransmuteRecipe recipe = display.getRecipe();
		Text hint = getHintText(recipe);

		int centerX = bounds.getCenterX();
		int centerY = bounds.getCenterY();

		widgets.add(Widgets.createLabel(new Point(centerX - 75, centerY + 42), hint).leftAligned());
	}


	@Override
	protected void renderWidgets(MatrixStack matrices, TransmuteRecipe recipe, double mouseX, double mouseY) {
		AllGuiTextures.JEI_SHADOW.render(matrices, 46, 29);
		getBlockShadow().render(matrices, 65, 39);
		AllGuiTextures.JEI_LONG_ARROW.render(matrices, 54, 51);
	}

	private Text getHintText(TransmuteRecipe recipe) {
		int count = recipe.getInputItem().getCount();
		if (recipe.isIgnoreCount()) {
			return new TranslatableText("label.astraladditions.transmute_hint_any");
		} else if (recipe.isSoftIgnoreCount()) {
			return new TranslatableText("label.astraladditions.transmute_hint_least", count);
		} else {
			return new TranslatableText("label.astraladditions.transmute_hint_exactly", count);
		}
	}
}