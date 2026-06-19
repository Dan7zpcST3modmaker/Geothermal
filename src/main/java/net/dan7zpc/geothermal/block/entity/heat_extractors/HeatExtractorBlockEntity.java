package net.dan7zpc.geothermal.block.entity.heat_extractors;

import net.dan7zpc.geothermal.Config;
import net.dan7zpc.geothermal.block.entity.HeatTechnologyEntity;
import net.dan7zpc.geothermal.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class HeatExtractorBlockEntity extends HeatTechnologyEntity {
    public HeatExtractorBlockEntity(BlockEntityType<?> type,BlockPos pos, BlockState blockState, int heat_capacity_joules) {
        super(type, pos, blockState,heat_capacity_joules);
    }

    public static int get_base_extraction_rate_watt(){
        return 100;
    }

    public void increment_heat() {
        this.heat += this.get_base_extraction_rate_watt();
        //this.heat = Math.clamp(this.heat+get_base_extraction_rate_watt(),0,this.heat_capacity);
    }

    public static int get_heat_capacity(){
        return 100;
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        this.increment_heat();
    }
}
