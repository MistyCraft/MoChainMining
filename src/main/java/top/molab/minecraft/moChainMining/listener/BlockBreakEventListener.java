package top.molab.minecraft.moChainMining.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import top.molab.minecraft.moChainMining.ConfigManager;
import top.molab.minecraft.moChainMining.MoChainMining;
import top.molab.minecraft.moChainMining.breakBehavior.NormalBreakBehavior;
import top.molab.minecraft.moChainMining.chain.IBlockChain;
import top.molab.minecraft.moChainMining.chain.RecursiveBlockChain;

public class BlockBreakEventListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL))return;
        if (!ConfigManager.getInstance().isBlockAllowed(event.getBlock().getBlockData().getMaterial()))return;
        if (!ConfigManager.getInstance().isPlayerChainMining(event.getPlayer()))return;

        try{
            new BukkitRunnable(){
                @Override
                public void run() {
                    IBlockChain blockChain = ConfigManager.getInstance().getBlockChain(event.getBlock());
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            if (blockChain.length() >= ConfigManager.getInstance().getLimit()){
                                event.getPlayer().sendMessage(ConfigManager.getInstance().getMessage("message.on-limit"));
                                return;
                            }
                            blockChain.getBreakBehavior().breakBlocks(blockChain, event.getPlayer());
                        }
                    }.runTask(MoChainMining.getInstance());;
                }
            }.run();
        } catch (StackOverflowError e) {
            event.getPlayer().sendMessage(ConfigManager.getInstance().getMessage("message.on-limit"));
            return;
        }

    }
}
