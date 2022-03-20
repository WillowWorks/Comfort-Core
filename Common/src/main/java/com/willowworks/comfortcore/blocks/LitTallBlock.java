package com.willowworks.comfortcore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LitTallBlock extends HorizontalDirectionalBlock {

    public static final VoxelShape LAMPSHAPE = Block.box(2.0D, 0.0D, 6.0D, 14.0D, 16.0D, 10.0D);
    public static final VoxelShape EASTWEST_LAMPSHAPE = Block.box(6.0D, 0.0D, 2.0D, 10.0D, 16.0D, 14.0D);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final BooleanProperty LAMPTOP = BooleanProperty.create("top");

    public LitTallBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LAMPTOP, false).setValue(FACING, Direction.NORTH));
    }
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);
        if (direction.equals(Direction.EAST)) {
            return EASTWEST_LAMPSHAPE;
        }
        if (direction.equals(Direction.WEST)) {
            return EASTWEST_LAMPSHAPE;
        }
        return LAMPSHAPE;
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        Direction direction = blockState.getValue(FACING);
        if (direction.equals(Direction.EAST)) {
            if (blockState.getValue(LAMPTOP) == !blockState.is(this))
                return;
            double d = (double) blockPos.getX() + 0.5D;
            double e = (double) blockPos.getY() + 0.97D;
            double f = (double) blockPos.getZ() + 0.5D;
            double g = (double) blockPos.getX() + 0.5D;
            double h = (double) blockPos.getY() + 0.7D;
            double i = (double) blockPos.getZ() + 0.25D;
            double j = (double) blockPos.getX() + 0.5D;
            double k = (double) blockPos.getY() + 0.45D;
            double l = (double) blockPos.getZ() + 0.75D;
            level.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, g, h, i, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, g, h, i, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, j, k, l, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, j, k, l, 0.0D, 0.0D, 0.0D);
        }
        if (direction.equals(Direction.SOUTH)) {
            if (blockState.getValue(LAMPTOP) == !blockState.is(this))
                return;
            double d = (double) blockPos.getX() + 0.5D;
            double e = (double) blockPos.getY() + 0.97D;
            double f = (double) blockPos.getZ() + 0.5D;
            double g = (double) blockPos.getX() + 0.25D;
            double h = (double) blockPos.getY() + 0.45D;
            double i = (double) blockPos.getZ() + 0.5D;
            double j = (double) blockPos.getX() + 0.75D;
            double k = (double) blockPos.getY() + 0.7D;
            double l = (double) blockPos.getZ() + 0.5D;
            level.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, g, h, i, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, g, h, i, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, j, k, l, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, j, k, l, 0.0D, 0.0D, 0.0D);
        }
        if (direction.equals(Direction.WEST)) {
            if (blockState.getValue(LAMPTOP) == !blockState.is(this))
                return;
            double d = (double) blockPos.getX() + 0.5D;
            double e = (double) blockPos.getY() + 0.97D;
            double f = (double) blockPos.getZ() + 0.5D;
            double g = (double) blockPos.getX() + 0.5D;
            double h = (double) blockPos.getY() + 0.7D;
            double i = (double) blockPos.getZ() + 0.75D;
            double j = (double) blockPos.getX() + 0.5D;
            double k = (double) blockPos.getY() + 0.45D;
            double l = (double) blockPos.getZ() + 0.25D;
            level.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, g, h, i, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, g, h, i, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, j, k, l, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, j, k, l, 0.0D, 0.0D, 0.0D);
        }
        if (direction.equals(Direction.NORTH)) {
            if (blockState.getValue(LAMPTOP) == !blockState.is(this))
                return;
            double d = (double) blockPos.getX() + 0.5D;
            double e = (double) blockPos.getY() + 0.97D;
            double f = (double) blockPos.getZ() + 0.5D;
            double g = (double) blockPos.getX() + 0.25D;
            double h = (double) blockPos.getY() + 0.7D;
            double i = (double) blockPos.getZ() + 0.5D;
            double j = (double) blockPos.getX() + 0.75D;
            double k = (double) blockPos.getY() + 0.45D;
            double l = (double) blockPos.getZ() + 0.5D;
            level.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, g, h, i, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, g, h, i, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, j, k, l, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, j, k, l, 0.0D, 0.0D, 0.0D);
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LAMPTOP);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
        if (blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().above()).canBeReplaced(blockPlaceContext))
            return defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction);
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        level.setBlock(blockPos.above(), blockState.setValue(LAMPTOP, true), 3);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(LAMPTOP)) {
            if (direction == Direction.DOWN && !blockState2.is(this)) {
                return Blocks.AIR.defaultBlockState();

            }
        } else {
            if (direction == Direction.UP && !blockState2.is(this)) {
                return Blocks.AIR.defaultBlockState();
            }

        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);

    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        if (!level.isClientSide && player.isCreative() && blockState.getValue(LAMPTOP)) {
            BlockPos below = blockPos.below();
            BlockState belowState = level.getBlockState(below);
            if (belowState.is(this) && !belowState.getValue(LAMPTOP)) {
                level.setBlock(below, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, below, Block.getId(belowState));
            }
        }
        super.playerWillDestroy(level, blockPos, blockState, player);
    }
}
