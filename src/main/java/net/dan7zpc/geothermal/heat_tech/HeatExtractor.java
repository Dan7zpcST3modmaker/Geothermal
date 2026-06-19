package net.dan7zpc.geothermal.heat_tech;

public class HeatExtractor extends HeatTechnology{
    protected float extraction_rate;
    public HeatExtractor(float extraction_rate_joule_per_tick,float HeatCapacity){
        super(HeatCapacity);
        this.extraction_rate = extraction_rate_joule_per_tick;
    }
}
