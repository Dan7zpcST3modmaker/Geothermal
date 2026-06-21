package net.dan7zpc.geothermal.block.entity.heat.heat_outputs;

import net.dan7zpc.geothermal.block.custom.heat_output.HeatVentHorizontalBlock;
import net.dan7zpc.geothermal.block.entity.*;
import net.dan7zpc.geothermal.block.entity.heat.HeatTechnologyEntity;
import net.dan7zpc.geothermal.block.entity.heat.IHeatOutput;
import net.dan7zpc.geothermal.block.entity.heat.ITierSwitchable;
import net.dan7zpc.geothermal.block.entity.heat.Tier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HeatVentHorizontalBlockEntity extends HeatTechnologyEntity implements IHeatOutput {
    private boolean active = false;

    public HeatVentHorizontalBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.HEAT_VENT_HORIZONTAL_BE.get(), pos, blockState);
    }
    public HeatVentHorizontalBlockEntity(BlockPos pos, BlockState blockState, Tier tier) {
        super(ModBlockEntities.HEAT_VENT_HORIZONTAL_BE.get(), pos, blockState);
        this.set_tier(tier);
    }

    @Override
    public void set_tier(Tier newtier) {
        super.set_tier(newtier);
    }

    public void tick(Level blockEntitiyLevel, BlockPos blockPos, BlockState blockState) {
        super.tick();
        if(this.get_temperature() > this.get_activation_threshold()){this.active = true;}
        if(this.get_temperature() < this.get_deactivation_threshold()){this.active = false;}
        level.setBlockAndUpdate(blockPos,blockState.setValue(HeatVentHorizontalBlock.ACTIVE, active));
        if(!this.active){return;}
        this.spreadHeat();
    }

    private float get_deactivation_threshold() {
        return ITierSwitchable.vent_deactivation_threshold.get(this.get_tier());
    }

    private float get_activation_threshold() {
        return  ITierSwitchable.vent_activation_threshold.get(this.get_tier());
    }

    @Override
    public void spreadHeat() {
        BlockPos pos = this.getBlockPos();
        int transfer_speed = this.get_transfer_speed();
        this.addHeat(-transfer_speed);
        assert level != null;
        if (level.isClientSide()){return;}
        ((ServerLevel) level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                pos.getX()+0.5,pos.getY()-0.2,pos.getZ()+0.5,
                15,
                0.1,0,0.1,
                0.01
        );
        level.playSound(level.getNearestPlayer(
                pos.getX()+0.5,pos.getY()-0.2,pos.getZ()+0.5,
                0,true
        ),pos, SoundEvents.WIND_CHARGE_BURST.value(), SoundSource.BLOCKS,0.5f,0.005f);
    }

    @Override
    public int get_transfer_speed() {
        return 5;
    }
}
