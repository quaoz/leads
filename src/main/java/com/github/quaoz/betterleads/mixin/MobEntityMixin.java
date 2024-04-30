package com.github.quaoz.betterleads.mixin;

import com.github.quaoz.betterleads.BetterLeadsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows hostile mobs to be leashed
@Mixin(MobEntity.class)
abstract class MobEntityMixin extends LivingEntity {
	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract boolean isLeashed();

	@Inject(method = "canBeLeashedBy", at = @At("RETURN"), cancellable = true)
	private void onCanBeLeashedBy(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue() || !this.isLeashed() && BetterLeadsConfig.INSTANCE.hostiles_enabled.value());
	}
}
