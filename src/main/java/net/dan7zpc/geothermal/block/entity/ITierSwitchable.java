package net.dan7zpc.geothermal.block.entity;

import net.dan7zpc.geothermal.Config;

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
}
