package net.dan7zpc.geothermal.block.custom.heat_extractors;

import com.mojang.logging.LogUtils;
import net.dan7zpc.geothermal.Geothermal;
import net.dan7zpc.geothermal.block.entity.ModBlockEntities;
import net.dan7zpc.geothermal.block.entity.heat_extractors.HeatExtractorBlockEntity;
import net.dan7zpc.geothermal.block.entity.heat_extractors.TierOneHeatExtractorBlockEntity;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;


public abstract class HeatExtractorBlock extends BaseEntityBlock {
    //public static final MapCodec<HeatExtractorBlock> CODEC = simpleCodec(HeatExtractorBlock::new);
    public static final VoxelShape VOXEL_SHAPE = Block.box(0,0,0,16,16,16);
    protected final BlockEntityType<? extends HeatExtractorBlockEntity> myBlockEntity;

    protected HeatExtractorBlock(Properties properties, BlockEntityType<? extends HeatExtractorBlockEntity> myBlockEntity) {
        super(properties);
        this.myBlockEntity = myBlockEntity;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VOXEL_SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return myBlockEntity.create(pos,state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        HeatExtractorBlockEntity blockEntity = (HeatExtractorBlockEntity)level.getBlockEntity(pos);
        assert blockEntity != null;
        blockEntity.increment_heat();
        player.sendSystemMessage(Component.literal("heat:"+blockEntity.get_heat()));
        player.sendSystemMessage(Component.literal("extraction_rate:"+blockEntity.get_base_extraction_rate_watt()));
        player.sendSystemMessage(Component.literal("heat_capacity:"+blockEntity.get_heat_capacity()));
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        }

        return createTickerHelper(blockEntityType, myBlockEntity,
                (blockEntitiyLevel,blockPos,blockState,blockEntity) -> blockEntity.tick(blockEntitiyLevel,blockPos,blockState));
    }
}
