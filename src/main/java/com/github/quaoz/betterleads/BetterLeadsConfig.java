package com.github.quaoz.betterleads;

import org.jetbrains.annotations.NotNull;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class BetterLeadsConfig extends ReflectiveConfig {
	public static final BetterLeadsConfig INSTANCE = QuiltConfig.create(BetterLeads.MOD_ID, BetterLeads.MOD_ID, BetterLeadsConfig.class);

	@Comment("Whether merchants (villagers and traders) can be leashed.")
	public final TrackedValue<Boolean> merchants_enabled = this.value(true);

	@Comment("Whether hostile mobs can be leashed.")
	public final TrackedValue<Boolean> hostiles_enabled = this.value(false);

	@Comment("Whether water creatures (fish and squid) can be leashed.")
	public final TrackedValue<Boolean> water_creatures_enabled = this.value(false);

	@Comment("Whether turtles can be leashed.")
	public final TrackedValue<Boolean> turtles_enabled = this.value(true);

	@Comment("Whether ambient mobs (bats) can be leashed.")
	public final TrackedValue<Boolean> ambients_enabled = this.value(false);

	@Comment("Whether pandas can be leashed.")
	public final TrackedValue<Boolean> pandas_enabled = this.value(true);

	@Comment("Whether leashes can be chained between entities.")
	public final TrackedValue<Boolean> chain_leashes = this.value(false);

	/**
	 * Resets all the config options.
	 */
	public void reset() {
		restoreDefault(merchants_enabled);
		restoreDefault(hostiles_enabled);
		restoreDefault(water_creatures_enabled);
		restoreDefault(turtles_enabled);
		restoreDefault(ambients_enabled);
		restoreDefault(pandas_enabled);
		restoreDefault(chain_leashes);

		BetterLeads.LOGGER.info("Reset BetterLeads config");
	}

	/**
	 * Restores the default value of a {@link TrackedValue}.
	 */
	private <T> void restoreDefault(@NotNull TrackedValue<T> value) {
		value.setValue(value.getDefaultValue());
	}
}
