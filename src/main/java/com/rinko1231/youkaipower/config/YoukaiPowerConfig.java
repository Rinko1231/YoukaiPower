package com.rinko1231.youkaipower.config;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;


public class YoukaiPowerConfig
{
    public static ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec.BooleanValue disableReimuDamageReduction;
    static
    {
        BUILDER.push("Youkai Power Config");


        disableReimuDamageReduction = BUILDER
                .comment("If true, Reimu's Damage reduction will be shut down completely. Youkai's Homecoming's related configs will not take effects.")
                .define("Disable Reimu's Damage Reduction",true);

        SPEC = BUILDER.build();
    }

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "YoukaiPower.toml");
    }


}