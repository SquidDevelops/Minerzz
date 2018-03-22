package net.TeSqGr.NoahCraft.Entity;

public interface Entity {

    int entityID = -1;
    void update();

    void fixedUpdate();

    void nonRenderTick();

}
