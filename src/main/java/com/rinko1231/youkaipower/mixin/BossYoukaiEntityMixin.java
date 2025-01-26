package com.rinko1231.youkaipower.mixin;


import com.rinko1231.youkaipower.config.YoukaiPowerConfig;
import dev.xkmc.youkaishomecoming.content.entity.boss.BossYoukaiEntity;
import dev.xkmc.youkaishomecoming.content.entity.youkai.GeneralYoukaiEntity;
import dev.xkmc.youkaishomecoming.init.data.YHDamageTypes;
import dev.xkmc.youkaishomecoming.init.data.YHModConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BossYoukaiEntity.class, remap = false)
public abstract class BossYoukaiEntityMixin extends GeneralYoukaiEntity {


    @Shadow
    private boolean hurtCall;

    public BossYoukaiEntityMixin(EntityType<? extends GeneralYoukaiEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Inject(method = "clampDamage", at = @At("HEAD"), cancellable = true)
    protected void clampDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        if (!this.hurtCall) {
            ci.setReturnValue(0.0F);
        } else {
            if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                Entity sp = source.getEntity();
                if (sp instanceof LivingEntity le) {
                    if (le instanceof ServerPlayer severp) {
                        if (severp.isCreative()) {
                            ci.setReturnValue(amount);
                        }
                    }
                } else {
                    if (source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                        ci.setReturnValue(Math.min(4.0F, amount));
                    }

                    if (source.is(DamageTypes.GENERIC_KILL)) {
                        ci.setReturnValue(amount);
                    }
                }
            }

            if (YoukaiPowerConfig.disableReimuDamageReduction.get())
                ci.setReturnValue(amount);
            else {
                int reduction = 20;
                amount = Math.min(this.getMaxHealth() / (float) reduction, amount);
                if (YHModConfig.COMMON.reimuDamageReduction.get() && !source.is(YHDamageTypes.DANMAKU_TYPE)) {
                    amount /= 5.0F;
                }
                ci.setReturnValue(amount);
            }
        }
    }


}
