package com.github.quaoz.betterleads;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.LeadItem;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class BetterLeads implements ModInitializer {
	public static final String MOD_ID = "betterleads";
	public static final Logger LOGGER = LoggerFactory.getLogger("BetterLeads");

	@Override
	public void onInitialize(ModContainer mod) {
		BetterLeadsConfig config = BetterLeadsConfig.INSTANCE;
		LOGGER.info("Initialised BetterLeads");

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ActionResult result = ActionResult.PASS;

			if (config.chain_leashes.value()
				&& player.getStackInHand(hand).getItem() instanceof LeadItem
				&& entity instanceof MobEntity mob
				&& mob.canBeLeashedBy(player)
				&& player.isSneaking()
				&& !player.isSpectator()
				&& world.isClient) {
				Vec3d pos = mob.getPos();
				List<MobEntity> entities = world.getNonSpectatingEntities(
					MobEntity.class, new Box(pos.add(7, 7, 7), pos.subtract(7, 7, 7))
				);

				for (MobEntity mobEntity : entities) {
					if (mobEntity.getHoldingEntity() == player) {
						mobEntity.attachLeash(mob, true);
						result = ActionResult.SUCCESS;
					}
				}
			}

			return result;
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
			dispatcher.register(literal(MOD_ID)
				.then(argBuilder("merchants", config.merchants_enabled::setValue))
				.then(argBuilder("hostiles", config.hostiles_enabled::setValue))
				.then(argBuilder("water-creatures", config.water_creatures_enabled::setValue))
				.then(argBuilder("turtles", config.turtles_enabled::setValue))
				.then(argBuilder("ambients", config.ambients_enabled::setValue))
				.then(argBuilder("pandas", config.pandas_enabled::setValue))
				.then(argBuilder("chain-leashes", config.chain_leashes::setValue))
				.then(literal("reset").executes(context -> {
					context.getSource().sendFeedback(() ->
						Text.literal("Reset the config").formatted(Formatting.BOLD), false
					);

					config.reset();
					return Command.SINGLE_SUCCESS;
				}))
			));
	}

	private LiteralArgumentBuilder<ServerCommandSource> argBuilder(String literal, Consumer<Boolean> setter) {
		return literal(literal).then(argument("value", BoolArgumentType.bool()).executes(context -> {
			boolean value = BoolArgumentType.getBool(context, "value");
			context.getSource().sendFeedback(() ->
				Text.literal("Set " + literal + " to " + value).formatted(Formatting.BOLD), false
			);

			setter.accept(value);
			return Command.SINGLE_SUCCESS;
		}));
	}
}
