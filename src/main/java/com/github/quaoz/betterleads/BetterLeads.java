package com.github.quaoz.betterleads;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterLeads implements ModInitializer {
	public static final String MOD_ID = "betterleads";
	public static final Logger LOGGER = LoggerFactory.getLogger("BetterLeads");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Initialised BetterLeads");
	}
}
