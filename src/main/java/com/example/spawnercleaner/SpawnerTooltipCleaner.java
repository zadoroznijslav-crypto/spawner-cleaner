package com.example.spawnercleaner;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;

public class SpawnerTooltipCleaner implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            // Проверяем, является ли предмет спавнером
            String name = stack.getName().getString().toLowerCase();
            String id = stack.getItem().toString().toLowerCase();

            if (name.contains("спавнер") || name.contains("spawner") || id.contains("spawner")) {
                
                Text swordLine = null;
                Text mobLine = null;
                
                // Ищем нужные строки: меч и тип моба
                for (Text line : lines) {
                    String str = line.getString().toLowerCase();
                    
                    // Ищем меч
                    if (str.contains("меч") || str.contains("sword") || str.contains("оружие")) {
                        swordLine = line;
                    }
                    // Ищем тип моба (яйцо)
                    if (str.contains("моб") || str.contains("тип") || str.contains("яйцо") || str.contains("entity")) {
                        mobLine = line;
                    }
                }

                // Очищаем ВСЕ лишние строки описания (оставляем только имя предмета)
                if (lines.size() > 1) {
                    lines.subList(1, lines.size()).clear();
                }

                // Добавляем обратно строку с мобом, если нашли
                if (mobLine != null) {
                    lines.add(mobLine);
                }

                // Добавляем обратно строку с мечом, если нашли
                if (swordLine != null) {
                    lines.add(swordLine);
                } else {
                    lines.add(Text.literal("§a⚔ Меч: Обнаружен"));
                }
            }
        });
    }
}
