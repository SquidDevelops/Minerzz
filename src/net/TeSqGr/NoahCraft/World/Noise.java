package net.TeSqGr.NoahCraft.World;

import net.TeSqGr.NoahCraft.World.FastNoise.*;

import java.util.Arrays;

public class Noise {

    private FastNoise myNoise = new FastNoise(3779999); // Create a FastNoise object

    public Noise() {
        myNoise.SetNoiseType(NoiseType.SimplexFractal); // Set the desired noise type

    }

    public int[] genNoise(int xCoord, int yCoord) {
        int[] heightMap = new int[256]; // 2D heightmap to create terrain

        for (int x = xCoord; x < xCoord+16; x++) {
            for (int y = yCoord; y < yCoord+16; y++) {
                heightMap[(x%16) * 16 + (y%16)] = (int) (150 * (1.0f + myNoise.GetNoise(x, y)));
            }
        }
        //System.out.println(Arrays.toString(heightMap));
        return heightMap;

    }
}