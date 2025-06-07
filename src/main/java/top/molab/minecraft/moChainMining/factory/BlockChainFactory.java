package top.molab.minecraft.moChainMining.factory;

import org.bukkit.block.Block;
import top.molab.minecraft.moChainMining.breakBehavior.IBreakBehavior;
import top.molab.minecraft.moChainMining.breakBehavior.NormalBreakBehavior;
import top.molab.minecraft.moChainMining.chain.IBlockChain;
import top.molab.minecraft.moChainMining.chain.RecursiveBlockChain;

public class BlockChainFactory {
    public static IBlockChain makeBlockChain(String chainType, String breakBehaviorType, Block block){

        IBreakBehavior breakBehavior;

        switch (breakBehaviorType){
            case "break": breakBehavior = new NormalBreakBehavior(); break;
            default: breakBehavior = new NormalBreakBehavior();
        }

        return new RecursiveBlockChain(block, breakBehavior);

        }
    }

