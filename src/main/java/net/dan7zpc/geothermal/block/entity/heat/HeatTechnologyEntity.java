package net.dan7zpc.geothermal.block.entity.heat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class HeatTechnologyEntity extends BlockEntity implements ITierSwitchable {
    protected int heat = 0;
    protected int heat_capacity = 100;
    protected final ContainerData data;
    protected Tier tier = Tier.tier_one;
    public HeatTechnologyEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.set_tier(tier);
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
        tag.putInt("heat",this.get_heat());
        tag.putString("tier",this.get_tier().toString());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.heat=tag.getInt("heat");
        this.set_tier(Tier.valueOf(tag.getString("tier")));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return writeClient(new CompoundTag(),registries);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        readClient(tag,registries);
    }

    public void readClient(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag,registries);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        CompoundTag tag = pkt.getTag();
        readClient(tag==null? new CompoundTag():tag,registries);
    }

    public CompoundTag writeClient(CompoundTag tag, HolderLookup.Provider registries){
        saveAdditional(tag,registries);
        return tag;
    }

    public void sendData(){
        if(level instanceof ServerLevel serverLevel)
            serverLevel.getChunkSource().blockChanged(getBlockPos());
    }

    public void notifyUpdate(){
        setChanged();
        sendData();
    }



    public int get_heat() {
        return this.heat;
    }

    public void addHeat(int amount){
        this.heat += amount;
        this.notifyUpdate();
    }

    public void set_tier(Tier newtier){
        this.tier = newtier;
        this.heat_capacity = ITierSwitchable.heat_capacity.get(newtier);
        this.notifyUpdate();
    }

    public float get_temperature(){
        return (float) this.heat /this.heat_capacity;
    }

    public Tier get_tier() {
        return this.tier;
    }

    public int get_heat_capacity(){
        return this.heat_capacity;
    }
    public void transfer_heat(HeatTechnologyEntity addressee,int amount){
        int transferable_heat = Math.clamp(amount,0,this.get_heat());
        this.heat -= transferable_heat;
        addressee.heat += transferable_heat;
        this.notifyUpdate();
        addressee.notifyUpdate();
    }
    public void transfer_heat_to(BlockPos pos, int amount){
        HeatTechnologyEntity addressee = get_HTE_at(pos);
        if(addressee == null) return;
        transfer_heat(addressee,amount);
    }
    public void try_transfer_heat(BlockPos pos, int amount){
        HeatTechnologyEntity addressee = get_HTE_at(pos);
        if(addressee == null) return;
        if(!amIHotter(addressee)) return;
        transfer_heat(addressee,amount);
    }
    private boolean amIHotter(HeatTechnologyEntity compareTo){
        return (this.get_temperature()>compareTo.get_temperature());
    }
    private HeatTechnologyEntity get_HTE_at(BlockPos pos){
        assert this.level != null;
        BlockEntity be = this.level.getBlockEntity(pos);
        if(!(be instanceof IHeatTransfer)) return null;
        return (HeatTechnologyEntity) be;
    }

    public void tick(){
        if(this.get_temperature() > 750){
            assert level != null;
            level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
        }
    }
}
