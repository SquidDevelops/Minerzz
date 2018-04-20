package net.TeSqGr.NoahCraft.World;

public class Chunk {

    private static Noise n = new Noise();

    public static int[] genChunk(int xO, int yO){
        int[] heightMap = n.genNoise(xO ,yO);
        int[] chunk = new int[65536];
        int pos = 0;
        for (int height : heightMap) {
            for (int i = 0; i < height; i++)
                if(i > height-1)
                    chunk[(i*16*16)+((pos/16)*16)+(pos%16)] = BlockType.GRASS.blockID;
                else if(i > height-6)
                    chunk[(i*16*16)+((pos/16)*16)+(pos%16)] = BlockType.DIRT.blockID;
                else
                    chunk[(i*16*16)+((pos/16)*16)+(pos%16)] = BlockType.STONE.blockID;
            pos++;
        }

        return chunk;
    }

}
