package net.TeSqGr.NoahCraft.Entity;

public abstract class Entity {

    public Entity(int entityID, float x, float y, float z){
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    int entityID;

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    float health = 20f;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    protected float x = 0, y = 0, z = 0;

    public abstract void update();

    public abstract void fixedUpdate();

    public abstract void nonRenderTick();

}
