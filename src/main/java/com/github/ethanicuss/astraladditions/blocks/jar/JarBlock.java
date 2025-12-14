package com.github.ethanicuss.astraladditions.blocks.jar;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class JarBlock extends Block implements BlockEntityProvider {
    public JarBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape SHAPE_MAN = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context){
        return SHAPE_MAN;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //if (world.isClient) return ActionResult.SUCCESS;
        BlockState oldState = state;

        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (!player.getStackInHand(hand).isEmpty()) {
            // Check what is the first open slot and put an item from the player's hand there
            if (blockEntity.getStack(0).isEmpty()) {
                // Put the stack the player is holding into the inventory
                blockEntity.setStack(0, player.getStackInHand(hand).copy());
                // Remove the stack from the player's hand
                player.getStackInHand(hand).setCount(0);
            }
        } else {
            // If the player is not holding anything we'll get give him the items in the block entity one by one
            // Find the first slot that has an item and give it to the player
            if (!blockEntity.getStack(0).isEmpty()) {
                player.getInventory().offerOrDrop(blockEntity.getStack(0));
                blockEntity.removeStack(0);
            }
        }
        world.updateListeners(pos, oldState, state, Block.NOTIFY_LISTENERS);

        BlockPos goofyPos = new BlockPos(pos.getX(), -64, pos.getZ());
        BlockState goofyState = world.getBlockState(goofyPos);
        world.setBlockState(goofyPos, Blocks.STRUCTURE_VOID.getDefaultState());
        world.setBlockState(goofyPos, goofyState);
        return ActionResult.SUCCESS;
    }
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);//do this, then test on LAN then ur done yay
        Inventory jarBlockEntity = (Inventory) world.getBlockEntity(pos);
        if (!jarBlockEntity.getStack(0).isEmpty()) {
            player.getInventory().offerOrDrop(jarBlockEntity.getStack(0));
            jarBlockEntity.removeStack(0);
        }
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new JarBlockEntity(pos, state);
    }
}