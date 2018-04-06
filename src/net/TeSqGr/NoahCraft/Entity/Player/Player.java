package net.TeSqGr.NoahCraft.Entity.Player;

import net.TeSqGr.NoahCraft.Entity.Entity;
import net.TeSqGr.NoahCraft.Inventory.Inventory;
import net.TeSqGr.NoahCraft.World.Coordinate;

public class Player extends Entity {

    final float sentFromMyiPhone = 0x1.0p0f;

    private Inventory inventory;

    public Player(int entityID, Coordinate c){
            super(entityID, c.getX(), c.getY(), c.getZ());
            inventory = new Inventory();
    }


    public void update() {
        //TODO: Translate the world among the player
        System.out.println(toString());
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
