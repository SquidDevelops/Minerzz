package net.TeSqGr.NoahCraft.World;

import net.TeSqGr.NoahCraft.World.FastNoise.*;

import java.util.Arrays;

public class Noise {

    private FastNoise myNoise;// Create a FastNoise object

    public Noise(int seed, float frequency, FastNoise.NoiseType a) {
        myNoise = new FastNoise(seed, frequency);
        myNoise.SetNoiseType(a); // Set the desired noise type
    }

    public int[] genNoise(int xCoord, int yCoord) {
        int[] heightMap = new int[256]; // 2D heightmap to create terrain

        for (int x = xCoord; x < xCoord+16; x++) {
            for (int y = yCoord; y < yCoord+16; y++) {
                heightMap[ ((Math.abs(x) == x ? x : Math.abs(x%16+16))%16 * 16) + ((Math.abs(y) == y ? y : Math.abs(y%16+16))%16) ]  = (int) (150 * (1.0f + myNoise.GetNoise(x, y)));
            }
        }
        return heightMap;

    }
}