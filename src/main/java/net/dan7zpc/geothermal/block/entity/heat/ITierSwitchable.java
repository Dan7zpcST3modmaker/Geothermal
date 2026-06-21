package net.dan7zpc.geothermal.block.entity.heat;

import net.dan7zpc.geothermal.Config;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

public interface ITierSwitchable {
    Map<Tier, Integer> heat_capacity = Map.of(
            Tier.tier_one,Config.T1_HEAT_CAPACITY.getAsInt(),
            Tier.tier_two,Config.T2_HEAT_CAPACITY.getAsInt()
    );

    Map<Tier, Integer> heat_extraction_rate_watt = Map.of(
            Tier.tier_one,Config.T1_HEAT_EXTRACTION_RATE.getAsInt(),
            Tier.tier_two,Config.T2_HEAT_EXTRACTION_RATE.getAsInt()
    );

    Map<Tier, Integer> heat_capacity_multiplier = Map.of(
            Tier.tier_one,Config.T1_HEAT_ACCUMULATOR_MULTIPLIER.getAsInt(),
            Tier.tier_two,Config.T2_HEAT_ACCUMULATOR_MULTIPLIER.getAsInt()
    );

    Map<Tier, Integer> vent_activation_threshold = Map.of(
            Tier.tier_one,Config.T1_VENT_ACTIVATION_THRESHOLD.getAsInt(),
            Tier.tier_two,Config.T2_VENT_ACTIVATION_THRESHOLD.getAsInt()
    );

    Map<Tier, Integer> vent_deactivation_threshold = Map.of(
            Tier.tier_one,Config.T1_VENT_DEACTIVATION_THRESHOLD.getAsInt(),
            Tier.tier_two,Config.T2_VENT_DEACTIVATION_THRESHOLD.getAsInt()
    );

    Map<Tier, Item> items_for_upgrade = Map.of(
            Tier.tier_one,Items.IRON_BARS.asItem(),
            //Tier.tier_two,Items.COPPER_BLOCK.asItem()
            Tier.tier_two,Items.AIR.asItem()
    );

    Map<Integer, Tier> tier_from_int = Map.of(
            1,Tier.tier_one,
            2,Tier.tier_two
    );

    Map<Tier,Integer> int_from_tier = Map.of(
            Tier.tier_one,1,
            Tier.tier_two,2
    );
}
