package net.dan7zpc.geothermal.block.custom.heat_extractors;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.geothermal.Geothermal;
import net.dan7zpc.geothermal.block.entity.ModBlockEntities;
import net.dan7zpc.geothermal.block.entity.heat_extractors.HeatExtractorBlockEntity;
import net.dan7zpc.geothermal.block.entity.heat_extractors.TierOneHeatExtractorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TierOneHeatExtractorBlock extends HeatExtractorBlock {
    public static final MapCodec<TierOneHeatExtractorBlock> CODEC = simpleCodec(TierOneHeatExtractorBlock::new);
    public TierOneHeatExtractorBlock(Properties properties) {
        super(properties, ModBlockEntities.TIER_ONE_HEAT_EXTRACTOR_BE.get());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
