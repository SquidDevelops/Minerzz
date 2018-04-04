package net.TeSqGr.NoahCraft.World;

public enum BlockType {

    AIR(0),
    DIRT(1000),
    GRASS(1000),
    STONE(1500),
    COBBLESTONE(1500),
    WOOD(1000),
    BEDROCK(-1),
    WOODPLANKS(1000),
    SAND(1500),
    GLASS(1000),
    WOOL(1000),
    BRICKS(1500),
    OBSIDIAN(16000),
    ICE(1000),
    SNOW(1000),
    CLAY(1000);

    int breakTime;

    BlockType(int breakTime){
        //this.blockID = blockID;
        this.breakTime = breakTime;
    }
   /* AIR, DIRT, GRASS, STONE, COBBLESTONE, WOOD, BEDROCK, WOODPLANKS,
    SAND, GLASS, WOOL, BRICKS, OBSIDIAN, ICE, SNOW, CLAY */
}