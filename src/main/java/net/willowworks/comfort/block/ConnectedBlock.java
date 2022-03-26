package net.willowworks.comfort.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
//There is an explanation for using this class at the bottom of the script :)

public class ConnectedBlock extends CrossCollisionBlock {

    public static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public ConnectedBlock(Properties properties) {
        super(8.0F, 8.0F, 16.0F, 8.0F, 16.0F, properties);
        this.registerDefaultState((BlockState) ((BlockState) ((BlockState) ((BlockState) ((BlockState) ((BlockState)
                this.stateDefinition.any())
                .setValue(NORTH, false))
                .setValue(EAST, false))
                .setValue(SOUTH, false))
                .setValue(WEST, false))
                .setValue(WATERLOGGED, false));
    }

    public VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return AABB;
    }

    public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
        return false;
    }

    public boolean connectsTo(BlockState blockState, boolean bl, Direction direction) {
        Block block = blockState.getBlock();
        boolean bl2 = this.isSameBlock(blockState);
        return !isExceptionForConnection(blockState) && bl || bl2;
    }

    private boolean isSameBlock(BlockState blockState) {
        return blockState.is(BlockTags.FENCES) && blockState.is(BlockTags.WOODEN_FENCES) == this.defaultBlockState().is(BlockTags.WOODEN_FENCES);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockGetter blockGetter = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.east();
        BlockPos blockPos4 = blockPos.south();
        BlockPos blockPos5 = blockPos.west();
        BlockState blockState = blockGetter.getBlockState(blockPos2);
        BlockState blockState2 = blockGetter.getBlockState(blockPos3);
        BlockState blockState3 = blockGetter.getBlockState(blockPos4);
        BlockState blockState4 = blockGetter.getBlockState(blockPos5);
        return (BlockState) ((BlockState) ((BlockState) ((BlockState) ((BlockState)
                super.getStateForPlacement(blockPlaceContext)
                        .setValue(NORTH, this.connectsTo(blockState, blockState.isFaceSturdy(blockGetter, blockPos2, Direction.SOUTH), Direction.SOUTH)))
                .setValue(EAST, this.connectsTo(blockState2, blockState2.isFaceSturdy(blockGetter, blockPos3, Direction.WEST), Direction.WEST)))
                .setValue(SOUTH, this.connectsTo(blockState3, blockState3.isFaceSturdy(blockGetter, blockPos4, Direction.NORTH), Direction.NORTH)))
                .setValue(WEST, this.connectsTo(blockState4, blockState4.isFaceSturdy(blockGetter, blockPos5, Direction.EAST), Direction.EAST)))
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    public BlockState updateShape(
            BlockState blockState, Direction direction,
            BlockState blockState2, LevelAccessor levelAccessor,
            BlockPos blockPos, BlockPos blockPos2) {
        return direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? (BlockState) blockState
                .setValue((Property) PROPERTY_BY_DIRECTION.get(direction),
                        this.connectsTo(blockState2, blockState2.isFaceSturdy(levelAccessor, blockPos2, direction.getOpposite()), direction.getOpposite())) :
                super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{NORTH, EAST, WEST, SOUTH, WATERLOGGED});
    }
}

/*
This class is used to create full blocks that connect to each other. Such as tables or maybe desks, So long as it has  collision box of a block it will connect.
The class utilizes the power of blockstates. There is a tag for every direction of a block, which can be used to define a model using the multi-block parameter
in a blockstate json file. Here follows an example:

{
  "multipart": [{"apply": {"model": "willowsfurniture:block/norse_table_top"}},
    {"when": {"north": "false", "west": "false"},
      "apply": {"model": "willowsfurniture:block/norse_nw_leg"}},
    {"when": {"north": "false", "east": "false"},
      "apply": {"model": "willowsfurniture:block/norse_ne_leg"}},
    {"when": {"south": "false", "east": "false"},
      "apply": {"model": "willowsfurniture:block/norse_nw_leg", "y": 180}},
    {"when": {"south": "false", "west": "false"},
      "apply": {"model": "willowsfurniture:block/norse_ne_leg", "y": 180}}
  ]
}

a false boolean means to say no block is present on that side. This logically results in a support strut being needed there.
 */
