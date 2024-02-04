package me.arian.itemdisplays.mechanic;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.arian.itemdisplays.util.Config;
import me.arian.itemdisplays.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

@SuppressWarnings("deprecation")
public final class DisplayCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("displayitem").requires(source -> {
            if (Config.usePermission) {
                return source.getBukkitEntity().hasPermission("displayitem.command");
            } else {
                return source.hasPermission(0);
            }}).then(argument("displayName", StringArgumentType.greedyString()).executes(DisplayCommand::displayItem)));
    }

    private static int displayItem(CommandContext<CommandSourceStack> ctx) {
        final Location playerLocation = ctx.getSource().getPlayer().getBukkitEntity().getLocation();
        final String formattedName = Util.translateColors(StringArgumentType.getString(ctx, "displayName"));
        final Location itemLocation = new Location(
            ctx.getSource().getBukkitWorld(),
            playerLocation.getBlockX() + 0.5,
            playerLocation.getBlockY() + 0.075,
            playerLocation.getBlockZ() + 0.5
        );

        ItemStack itemStack = new ItemStack(ctx.getSource().getPlayer().getBukkitEntity().getItemInHand().getType());
        if (itemStack.getType().equals(Material.AIR)) {
            ctx.getSource().sendFailure(Component.literal("The item cant be air!").withStyle(ChatFormatting.RED), true);
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(formattedName);
        itemStack.setItemMeta(meta);

        ctx.getSource().getBukkitWorld().spawn(itemLocation, Item.class, item -> {
            item.setItemStack(itemStack);
            item.setCanMobPickup(false);
            item.setCanPlayerPickup(false);
            item.setWillAge(false);
            item.setCustomName(itemStack.getItemMeta().getDisplayName());
            item.setCustomNameVisible(true);
            item.setGravity(false);
            item.setInvulnerable(true);
            item.setVelocity(new Vector(0, 0, 0));
        }, CreatureSpawnEvent.SpawnReason.COMMAND);

        ctx.getSource().getPlayer().getBukkitEntity().getInventory().setItem(EquipmentSlot.HAND, new ItemStack(Material.AIR));

        return Command.SINGLE_SUCCESS;
    }
}
