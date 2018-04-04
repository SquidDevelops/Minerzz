package net.TeSqGr.NoahCraft.World;

public enum BlockType {
    AIR(0,0),
    DIRT(1,1000),
    GRASS(2, 1000),
    STONE(3, 1500),
    COBBLESTONE(4, 1500),
    WOOD(5, 1000),
    BEDROCK(6, -1),
    WOODPLANKS(7, 1000),
    SAND(8, 1500),
    GRAVEL(9, 1500),
    GLASS(10, 1000),
    WOOL(11, 1000),
    BRICKS(12, 1500),
    OBSIDIAN(13, 16000),
    ICE(14, 1000),
    SNOW(15, 1000),
    CLAY(16, 1000);
    final int breakTime;
    final int blockID;

    BlockType(int blockID, int breakTime){
        this.breakTime = breakTime;
        this.blockID = blockID;

    }
}