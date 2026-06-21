package net.dan7zpc.geothermal.block.custom;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.geothermal.block.entity.heat.HeatTechnologyEntity;
import net.dan7zpc.geothermal.block.entity.heat.ITierSwitchable;
import net.dan7zpc.geothermal.block.entity.heat.Tier;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractHeatTechBlock extends BaseEntityBlock {
    public static final IntegerProperty TIER = IntegerProperty.create("tier",1,2);
    protected AbstractHeatTechBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        HeatTechnologyEntity blockEntity = (HeatTechnologyEntity)level.getBlockEntity(pos);
        if(blockEntity == null) return;
        if(blockEntity.get_temperature() < 70) return;
        if(entity instanceof ItemEntity) return;
        DamageSources damageSources = level.damageSources();
        entity.hurt(damageSources.hotFloor(),5);
        blockEntity.addHeat(-20);
    }

    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemInteractionResult result = ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        HeatTechnologyEntity hbe = (HeatTechnologyEntity) level.getBlockEntity(pos);
        assert hbe != null;
        result = try_upgrade(stack,state,level,pos,player);
        return result;
    }

    public ItemInteractionResult try_upgrade(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player){
        Item item = stack.getItem();
        HeatTechnologyEntity hbe = (HeatTechnologyEntity) level.getBlockEntity(pos);
        Tier tier = hbe.get_tier();
        if(stack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if(!is_suitable_for_upgrade(item,tier)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        //if(level.isClientSide()) {level.playSound(player,pos, SoundEvents.ANVIL_HIT, SoundSource.PLAYERS,1,0.5f); return ItemInteractionResult.SUCCESS;}
        level.playSound(player,pos, SoundEvents.ANVIL_HIT, SoundSource.PLAYERS,1,0.5f);
        if(!player.isCreative()) stack.setCount(stack.getCount() - 1);
        upgrade(state, level, pos,hbe);
        return ItemInteractionResult.SUCCESS;
    }

    private void upgrade(BlockState state, Level level, BlockPos pos, HeatTechnologyEntity hbe) {
        int currentTier = TIER.getValue("tier").isPresent()? TIER.getValue("tier").get() : 1;
        level.setBlockAndUpdate(pos,state.setValue(TIER, currentTier+1));
        hbe.set_tier(ITierSwitchable.tier_from_int.get(currentTier+1));
    }

    private boolean is_suitable_for_upgrade(Item item, Tier tier) {
        return item == ITierSwitchable.items_for_upgrade.get(tier);
    }


}
