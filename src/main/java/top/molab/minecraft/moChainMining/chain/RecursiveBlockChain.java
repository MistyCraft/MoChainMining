package top.molab.minecraft.moChainMining.chain;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import top.molab.minecraft.moChainMining.breakBehavior.IBreakBehavior;

import java.util.ArrayList;
import java.util.List;

public class RecursiveBlockChain implements IBlockChain{

    private List<RecursiveBlockChain> nextChains = new ArrayList<>();
    private IBreakBehavior breakBehavior;
    private Block thisBlock;
    private BlockFace father;
    private List<Block> result = new ArrayList<>();
    private final List<BlockFace> faces = List.of(
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST,
            BlockFace.UP,
            BlockFace.DOWN
    );

    private boolean isSameMaterial(Block block){
        return block.getBlockData().getMaterial().equals(this.thisBlock.getBlockData().getMaterial());
    }

    private RecursiveBlockChain(Block block, BlockFace father , IBreakBehavior breakBehavior){
        this.thisBlock = block;

        this.father = father;
        this.breakBehavior = breakBehavior;
        this.result.add(block);

        this.findClose();
    }

    public RecursiveBlockChain(Block block, IBreakBehavior breakBehavior){
        this.thisBlock = block;


        this.father = null;
        this.breakBehavior = breakBehavior;
        this.result.add(block);

        this.findClose();
    }

    private RecursiveBlockChain(Block block, BlockFace father, List<Block> result){
        this.thisBlock = block;

        this.father = father;
        this.breakBehavior = null;
        this.result = result;
        this.result.add(block);
        this.findClose();
    }

    private void findClose(){
        Block tempBlock;
        List<BlockFace> faces = new ArrayList<>(this.faces);
        faces.remove(this.father);
        for (BlockFace face : faces){
            tempBlock = this.thisBlock.getRelative(face);
            if (this.isSameMaterial(tempBlock) && this.isBlockFound(tempBlock)){
                // Bukkit.getLogger().info(tempBlock.getLocation().toString());
                this.nextChains.add(new RecursiveBlockChain(tempBlock, tempBlock.getFace(this.thisBlock), this.result));
            }
        }
    }

    private boolean isBlockFound(Block tempBlock){
        return !this.result.stream().map(Block::getLocation).toList().contains(tempBlock.getLocation());
    }

    @Override
    public List<Block> getBlocks() {
        return this.result;
    }

    @Override
    public IBreakBehavior getBreakBehavior() {
        return this.breakBehavior;
    }

    @Override
    public int length() {
        return this.getBlocks().size();
    }
}
