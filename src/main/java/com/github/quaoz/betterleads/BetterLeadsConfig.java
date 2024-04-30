package com.github.quaoz.betterleads;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class BetterLeadsConfig extends ReflectiveConfig {
	public static final BetterLeadsConfig INSTANCE = QuiltConfig.create(BetterLeads.MOD_ID, BetterLeads.MOD_ID, BetterLeadsConfig.class);

	@Comment("Whether villagers and traders can be leashed.")
	public final TrackedValue<Boolean> villagers_enabled = this.value(true);

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

	@Comment("Whether leashes can be chained.")
	public final TrackedValue<Boolean> chain_leashes = this.value(false);

	/**
	 * Resets all the config options.
	 */
	public void reset() {
		villagers_enabled.removeOverride();
		hostiles_enabled.removeOverride();
		water_creatures_enabled.removeOverride();
		turtles_enabled.removeOverride();
		ambients_enabled.removeOverride();
		pandas_enabled.removeOverride();
		chain_leashes.removeOverride();

		BetterLeads.LOGGER.info("Reset BetterLeads config");
	}
}
