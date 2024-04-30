package com.github.quaoz.betterleads;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.option.SpruceBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceSimpleActionOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class SettingsScreen extends SpruceScreen {
	private final BetterLeadsConfig config;
	private final Screen parent;
	private final SpruceOption merchantsOption;
	private final SpruceOption hostilesOption;
	private final SpruceOption waterCreaturesOption;
	private final SpruceOption turtlesOption;
	private final SpruceOption ambientsOption;
	private final SpruceOption pandasOption;
	private final SpruceOption chainOption;
	private final SpruceOption resetOption;

	public SettingsScreen(@Nullable Screen parent) {
		super(Text.translatable("betterleads.menu.title"));
		this.config = BetterLeadsConfig.INSTANCE;
		this.parent = parent;

		this.merchantsOption = new SpruceBooleanOption(
			"betterleads.merchants.option",
			this.config.merchants_enabled::value,
			this.config.merchants_enabled::setValue,
			Text.translatable("betterleads.merchants.tooltip"),
			true
		);

		this.hostilesOption = new SpruceBooleanOption(
			"betterleads.hostiles.option",
			this.config.hostiles_enabled::value,
			this.config.hostiles_enabled::setValue,
			Text.translatable("betterleads.hostiles.tooltip"),
			true
		);

		this.waterCreaturesOption = new SpruceBooleanOption(
			"betterleads.watercreatures.option",
			this.config.water_creatures_enabled::value,
			this.config.water_creatures_enabled::setValue,
			Text.translatable("betterleads.watercreatures.tooltip"),
			true
		);

		this.turtlesOption = new SpruceBooleanOption(
			"betterleads.turtles.option",
			this.config.turtles_enabled::value,
			this.config.turtles_enabled::setValue,
			Text.translatable("betterleads.turtles.tooltip"),
			true
		);

		this.ambientsOption = new SpruceBooleanOption(
			"betterleads.ambients.option",
			this.config.ambients_enabled::value,
			this.config.ambients_enabled::setValue,
			Text.translatable("betterleads.ambients.tooltip"),
			true
		);

		this.pandasOption = new SpruceBooleanOption(
			"betterleads.pandas.option",
			this.config.pandas_enabled::value,
			this.config.pandas_enabled::setValue,
			Text.translatable("betterleads.pandas.tooltip"),
			true
		);

		this.chainOption = new SpruceBooleanOption(
			"betterleads.chain.option",
			this.config.chain_leashes::value,
			this.config.chain_leashes::setValue,
			Text.translatable("betterleads.chain.tooltip"),
			true
		);

		this.resetOption = SpruceSimpleActionOption.reset(
			btn -> {
				this.config.reset();
				MinecraftClient client = MinecraftClient.getInstance();
				this.init(client, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
			});
	}

	private int getTextHeight() {
		return (5 + this.textRenderer.fontHeight) * 3 + 5;
	}

	@Override
	protected void init() {
		super.init();

		SpruceOptionListWidget list = new SpruceOptionListWidget(
			Position.of(this, 0, 43), this.width, this.height - 43 - 29 - this.getTextHeight()
		);
		list.addSingleOptionEntry(merchantsOption);
		list.addSingleOptionEntry(hostilesOption);
		list.addSingleOptionEntry(waterCreaturesOption);
		list.addSingleOptionEntry(turtlesOption);
		list.addSingleOptionEntry(ambientsOption);
		list.addSingleOptionEntry(pandasOption);
		list.addSingleOptionEntry(chainOption);
		this.addDrawableSelectableElement(list);

		this.addDrawableSelectableElement(
			this.resetOption.createWidget(
				Position.of(this, this.width / 2 - 155, this.height - 29), 150
			)
		);
		this.addDrawableSelectableElement(
			new SpruceButtonWidget(
				Position.of(
					this,
					this.width / 2 - 155 + 160,
					this.height - 29
				),
				150,
				20,
				Text.translatable("gui.done"),
				(btn) -> {
					assert this.client != null;
					this.client.setScreen(this.parent);
				})
		);
	}
}
