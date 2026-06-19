package net.dan7zpc.geothermal.block.entity.heat_extractors;

import net.dan7zpc.geothermal.Config;
import net.dan7zpc.geothermal.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TierOneHeatExtractorBlockEntity extends HeatExtractorBlockEntity {
    public TierOneHeatExtractorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TIER_ONE_HEAT_EXTRACTOR_BE.get(),pos, blockState, get_heat_capacity());
    }

    public static int get_base_extraction_rate_watt() {
        return Config.T1_HEAT_EXTRACTION_RATE.getAsInt();
    }
    public static int get_heat_capacity(){
        return Config.T1_HEAT_CAPACITY.getAsInt();
    }
}
