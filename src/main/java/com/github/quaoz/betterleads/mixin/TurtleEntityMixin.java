package com.github.quaoz.betterleads.mixin;

import com.github.quaoz.betterleads.BetterLeads;
import com.github.quaoz.betterleads.BetterLeadsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows turtles to be leashed
@Mixin(TurtleEntity.class)
abstract class TurtleEntityMixin extends AnimalEntity {
	protected TurtleEntityMixin(EntityType<? extends TurtleEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(method = "canBeLeashedBy", at = @At("RETURN"), cancellable = true)
	private void onCanBeLeashedBy(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue() || !this.isLeashed() && BetterLeadsConfig.INSTANCE.turtles_enabled.value());
	}
}
