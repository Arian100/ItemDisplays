package me.arian.itemdisplays.util;

import me.arian.itemdisplays.ItemDisplays;
import org.bukkit.configuration.file.FileConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public final class Config {

    private static final FileConfiguration CONFIG = ItemDisplays.get().getConfig();

    public static boolean usePermission = CONFIG.getBoolean("usePermission");

    private Config() {
        throw new RuntimeException("You cant initialize this class!");
    }
}
