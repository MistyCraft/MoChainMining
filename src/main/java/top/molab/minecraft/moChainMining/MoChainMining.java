package top.molab.minecraft.moChainMining;

import cc.carm.lib.easyplugin.utils.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.molab.minecraft.moChainMining.commands.MainCommand;
import top.molab.minecraft.moChainMining.listener.BlockBreakEventListener;

import java.text.MessageFormat;
import java.util.Objects;


public final class MoChainMining extends JavaPlugin {

    private static MoChainMining instance;


    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ColorParser.parse("&(#66ccff)--------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(ColorParser.parse("&(#66ccff)| MoChainMining   | OpenMoPlugin Project   |"));
        Bukkit.getConsoleSender().sendMessage(ColorParser.parse("&(#66ccff)| Author: Moran0710 | By MoCStudio         |"));
        Bukkit.getConsoleSender().sendMessage(ColorParser.parse("&(#66ccff)| A Lite Chain Mining Plugin               |"));
        Bukkit.getConsoleSender().sendMessage(ColorParser.parse("&(#66ccff)--------------------------------------------"));
        getLogger().info(MessageFormat.format("MoChainMining, Version:{0}", getDescription().getVersion()));

        getServer().getPluginManager().registerEvents(new BlockBreakEventListener(), this);
        Objects.requireNonNull(this.getCommand("chainmine")).setExecutor(new MainCommand());
        Objects.requireNonNull(this.getCommand("chainmine")).setTabCompleter(new MainCommand());
        getLogger().info("MoChainMining Load Successfully");
        saveDefaultConfig();

        ConfigManager.getInstance().init();
    }

    public static MoChainMining getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("MoChainMining Disabled");
    }
}
