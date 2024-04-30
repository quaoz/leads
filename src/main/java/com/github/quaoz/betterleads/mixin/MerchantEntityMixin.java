package com.github.quaoz.betterleads.mixin;

import com.github.quaoz.betterleads.BetterLeadsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Npc;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.village.Merchant;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows trader entities (villagers and wandering traders) to be leashed
@Mixin(MerchantEntity.class)
abstract class MerchantEntityMixin extends PassiveEntity implements Npc, Merchant {
	protected MerchantEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "canBeLeashedBy", at = @At("RETURN"), cancellable = true)
	private void onCanBeLeashedBy(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue() || !this.isLeashed() && BetterLeadsConfig.INSTANCE.merchants_enabled.value());
	}
}
