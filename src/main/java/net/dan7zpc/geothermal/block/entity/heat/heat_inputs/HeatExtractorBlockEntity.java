package net.dan7zpc.geothermal.block.entity.heat.heat_inputs;

import net.dan7zpc.geothermal.block.entity.*;
import net.dan7zpc.geothermal.block.entity.heat.HeatTechnologyEntity;
import net.dan7zpc.geothermal.block.entity.heat.IHeatInput;
import net.dan7zpc.geothermal.block.entity.heat.ITierSwitchable;
import net.dan7zpc.geothermal.block.entity.heat.Tier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HeatExtractorBlockEntity extends HeatTechnologyEntity implements IHeatInput {
    protected int extraction_rate;
    public HeatExtractorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.HEAT_EXTRACTOR_BE.get(), pos, blockState);
        this.extraction_rate = ITierSwitchable.heat_extraction_rate_watt.get(Tier.tier_one);
    }
    public HeatExtractorBlockEntity(BlockPos pos, BlockState blockState, Tier tier){
        super(ModBlockEntities.HEAT_EXTRACTOR_BE.get(), pos, blockState);
        this.set_tier(tier);
    }

    public int get_base_extraction_rate_watt(){
        return this.extraction_rate;
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        super.tick();
        this.addHeat(this.get_base_extraction_rate_watt());
        this.spreadHeat();
    }

    @Override
    public void set_tier(Tier newtier) {
        this.extraction_rate = ITierSwitchable.heat_extraction_rate_watt.get(newtier);
        super.set_tier(newtier);
    }

    @Override
    public void spreadHeat() {
        this.try_transfer_heat(this.getBlockPos().above(), this.get_transfer_speed());
    }

    @Override
    public int get_transfer_speed() {
        return this.get_base_extraction_rate_watt();
    }
}
