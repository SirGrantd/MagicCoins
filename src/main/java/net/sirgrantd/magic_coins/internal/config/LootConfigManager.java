package net.sirgrantd.magic_coins.internal.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.loading.FMLPaths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class LootConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Path CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("magic_coins");

    public static final Set<Identifier> COMMON_LOOTS = new HashSet<>();
    public static final Set<Identifier> UNCOMMON_LOOTS = new HashSet<>();
    public static final Set<Identifier> RARE_LOOTS = new HashSet<>();
    public static final Set<Identifier> EPIC_LOOTS = new HashSet<>();

    public static void loadConfigs() {

        try {
            Files.createDirectories(CONFIG_DIR);
            loadOrGenerate("common.json", COMMON_LOOTS, List.of());
            loadOrGenerate("uncommon.json", UNCOMMON_LOOTS, List.of());
            loadOrGenerate("rare.json", RARE_LOOTS, List.of());
            loadOrGenerate("epic.json", EPIC_LOOTS, List.of());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void loadOrGenerate(String fileName, Set<Identifier> targetSet, List<String> defaultValues)
            throws IOException {

        Path filePath = CONFIG_DIR.resolve(fileName);

        if (!Files.exists(filePath)) {
            ConfigFormat format = new ConfigFormat();
            format.loot_tables = defaultValues;
            Files.writeString(filePath, GSON.toJson(format));
        }

        String json = Files.readString(filePath);
        ConfigFormat parsed = GSON.fromJson(json, ConfigFormat.class);
        targetSet.clear();

        if (parsed != null && parsed.loot_tables != null) {
            for (String loc : parsed.loot_tables) {
                targetSet.add(Identifier.parse(loc));
            }
        }
    }

    private static class ConfigFormat {
        List<String> loot_tables;
    }
}
