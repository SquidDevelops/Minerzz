package net.TeSqGr.NoahCraft.World;

import javax.swing.*;
import java.io.*;
import java.util.Random;

import static net.TeSqGr.NoahCraft.World.FastNoise.NoiseType.SimplexFractal;

public class Chunk {


    private static int seedy = new Random().nextInt();

    private static Noise n = new Noise(seedy, .001f, SimplexFractal);
    private static Noise m = new Noise(seedy, .01f, SimplexFractal);

    private static String filepath = "worlds/" + JOptionPane.showInputDialog("world name") + "/chunks";

    public static int[] genChunk(int xO, int yO) {
        File file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
            //file.mkdir();
        }
        int seed = readSeed();
        if (seed != -1) {
            seedy = seed;
            n = new Noise(seedy, .001f, SimplexFractal);
            m = new Noise(seedy, .01f, SimplexFractal);
        } else {
            saveSeed(seedy);
        }

        int[] heightMap = n.genNoise(xO, yO);
        int[] treeMap = m.genNoise(xO, yO);
        int[] chunk = new int[65536];
        int pos;
        pos = 0;
        for (int height : heightMap) {
            for (int i = 0; i < height; i++)
                if (i > height - 2)
                    chunk[(i * 256) + ((pos >> 4) << 4) + (pos % 16)] = BlockType.GRASS.blockID;
                else if (i > height - 6)
                    chunk[(i * 256) + ((pos >> 4) << 4) + (pos % 16)] = BlockType.DIRT.blockID;
                else if (i == 0)
                    chunk[(i * 256) + ((pos >> 4) << 4) + (pos % 16)] = BlockType.BEDROCK.blockID;
                else
                    chunk[(i * 256) + ((pos >> 4) << 4) + (pos % 16)] = BlockType.STONE.blockID;
            pos++;
            //System.out.println(height);
        }
        for (int height : heightMap)
            for (int i = 0; i < height; i++)
                if (i > height - 1)
                    chunk[(i * 16 * 16) + ((pos / 16) * 16) + (pos % 16)] = BlockType.WOOD.blockID;
        pos++;


        return chunk;

    }

    public static void saveSeed(int seed) {


        try {
            FileOutputStream fos = new FileOutputStream(filepath + "/seed.txt");
            ObjectOutputStream writer = new ObjectOutputStream(fos);
            writer.writeInt(seed);
            writer.close();

        } catch (Exception e) {
        }
    }

    public static int readSeed() {
        //System.out.println("reading seed");
        int seed = -1;
        File file = new File(filepath + "/seed.txt");
        if (!file.exists()) {

            return seed;
        }
        try {
            FileInputStream fos = new FileInputStream(file);
            ObjectInputStream reader = new ObjectInputStream(fos);
            seed = reader.readInt();
            reader.close();
            //System.out.println(seed);

            //System.out.println("read chunk");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return seed;
    }

    public static void saveChunkToFile(int[] chunk, int x0, int y0) {
        //System.out.println("saving chunk");


        File file = new File(filepath + "/chunk" + x0 + "." + y0 + ".chnk");
        try {
            file.createNewFile();
            //file.setWritable(true);
        } catch (Exception e) {
        }
        try {

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream writer = new ObjectOutputStream(fos);
            writer.writeObject(chunk);
            writer.close();
            // System.out.println("saved chunk");
        } catch (Exception e) {
        }

    }

    public static int[] readChunkFromFile(int x0, int y0) {
        //System.out.println("reading chunk");
        int[] chunk = null;
        File file = new File(filepath + "/chunk" + x0 + "." + y0 + ".chnk");
        if (!file.exists()) {
            return null;
        }
        try {
            FileInputStream fos = new FileInputStream(file);
            ObjectInputStream reader = new ObjectInputStream(fos);
            chunk = (int[]) reader.readObject();
            //System.out.println(chunk);
            reader.close();
            //System.out.println("read chunk");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return chunk;
    }
}
