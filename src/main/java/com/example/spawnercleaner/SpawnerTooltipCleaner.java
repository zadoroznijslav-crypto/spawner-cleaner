package com.example.spawnercleaner;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;

public class SpawnerTooltipCleaner implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            // Проверяем, является ли предмет спавнером (в названии или ID)
            String name = stack.getName().getString().toLowerCase();
            String id = stack.getItem().toString().toLowerCase();

            if (name.contains("спавнер") || name.contains("spawner") || id.contains("spawner")) {
                
                // Ищем, есть ли среди строк упоминание меча/оружия
                Text swordLine = null;
                for (Text line : lines) {
                    String str = line.getString().toLowerCase();
                    if (str.contains("меч") || str.contains("sword") || str.contains("оружие")) {
                        swordLine = line;
                        break;
                    }
                }

                // Очищаем ВСЕ лишние строки описания (оставляем только имя предмета)
                if (lines.size() > 1) {
                    lines.subList(1, lines.size()).clear();
                }

                // Если нашли строку с мечом — добавляем её единственной!
                if (swordLine != null) {
                    lines.add(swordLine);
                } else {
                    // Если строка с мечом была зашифрована, добавляем понятную плашку
                    lines.add(Text.literal("§a⚔ Меч: Обнаружен"));
                }
            }
        });
    }
}
            }
        });
    }
}
