package net.TeSqGr.NoahCraft.World;

import net.TeSqGr.NoahCraft.World.FastNoise.*;

import java.util.Arrays;

public class Noise {

    FastNoise myNoise = new FastNoise(); // Create a FastNoise object

    public Noise()
    {
        myNoise.SetNoiseType(FastNoise.NoiseType.SimplexFractal); // Set the desired noise type

        float[][] heightMap = new float[32][32]; // 2D heightmap to create terrain

        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                heightMap[x][y] = myNoise.GetNoise(x, y);
            }
        }
        System.out.println(Arrays.deepToString(heightMap));
    }
}