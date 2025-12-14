package com.github.ethanicuss.astraladditions.util;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Box;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.TagKey;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.event.GameEvent;

public class ModUtils {
	public static <T extends ParticleEffect> void spawnForcedParticles(ServerWorld world, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
		for (ServerPlayerEntity player : world.getPlayers()) {
			world.spawnParticles(player, particle, true, x, y, z, count, deltaX, deltaY, deltaZ, speed);
		}
	}

	public static void playSound(ServerWorld world, double x, double y, double z, SoundEvent sound, SoundCategory category, float vol, float pitch, boolean falloff) {
		for (ServerPlayerEntity player : world.getPlayers()) {
			player.world.playSound(x, y, z, sound, category, vol, pitch, falloff);
		}
	}

	public static void pullPlayer(Entity entityActor, World world, boolean onlyPlayers, double strength, double vStrength, double entityPosX, double entityPosZ, double rangeX1, double rangeY1, double rangeZ1, double rangeX2, double rangeY2, double rangeZ2) {

		List<Entity> pl = world.getOtherEntities(entityActor, new Box(rangeX1, rangeY1, rangeZ1, rangeX2, rangeY2, rangeZ2));
		for (Entity p : pl) {
			if (p instanceof LivingEntity) {
				int strMult = 1;
				if (!(p instanceof PlayerEntity)) {
					strMult *= 2;
					//setting onlyPlayers to true will cause it to only pull players
					if (onlyPlayers) {
						strMult = 0;
					}
				}
				double xdiff = entityPosX - p.getX();
				double zdiff = entityPosZ - p.getZ();
				double dist = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(zdiff, 2));
				if (dist < 10) {
					if (xdiff == 0) {
						xdiff = 0.01;
					}
					if (zdiff == 0) {
						zdiff = 0.01;
					}
					double angleX = Math.atan(Math.abs(zdiff) / xdiff);
					double angleZ = Math.atan(Math.abs(xdiff) / zdiff);
					double cosX = Math.cos(angleX);
					double cosZ = Math.cos(angleZ);
					if (cosX == 0) {
						cosX = 0.01;
					}
					if (cosZ == 0) {
						cosZ = 0.01;
					}
					dist = -dist + 10;
					p.addVelocity(dist * cosX * strength * strMult * (Math.abs(angleX) / angleX), dist * vStrength * strMult, dist * cosZ * strength * strMult * (Math.abs(angleZ) / angleZ));
				}
			}
		}
	}

	//? lets us easily add custom fluid bottling
	public static void addPotionBottlingHandler(TagKey<Fluid> fluidTag, Potion potion) {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemStack inHand = player.getStackInHand(hand);
			if (inHand.getItem() != Items.GLASS_BOTTLE) return TypedActionResult.pass(inHand);

			BlockHitResult hit = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
			if (hit.getType() != HitResult.Type.BLOCK) return TypedActionResult.pass(inHand);

			BlockPos pos = hit.getBlockPos();
			if (!world.canPlayerModifyAt(player, pos)) return TypedActionResult.pass(inHand);
			if (!world.getFluidState(pos).getFluid().isIn(fluidTag)) return TypedActionResult.pass(inHand);

			ItemStack out = PotionUtil.setPotion(new ItemStack(Items.POTION), potion);

			world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
			player.incrementStat(Stats.USED.getOrCreateStat(Items.GLASS_BOTTLE));

			ItemStack exchanged = ItemUsage.exchangeStack(inHand, player, out);

			if (inHand.isEmpty()) {
				player.setStackInHand(hand, exchanged);
			}
			return TypedActionResult.success(player.getStackInHand(hand), world.isClient);
		});
	}

	//? the raycast i wanted to use was like protected or something so i had to make my own :(
	//? used in addPotionBottlingHandler
	private static BlockHitResult raycast(World world, PlayerEntity player, RaycastContext.FluidHandling fluidMode) {
		Vec3d start = player.getCameraPosVec(1.0F);
		Vec3d dir = player.getRotationVec(1.0F);
		Vec3d end = start.add(dir.multiply(5.0D));
		return world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, fluidMode, player));
	}
}