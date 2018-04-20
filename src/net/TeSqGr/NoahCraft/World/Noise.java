package net.TeSqGr.NoahCraft.World;

import net.TeSqGr.NoahCraft.World.FastNoise.*;

import java.util.Arrays;

public class Noise {

    private FastNoise myNoise = new FastNoise(); // Create a FastNoise object

    public Noise()
    {
        myNoise.SetNoiseType(NoiseType.Perlin); // Set the desired noise type

        int[] heightMap = new int[256]; // 2D heightmap to create terrain

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                heightMap[x*16+y] = (int)(150*(1.0f+myNoise.GetNoise(x, y)));
            }
        }
        System.out.println(Arrays.toString(heightMap));
    }
}