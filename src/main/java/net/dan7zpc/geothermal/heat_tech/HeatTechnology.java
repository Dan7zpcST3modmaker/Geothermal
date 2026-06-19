package net.dan7zpc.geothermal.heat_tech;

public abstract class HeatTechnology{
    protected float heatCapacity = 0;
    public HeatTechnology(float HeatCapacity){
         this.heatCapacity = HeatCapacity;
    }
}
