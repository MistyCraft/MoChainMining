package top.molab.minecraft.moChainMining;


import cc.carm.lib.easyplugin.utils.ColorParser;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.N;
import top.molab.minecraft.moChainMining.breakBehavior.NormalBreakBehavior;
import top.molab.minecraft.moChainMining.chain.IBlockChain;
import top.molab.minecraft.moChainMining.chain.RecursiveBlockChain;
import top.molab.minecraft.moChainMining.factory.BlockChainFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigManager {

    private static volatile ConfigManager instance;
    private List<Material> allowBlocks;
    private int limit;
    private boolean chainMineMessage;
    private boolean checkPermission;
    private FileConfiguration config;
    private String chainType;
    private String breakBehaviorType;
    private final List<Player> chainMiners = new ArrayList<>();

    public void addPlayer(Player player){
        chainMiners.add(player);
    }

    public void removePlayer(Player player){
        chainMiners.remove(player);
    }

    public List<Player> getChainMiners(){
        return chainMiners;
    }

    public boolean isPlayerChainMining(Player player){
        if (this.checkPermission && ! player.hasPermission("MoChainMining.command")){
            if (this.chainMiners.contains(player)){
                this.chainMiners.remove(player);
                player.sendMessage(this.getMessage("message.on-permission-disable"));
            }
            return false;
        }
        return this.chainMiners.contains(player);
    }



    private ConfigManager() { }

    public List<Material> getAllowBlocks() {
        return allowBlocks;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isChainMineMessage() {
        return chainMineMessage;
    }

    public boolean isCheckPermission() {
        return checkPermission;
    }

    public boolean isBlockAllowed(Material material) {
        return allowBlocks.contains(material);
    }

    public IBlockChain getBlockChain(Block block){
        return new RecursiveBlockChain(block, new NormalBreakBehavior());
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getMessage(String key){
        return ColorParser.parse(Objects.requireNonNull(this.config.getString(key)));
    }

    // 双重检查锁定获取单例
    public static ConfigManager getInstance() {
        if (instance == null) {                     // 第一次检查
            synchronized (ConfigManager.class) {    // 同步锁
                if (instance == null) {             // 第二次检查
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    public void init(){
        MoChainMining.getInstance().reloadConfig();
        FileConfiguration config = MoChainMining.getInstance().getConfig();
        if (!Objects.equals(config.getString("version"), "1.0")){
            MoChainMining.getInstance().getLogger().warning("Config Version didn't matched");
            throw new RuntimeException("Config Version didn't matched");
        }

        // 载入允许连锁的方块
        this.allowBlocks = new ArrayList<>();
        for (String block : config.getStringList("block")) {
            String blockname = block.toUpperCase();
            Material material = Material.getMaterial(blockname);
            if (material != null) {
                this.allowBlocks.add(material);
                MoChainMining.getInstance().getLogger().info("Block: "+material.getBlockTranslationKey());
            }
            else{
                MoChainMining.getInstance().getLogger().warning("Block: "+block+" not found");
            }
        }

        // 载入全局配置
        this.limit =  config.getInt("global.limit");
        this.chainMineMessage = config.getBoolean("global.chain-mine-message");
        this.checkPermission = config.getBoolean("permissions.disable-on-lack-permission");

        // 载入连锁模式

        this.breakBehaviorType = config.getString("global.drop-mode");

        this.config = config;
    }


}
