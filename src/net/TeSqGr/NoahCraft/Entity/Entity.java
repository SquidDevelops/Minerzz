package net.TeSqGr.NoahCraft.Entity;

public abstract class Entity {

    public Entity(int entityID, float x, float y, float z){
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    int entityID;

    protected float x = 0, y = 0, z = 0;

    public abstract void update();

    public abstract void fixedUpdate();

    public abstract void nonRenderTick();

}
