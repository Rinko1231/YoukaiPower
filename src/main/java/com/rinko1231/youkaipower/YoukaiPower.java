package com.rinko1231.youkaipower;

import com.rinko1231.youkaipower.config.YoukaiPowerConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(YoukaiPower.MODID)
public class YoukaiPower
{
    public static final String MODID = "youkaipower";

    public YoukaiPower()
    {
        YoukaiPowerConfig.setup();
        MinecraftForge.EVENT_BUS.register(this);
    }


}
