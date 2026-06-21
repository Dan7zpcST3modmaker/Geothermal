package net.dan7zpc.geothermal.block.custom.heat_output;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.geothermal.block.custom.AbstractHeatTechBlock;
import net.dan7zpc.geothermal.block.entity.ModBlockEntities;
import net.dan7zpc.geothermal.block.entity.heat.Tier;
import net.dan7zpc.geothermal.block.entity.heat.heat_outputs.HeatVentHorizontalBlockEntity;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class HeatVentHorizontalBlock extends AbstractHeatTechBlock {
    public static final MapCodec<HeatVentHorizontalBlock> CODEC = simpleCodec(HeatVentHorizontalBlock::new);
    public static final VoxelShape VOXEL_SHAPE = Block.box(0,0,0,16,16,16);
    private BlockEntity be;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public HeatVentHorizontalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TIER,1));
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE,false));
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
        be = new HeatVentHorizontalBlockEntity(pos,state,Tier.tier_one);
        return be;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIER);
        builder.add(ACTIVE);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        HeatVentHorizontalBlockEntity blockEntity = (HeatVentHorizontalBlockEntity)level.getBlockEntity(pos);
        if(!level.isClientSide){return InteractionResult.PASS;}
        assert blockEntity != null;
        player.sendSystemMessage(Component.literal("heat:"+blockEntity.get_heat()));
        player.sendSystemMessage(Component.literal("heat_capacity:"+blockEntity.get_heat_capacity()));
        player.sendSystemMessage(Component.literal("temperature:"+blockEntity.get_temperature()));
        return InteractionResult.SUCCESS;
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.HEAT_VENT_HORIZONTAL_BE.get(),
                (blockEntitiyLevel,blockPos,blockState,blockEntity) -> blockEntity.tick(blockEntitiyLevel,blockPos,blockState));
    }
}
