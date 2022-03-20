package com.willowworks.comfortcore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class WideBlock extends Block {
    private static final BooleanProperty SIDE = BooleanProperty.create("side");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public WideBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(SIDE, false).setValue(FACING, Direction.NORTH));
    }

    private static Direction getDirection(BlockState blockState, boolean negated) {
        return blockState.getValue(SIDE) == negated ? blockState.getValue(FACING).getCounterClockWise() : blockState.getValue(FACING).getClockWise();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SIDE);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
        if (blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(direction.getClockWise())).canBeReplaced(blockPlaceContext))
            return defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction);
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        Direction direction = blockState.getValue(FACING);
        level.setBlock(blockPos.relative(direction.getClockWise()), blockState.setValue(SIDE, true), 3);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == getDirection(blockState, true) && !blockState2.is(this)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            BlockPos otherPos = blockPos.relative(getDirection(blockState, false));
            BlockState otherState = level.getBlockState(otherPos);
            if (otherState.is(this)) {
                level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, otherPos, Block.getId(otherState));
            }
        }
        super.playerWillDestroy(level, blockPos, blockState, player);
    }
}
