package me.arian.itemdisplays;

import com.mojang.brigadier.CommandDispatcher;
import me.arian.itemdisplays.mechanic.DisplayCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemDisplays extends JavaPlugin {

    private static ItemDisplays inst;
    private final CommandDispatcher<CommandSourceStack> DISPATCHER = MinecraftServer.getServer().getCommands().getDispatcher();

    @Override
    public void onLoad() {
        inst = this;
    }

    @Override
    public void onEnable() {
        DisplayCommand.register(DISPATCHER);
        getServer().getConsoleSender().sendMessage("§aI§bt§ce§dm §eD§0i§1s§2p§3l§4a§5y§6s §8b§f1");
    }

    public static ItemDisplays get() {
        return inst;
    }
}
