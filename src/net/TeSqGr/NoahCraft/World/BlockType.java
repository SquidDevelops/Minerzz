package net.TeSqGr.NoahCraft.World;

public enum BlockType {

    AIR(0),
    DIRT(1000),
    GRASS(1000);

    int breakTime;

    BlockType(int breakTime){
        //this.blockID = blockID;
        this.breakTime = breakTime;
    }
   /* AIR, DIRT, GRASS, STONE, COBBLESTONE, WOOD, BEDROCK, WOODPLANKS,
    SAND, GLASS, WOOL, BRICKS, OBSIDIAN, ICE, SNOW, CLAY */
}