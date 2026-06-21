package net.dan7zpc.geothermal.block.custom.heat_transfer;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.geothermal.block.custom.AbstractHeatTechBlock;
import net.dan7zpc.geothermal.block.entity.ModBlockEntities;
import net.dan7zpc.geothermal.block.entity.heat.Tier;
import net.dan7zpc.geothermal.block.entity.heat.heat_transfer.HeatAccumulatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class HeatAccumulatorBlock extends AbstractHeatTechBlock {
    public static final MapCodec<HeatAccumulatorBlock> CODEC = simpleCodec(HeatAccumulatorBlock::new);
    public static final VoxelShape VOXEL_SHAPE = Block.box(0,0,0,16,16,16);
    private BlockEntity be;

    public HeatAccumulatorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TIER,1));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VOXEL_SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        be = new HeatAccumulatorBlockEntity(pos,state,Tier.tier_one);
        return be;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIER);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        HeatAccumulatorBlockEntity blockEntity = (HeatAccumulatorBlockEntity)level.getBlockEntity(pos);
        if(!level.isClientSide){return InteractionResult.PASS;}
        assert blockEntity != null;
        player.sendSystemMessage(Component.literal("heat:"+blockEntity.get_heat()));
        player.sendSystemMessage(Component.literal("heat_capacity:"+blockEntity.get_heat_capacity()));
        player.sendSystemMessage(Component.literal("temperature:"+blockEntity.get_temperature()));
        return InteractionResult.SUCCESS;
    }

//    @Override
//    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
//        ItemInteractionResult result = ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//        HeatAccumulatorBlockEntity hbe = (HeatAccumulatorBlockEntity) level.getBlockEntity(pos);
//        assert hbe != null;
//        if(stack.getItem() == Items.COPPER_BLOCK && hbe.get_tier()==Tier.tier_one){
//            result = ItemInteractionResult.SUCCESS;
//            stack.setCount(stack.getCount()-1);
//            hbe.set_tier(Tier.tier_two);
//            if(level.isClientSide()) return ItemInteractionResult.SUCCESS;
//            level.setBlockAndUpdate(pos,state.setValue(TIER, 2));
//        }
//        return result;
//    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.HEAT_ACCUMULATOR_BE.get(),
                (blockEntitiyLevel,blockPos,blockState,blockEntity) -> blockEntity.tick(blockEntitiyLevel,blockPos,blockState));
    }
}
