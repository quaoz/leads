package com.github.quaoz.betterleads;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.LeadItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BetterLeads implements ModInitializer {
	public static final String MOD_ID = "betterleads";
	public static final Logger LOGGER = LoggerFactory.getLogger("BetterLeads");

	@Override
	public void onInitialize(ModContainer mod) {
		BetterLeadsConfig config = BetterLeadsConfig.INSTANCE;
		LOGGER.info("Initialised BetterLeads");

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (config.chain_leashes.value()
				&& player.getStackInHand(hand).getItem() instanceof LeadItem
				&& entity instanceof MobEntity mob
				&& player.isSneaking()
				&& !player.isSpectator()
				&& world.isClient) {
				Vec3d pos = mob.getPos();
				List<MobEntity> entities = world.getNonSpectatingEntities(MobEntity.class, new Box(pos.add(7, 7, 7), pos.subtract(7, 7, 7)));
				for (MobEntity mobEntity : entities) {
					if (mobEntity.getHoldingEntity() == player) {
						mobEntity.attachLeash(mob, true);
						return ActionResult.SUCCESS;
					}
				}
			}

			return ActionResult.PASS;
		});
	}
}
