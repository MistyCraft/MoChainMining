package top.molab.minecraft.moChainMining.breakBehavior;

import org.bukkit.entity.Player;
import top.molab.minecraft.moChainMining.chain.IBlockChain;

public interface IBreakBehavior {
    public void breakBlocks(IBlockChain chain, Player player);
}
