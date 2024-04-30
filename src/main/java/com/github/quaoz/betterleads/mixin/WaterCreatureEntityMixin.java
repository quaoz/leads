package com.github.quaoz.betterleads.mixin;

import com.github.quaoz.betterleads.BetterLeadsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows water creatures (fish) to be leashed
@Mixin(WaterCreatureEntity.class)
abstract class WaterCreatureEntityMixin extends PathAwareEntity {
	protected WaterCreatureEntityMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(method = "canBeLeashedBy", at = @At("RETURN"), cancellable = true)
	private void onCanBeLeashedBy(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue() || !this.isLeashed() && BetterLeadsConfig.INSTANCE.water_creatures_enabled.value());
	}
}
