package net.TeSqGr.NoahCraft;

import java.util.logging.Logger;

public class NoahCraft {

    public Logger noahLogger = Logger.getLogger("Noah L");

    public static void main(String[] args) {
        new NoahCraft();
        instance.noahLogger.exiting("NoahCraft", "Main");




    }

    public static NoahCraft instance;

    public NoahCraft() {
        if (instance == null) {
            instance = this;
        }
    }

}
