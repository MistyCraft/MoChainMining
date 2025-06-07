package top.molab.minecraft.moChainMining.breakBehavior;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.molab.minecraft.moChainMining.chain.IBlockChain;

public class NormalBreakBehavior implements IBreakBehavior{
    @Override
    public void breakBlocks(IBlockChain chain, Player player) {
        ItemStack tool = player.getInventory().getItemInMainHand();
        for (Block block : chain.getBlocks()) {
            block.breakNaturally(tool);
        }
    }

    public NormalBreakBehavior(){}
}
