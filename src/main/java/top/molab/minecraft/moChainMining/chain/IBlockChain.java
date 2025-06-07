package top.molab.minecraft.moChainMining.chain;

import org.bukkit.block.Block;
import top.molab.minecraft.moChainMining.breakBehavior.IBreakBehavior;

import java.util.List;

/**
 * 矿脉相关接口
 * 用于搜索一个矿脉，方便连锁挖掘处理
 * 方便替换算法，方便扩展
 */
public interface IBlockChain {

    /**
     * 获取矿脉
     * @return 矿脉的所有方块列表
     */
    public List<Block> getBlocks();

    /**
     * 获取挖掘行为接口
     */
    public IBreakBehavior getBreakBehavior();

    /**
     * 获取矿脉方块个数
     * @return 矿脉长度
     */
    public int length();

}
