package net.TeSqGr.NoahCraft.World;
import net.TeSqGr.NoahCraft.World.FastNoise.*;

import java.util.ArrayList;

import static net.TeSqGr.NoahCraft.World.FastNoise.NoiseType.SimplexFractal;

public class Chunk {

    private static Noise n = new Noise(3779999, .001f, SimplexFractal);
    private static Noise m = new Noise(3779999, .1f, SimplexFractal);

    public static int[] genChunk(int xO, int yO){
        int[] heightMap = n.genNoise(xO ,yO);
        int[] treeMap = m.genNoise(xO, yO);
        int[] chunk = new int[65536];
        ArrayList<Integer> treeLocations = new ArrayList<>();
        int pos = 0;
        for (int location : treeMap) {
            if(location > 0.9f) treeLocations.add(pos);
            pos++;
        }
        pos = 0;
        for (int height : heightMap) {
            for (int i = 0; i < height; i++)
                if(i > height-2)
                    chunk[(i*16*16)+((pos/16)*16)+(pos%16)] = BlockType.GRASS.blockID;
                else if(i > height-6)
                    chunk[(i*16*16)+((pos/16)*16)+(pos%16)] = BlockType.DIRT.blockID;
                else if (i==0)
                    chunk[(i*16*16)+((pos/16)*16)+(pos%16)] = BlockType.BEDROCK.blockID;
                else
                    chunk[(i*16*16)+((pos/16)*16)+(pos%16)] = BlockType.STONE.blockID;
            pos++;

        }


        return chunk;
    }
}
