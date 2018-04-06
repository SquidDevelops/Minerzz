package net.TeSqGr.NoahCraft.Entity.Player;

import net.TeSqGr.NoahCraft.World.Coordinate;

public class PlayerManager {
    public Coordinate getCoordinate(Player p) {
        return new Coordinate(0, 0, 0);
    }

    public void applyDamage(float damage, Player p) {
        float change = p.getHealth() - damage;
        if (change > 0)
            p.setHealth(p.getHealth() - damage);
        else ;
        //TODO: Cause the players untimely death :(


    }

    public void causeDeath() {
        //TODO: Death Mechanics
    }

    public static void movePlayer(float dx, Player p) {
        movePlayer(dx, 0, p);
    }

    public static void movePlayer(float dx, float dy, Player p) {
        movePlayer(dx, dy,0, p);
    }

    public static void movePlayer(float dx, float dy, float dz, Player p) {
        p.setX(p.getX() + dx);
        p.setY(p.getY() + dy);
        p.setZ(p.getZ() + dz);
    }







}
