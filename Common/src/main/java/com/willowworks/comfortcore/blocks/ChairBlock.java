package com.willowworks.comfortcore.blocks;

import com.willowworks.comfortcore.entities.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ChairBlock extends HorizontalDirectionalBlock {
    private static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final VoxelShape BOT_SEAT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public static final VoxelShape BOT_BACK_SHAPE = Block.box(0.0D, 10.0D, 12.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape TOP_FULL_SHAPE = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape BOT_FULL_SHAPE = Shapes.or(BOT_BACK_SHAPE, BOT_SEAT_SHAPE);
    public static final VoxelShape EAST_BOT_SEAT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public static final VoxelShape EAST_BOT_BACK_SHAPE = Block.box(0.0D, 10.0D, 0.0D, 4.0D, 16.0D, 16.0D);
    public static final VoxelShape EAST_TOP_FULL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
    public static final VoxelShape EAST_BOT_FULL_SHAPE = Shapes.or(EAST_BOT_BACK_SHAPE, EAST_BOT_SEAT_SHAPE);
    public static final VoxelShape WEST_BOT_SEAT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public static final VoxelShape WEST_BOT_BACK_SHAPE = Block.box(12.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape WEST_TOP_FULL_SHAPE = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape WEST_BOT_FULL_SHAPE = Shapes.or(WEST_BOT_BACK_SHAPE, WEST_BOT_SEAT_SHAPE);
    public static final VoxelShape SOUTH_BOT_SEAT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public static final VoxelShape SOUTH_BOT_BACK_SHAPE = Block.box(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    public static final VoxelShape SOUTH_TOP_FULL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    public static final VoxelShape SOUTH_BOT_FULL_SHAPE = Shapes.or(SOUTH_BOT_BACK_SHAPE, SOUTH_BOT_SEAT_SHAPE);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if(!blockState.getValue(TOP) && !SeatEntity.hasLocation(pos) && !level.isClientSide() && player.getItemInHand(interactionHand).isEmpty()) {
            SeatEntity seat = new SeatEntity(level, pos);
            seat.setPos(pos.getX() + .5, pos.getY() + .325, pos.getZ() + .5);
            player.startRiding(seat);
            level.addFreshEntity(seat);
            return InteractionResult.SUCCESS;
        }
        return super.use(blockState, level, pos, player, interactionHand, blockHitResult);
    }

    public ChairBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(TOP, false).setValue(FACING, Direction.NORTH));
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);
        if (direction.equals(Direction.EAST)) {
            if (blockState.getValue(TOP) == !blockState.is(this))
                return EAST_BOT_FULL_SHAPE;
            return EAST_TOP_FULL_SHAPE;
        }
        if (direction.equals(Direction.WEST)) {
            if (blockState.getValue(TOP) == !blockState.is(this))
                return WEST_BOT_FULL_SHAPE;
            return WEST_TOP_FULL_SHAPE;
        }
        if (direction.equals(Direction.SOUTH)) {
            if (blockState.getValue(TOP) == !blockState.is(this))
                return SOUTH_BOT_FULL_SHAPE;
            return SOUTH_TOP_FULL_SHAPE;
        }
        if (blockState.getValue(TOP) == !blockState.is(this))
            return BOT_FULL_SHAPE;
        return TOP_FULL_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TOP);
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
        level.setBlock(blockPos.above(), blockState.setValue(TOP, true), 3);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(TOP)) {
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
        if (!level.isClientSide && player.isCreative() && blockState.getValue(TOP)) {
            BlockPos below = blockPos.below();
            BlockState belowState = level.getBlockState(below);
            if (belowState.is(this) && !belowState.getValue(TOP)) {
                level.setBlock(below, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, below, Block.getId(belowState));
            }
        }
        super.playerWillDestroy(level, blockPos, blockState, player);
    }
}
