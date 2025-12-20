package com.github.ethanicuss.astraladditions.mixin.yttr;

import com.unascribed.yttr.inventory.MagtankScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MagtankScreenHandler.class)
public abstract class MagtankScreenHandlerMixin extends ScreenHandler {

	protected MagtankScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		if (index < 0 || index >= this.slots.size()) {
			return ItemStack.EMPTY;
		}

		var slot = this.slots.get(index);

		if (slot != null && slot.hasStack()) {
			ItemStack original = slot.getStack();
			newStack = original.copy();

			if (index < 27) {
				if (!this.insertItem(original, 27, 36, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index < 36) {
				if (!this.insertItem(original, 0, 27, false)) {
					return ItemStack.EMPTY;
				}
			} else {
				return ItemStack.EMPTY;
			}

			if (original.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}
}