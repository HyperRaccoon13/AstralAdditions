package com.github.ethanicuss.astraladditions.compat.create;

import com.github.ethanicuss.astraladditions.registry.ModFluids;
import com.github.ethanicuss.astraladditions.registry.ModParticles;
import com.github.ethanicuss.astraladditions.recipes.TransmuteRecipe;
import com.jozufozu.flywheel.util.Color;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ShimmerTransmuteType implements FanProcessingType {

	@Override
	public int getPriority() {
		return 450;
	}

	@Override
	public boolean isValidAt(World world, BlockPos pos) {
		FluidState fluid = world.getFluidState(pos);
		return fluid.isStill() && fluid.getFluid() == ModFluids.STILL_SHIMMER || fluid.getFluid() == ModFluids.FLOWING_SHIMMER;
	}

	@Override
	public boolean canProcess(ItemStack stack, World world) {
		return world.getRecipeManager()
				.listAllOfType(TransmuteRecipe.Type.INSTANCE)
				.stream()
				.map(r -> (TransmuteRecipe) r)
				.anyMatch(r -> matchesRecipeInput(stack, r));
	}

	@Override
	@Nullable
	public List<ItemStack> process(ItemStack stack, World world) {
		Optional<TransmuteRecipe> recipeOpt = world.getRecipeManager()
				.listAllOfType(TransmuteRecipe.Type.INSTANCE)
				.stream()
				.map(r -> (TransmuteRecipe) r)
				.filter(r -> matchesRecipeInput(stack, r))
				.findFirst();

		if (recipeOpt.isEmpty()) return null;

		TransmuteRecipe recipe = recipeOpt.get();
		ItemStack required = recipe.getInputItem();

		int multiplier = 1;

		if (!recipe.isIgnoreCount()) {
			if (recipe.isSoftIgnoreCount()) {
				multiplier = stack.getCount() / required.getCount();
			} else {
				if (stack.getCount() != required.getCount()) return null;
			}
		}

		List<ItemStack> result = new ArrayList<>();
		for (ItemStack output : recipe.getOutputItems()) {
			ItemStack out = output.copy();
			out.setCount(out.getCount() * multiplier);
			result.add(out);
		}

		return result;
	}

	private boolean matchesRecipeInput(ItemStack stack, TransmuteRecipe recipe) {
		ItemStack required = recipe.getInputItem();

		if (recipe.isIgnoreCount()) {
			return stack.getItem() == required.getItem();
		} else if (recipe.isSoftIgnoreCount()) {
			return stack.getItem() == required.getItem()
					&& stack.getCount() >= required.getCount();
		} else {
			return stack.getItem() == required.getItem()
					&& stack.getCount() == required.getCount();
		}
	}

	@Override
	public void spawnProcessingParticles(World world, Vec3d pos) {
		if (world.random.nextInt(6) != 0) return;
		world.addParticle(ParticleTypes.ENCHANT, pos.x, pos.y + .3, pos.z, 0, .1, 0);
	}


	@Override
	public void morphAirFlow(AirFlowParticleAccess particleAccess, Random random) {
		particleAccess.setColor(Color.mixColors(0xf543bc, 0xf993c5, random.nextFloat()));
		particleAccess.setAlpha(0.75f);
		if (random.nextFloat() < 1 / 128f)
			particleAccess.spawnExtraParticle(ModParticles.SHIMMER_BUBBLE, .125f);
		if (random.nextFloat() < 1 / 128f)
			particleAccess.spawnExtraParticle(ModParticles.SHIMMER_BUBBLE_POP, .125f);
	}


	@Override
	public void affectEntity(Entity entity, World world) {
		if (!(entity instanceof LivingEntity living)) return;
		living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 60, 0, false, false));
		living.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 330, 0, false, false));


	}
}