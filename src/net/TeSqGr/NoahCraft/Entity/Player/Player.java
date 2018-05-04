package net.TeSqGr.NoahCraft.Entity.Player;

import net.TeSqGr.NoahCraft.Entity.Entity;
import net.TeSqGr.NoahCraft.Inventory.Inventory;
import net.TeSqGr.NoahCraft.NoahCraft;
import net.TeSqGr.NoahCraft.World.Chunk;
import net.TeSqGr.NoahCraft.World.Coordinate;

public class Player extends Entity {

    final float sentFromMyiPhone = 0x1.0p0f;

    private int previousChunkX = 0, previousChunkY = 0, chunkX = 0, chunkY = 0;


    private int[] chunk;

    private Inventory inventory;

    public Player(int entityID, Coordinate c){
            super(entityID, c.getX(), c.getY(), c.getZ());
            inventory = new Inventory();
    }


    public void update() {
        //TODO: Translate the world among the player


        if (!(Math.floor(NoahCraft.instance.getCamera().getPosition().x / 16) == chunkX))
            chunkX = (int) (Math.floor(NoahCraft.instance.getCamera().getPosition().x / 16));
        if (!(Math.floor(NoahCraft.instance.getCamera().getPosition().y / 16) == chunkY))
            chunkY = (int) (Math.floor(NoahCraft.instance.getCamera().getPosition().y / 16));

        if ((((chunkY != previousChunkY)&& chunkY >= 0) && chunkX >=0) || (((chunkX != previousChunkX) && chunkX >= 0))) {

            chunk = Chunk.genChunk(chunkX, chunkY);
            previousChunkX = chunkX;
            previousChunkY = chunkY;
        }



        System.out.println(chunk[Math.abs((int)NoahCraft.instance.getCamera().getPosition().y*256)] + " : " + chunkX + " : " + chunkY);


//        System.out.println(toString());
    }


    public void fixedUpdate() {

    }


    public void nonRenderTick() {

    }

    @Override
    public String toString(){
        return "Player : x : " + getX() + " : y : " + getY() + " : " + getZ();
    }

}
