package com.github.ethanicuss.astraladditions.items;

import com.github.ethanicuss.astraladditions.registry.ModEntities;
import com.github.ethanicuss.astraladditions.entities.cogfly.CogflyEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class CogflySpawnItem extends Item {
    public CogflySpawnItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            CogflyEntity cogflyEntity = new CogflyEntity(ModEntities.COGFLY, user.getWorld());
            cogflyEntity.setOwner(user);
            cogflyEntity.updatePosition(user.getX(), user.getEyeY(), user.getZ());
            cogflyEntity.setOwner(user.getUuidAsString());
            world.spawnEntity(cogflyEntity);
        }
        user.getItemCooldownManager().set(this, 5);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("tooltip.astraladditions.cogfly").formatted(Formatting.GRAY));
    }
}