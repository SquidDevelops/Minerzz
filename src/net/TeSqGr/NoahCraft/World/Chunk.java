package net.TeSqGr.NoahCraft.World;
import net.TeSqGr.NoahCraft.World.FastNoise.*;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

import static net.TeSqGr.NoahCraft.World.FastNoise.NoiseType.Perlin;
import static net.TeSqGr.NoahCraft.World.FastNoise.NoiseType.Simplex;
import static net.TeSqGr.NoahCraft.World.FastNoise.NoiseType.SimplexFractal;

public class Chunk {

    Random rand = new Random();

    static int seedy = (int) Math.ceil(Math.random()* 99999);
    //seedy = rand.nextInt(999999) + 1000;

    private static Noise n = new Noise(seedy, .001f, SimplexFractal);

    public static int[] genChunk(int xO, int yO){
        int[] heightMap = n.genNoise(xO ,yO);
        int[] chunk = new int[524288];
        int pos;
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
            //System.out.println(height);
        }


        return chunk;

    }
}
