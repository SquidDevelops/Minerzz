package net.TeSqGr.NoahCraft.Entity.Player;

import net.TeSqGr.NoahCraft.World.Coordinate;

public class PlayerManager {
    public Coordinate getCoordinate(Player p) {
        return new Coordinate(0, 0, 0);
    }

    public void applyDamage(float damage ,Player p) {

    }

    public void movePlayer(float x, Player p) {
        movePlayer(x, p.getY(), p);
    }

    public void movePlayer(float x, float y, Player p) {
        movePlayer(x, y, p.getZ(), p);
    }

    public void movePlayer(float x, float y, float z, Player p) {
        p.setX(x);
        p.setY(y);
        p.setZ(z);
    }


}
