package com.github.quaoz.betterleads.mixin;

import com.github.quaoz.betterleads.BetterLeadsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


// Allows ambient entities (bats) to be leashed
@Mixin(AmbientEntity.class)
abstract class AmbientEntityMixin extends MobEntity {
	protected AmbientEntityMixin(EntityType<? extends AmbientEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "canBeLeashedBy", at = @At("RETURN"), cancellable = true)
	private void onCanBeLeashedBy(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue() || !this.isLeashed() && BetterLeadsConfig.INSTANCE.ambients_enabled.value());
	}
}
