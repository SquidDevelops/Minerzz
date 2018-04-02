package net.TeSqGr.NoahCraft.Entity.Player;

import net.TeSqGr.NoahCraft.Entity.Entity;
import net.TeSqGr.NoahCraft.World.Coordinate;

public class Player extends Entity {


    public Player(int entityID, Coordinate c){
            super(entityID, c.getX(), c.getY(), c.getZ());
    }


    public void update() {

    }


    public void fixedUpdate() {

    }


    public void nonRenderTick() {

    }
}
