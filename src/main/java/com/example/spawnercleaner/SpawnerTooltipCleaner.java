package com.example.spawnercleaner;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SpawnerTooltipCleaner implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            NbtComponent blockEntityData = stack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
            NbtComponent customData = stack.get(DataComponentTypes.CUSTOM_DATA);

            String mobName = null;

            if (blockEntityData != null) {
                NbtCompound nbt = blockEntityData.copyNbt();
                if (nbt.contains("SpawnData")) {
                    NbtCompound spawnData = nbt.getCompound("SpawnData");
                    if (spawnData.contains("entity")) {
                        mobName = spawnData.getCompound("entity").getString("id");
                    }
                }
            }

            if (mobName == null && customData != null) {
                NbtCompound nbt = customData.copyNbt();
                if (nbt.contains("mob")) {
                    mobName = nbt.getString("mob");
                } else if (nbt.contains("MobType")) {
                    mobName = nbt.getString("MobType");
                } else if (nbt.contains("GS_type")) {
                    mobName = nbt.getString("GS_type");
                }
            }

            if (mobName != null && !mobName.isEmpty()) {
                Text title = lines.get(0);
                lines.clear();
                lines.add(title);

                String cleanMobName = mobName.replace("minecraft:", "");
                cleanMobName = cleanMobName.substring(0, 1).toUpperCase() + cleanMobName.substring(1);

                lines.add(Text.literal("Тип моба: " + cleanMobName).formatted(Formatting.GREEN, Formatting.BOLD));
            }
        });
    }
}
