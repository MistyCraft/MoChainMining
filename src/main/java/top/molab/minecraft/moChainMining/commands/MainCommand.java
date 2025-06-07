package top.molab.minecraft.moChainMining.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.molab.minecraft.moChainMining.ConfigManager;

import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof ConsoleCommandSender){return true;}
        if (!sender.hasPermission("MoChainMining.command")){
            sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-permission-lack"));
            return true;
        }
        if (args.length == 0){
            setToggle(sender);
            return true;
        }
        else if(args.length == 1){
            switch (args[0]){
                case "on":
                    if (! ConfigManager.getInstance().isPlayerChainMining((Player) sender)){
                        sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-enable"));
                        ConfigManager.getInstance().addPlayer((Player) sender);
                    } else{
                        sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-already-enable"));
                    }
                    return true;
                case "off":
                    if (ConfigManager.getInstance().isPlayerChainMining((Player) sender)){
                        sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-disable"));
                        ConfigManager.getInstance().removePlayer((Player) sender);
                    } else{
                        sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-already-disable"));
                    }
                    return true;
                case "toggle":
                    setToggle(sender);
                    return true;
                case "reload":
                    if (sender.hasPermission("MoChainMining.command.admin")) {
                        ConfigManager.getInstance().init();
                        sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-reload"));
                    }
                    else sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-permission-lack"));
                    return true;
                default:
                    sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-command-error"));
                    return true;
            }
        }
        sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-command-error"));
        return true;
    }

    private static void setToggle(@NotNull CommandSender sender) {
        if (ConfigManager.getInstance().isPlayerChainMining((Player) sender)){
            ConfigManager.getInstance().removePlayer((Player) sender);
            sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-disable"));
        } else{
            ConfigManager.getInstance().addPlayer((Player) sender);
            sender.sendMessage(ConfigManager.getInstance().getMessage("message.on-enable"));
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1){
            if (sender.hasPermission("MoChainMining.command.admin")){
                return List.of("on", "off", "toggle", "reload");
            }
            return List.of("on", "off", "toggle");
        }
        return List.of();
    }
}
