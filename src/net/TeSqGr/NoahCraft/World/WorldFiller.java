package net.TeSqGr.NoahCraft.World;


import org.joml.SimplexNoise;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Random;

public class WorldFiller {
    int[] chunk = new int[16 * 16];

    public WorldFiller(){

    test(50);
    for (int a : chunk)
    System.out.print(a + ", ");
}/*public void noiseGenerator{
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double nx = x/width - 0.5, ny = y/height - 0.5;
                value[y][x] = noise(nx, ny);
            }
        }
    }
    */
    public void test(int seed1){
        int height = 16;
        int width = 16;
        //final float nx = 16;
        //final float ny = 16;
        Random rng1 = new Random(seed1);
        //int rng1 = Math.random(seed1);
        SimplexNoise gen1 = new SimplexNoise();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float nx = (float)(x/width - 0.5), ny = (float)(y/height - 0.5);
                double e = ((((1.00 * gen1.noise(1 * nx, 1 * ny)) / 2) + 0.5
                        + ((0.50 * gen1.noise(2 * nx, 2 * ny)) / 2) + 0.5
                        + ((0.0 * gen1.noise(4 * nx, 4 * ny)) / 2) + 0.5
                        + ((0.0 * gen1.noise(8 * nx, 8 * ny)) / 2) + 0.5
                        + ((0.0 * gen1.noise(16 * nx, 16 * ny)) / 2) + 0.5
                        + (0.0 * gen1.noise(32 * nx, 32 * ny))) / 2) + 0.5;
                e /= (1.00+0.50+0.0+0.0+0.0+0.0);
                e = Math.pow(e, 5.00);
                chunk[y*16+x] = (int)(e * 150);
            }
        }
    }
}
