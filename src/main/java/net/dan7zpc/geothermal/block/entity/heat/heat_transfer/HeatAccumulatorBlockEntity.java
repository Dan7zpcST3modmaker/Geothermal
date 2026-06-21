package net.dan7zpc.geothermal.block.entity.heat.heat_transfer;

import net.dan7zpc.geothermal.block.entity.*;
import net.dan7zpc.geothermal.block.entity.heat.HeatTechnologyEntity;
import net.dan7zpc.geothermal.block.entity.heat.IHeatAccumulator;
import net.dan7zpc.geothermal.block.entity.heat.ITierSwitchable;
import net.dan7zpc.geothermal.block.entity.heat.Tier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HeatAccumulatorBlockEntity extends HeatTechnologyEntity implements IHeatAccumulator {
    public HeatAccumulatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.HEAT_ACCUMULATOR_BE.get(), pos, blockState);
    }
    public HeatAccumulatorBlockEntity(BlockPos pos, BlockState blockState, Tier tier) {
        super(ModBlockEntities.HEAT_ACCUMULATOR_BE.get(), pos, blockState);
        this.set_tier(tier);
    }

    @Override
    public void set_tier(Tier newtier) {
        super.set_tier(newtier);
        this.heat_capacity *= ITierSwitchable.heat_capacity_multiplier.get(newtier);
    }

    public void tick(Level blockEntitiyLevel, BlockPos blockPos, BlockState blockState) {
        super.tick();
        this.spreadHeat();
    }

    @Override
    public void spreadHeat() {
        BlockPos pos = this.getBlockPos();
        int transfer_speed = this.get_transfer_speed();
        this.try_transfer_heat(pos.above(),transfer_speed);
        this.try_transfer_heat(pos.north(),transfer_speed);
        this.try_transfer_heat(pos.east(),transfer_speed);
        this.try_transfer_heat(pos.west(),transfer_speed);
        this.try_transfer_heat(pos.south(),transfer_speed);
        this.try_transfer_heat(pos.below(),transfer_speed);
    }

    @Override
    public int get_transfer_speed() {
        return 2 * ITierSwitchable.heat_capacity_multiplier.get(this.get_tier());
    }


}
