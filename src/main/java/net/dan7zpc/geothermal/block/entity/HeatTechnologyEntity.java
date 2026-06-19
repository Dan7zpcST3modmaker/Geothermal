package net.dan7zpc.geothermal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class HeatTechnologyEntity extends BlockEntity {
    protected int heat = 0;
    protected int heat_capacity = 100;
    protected final ContainerData data;
    public HeatTechnologyEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int heat_capacity_joules) {
        super(type, pos, blockState);
        this.heat_capacity = heat_capacity_joules;
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch(index){
                    case 0 -> HeatTechnologyEntity.this.heat;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: HeatTechnologyEntity.this.heat = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("heat_extractor_heat",this.get_heat());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.heat=tag.getInt("heat_extractor_heat");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public int get_heat() {
        return this.heat;
    }
}
