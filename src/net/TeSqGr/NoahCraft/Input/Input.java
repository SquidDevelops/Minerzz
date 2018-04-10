package net.TeSqGr.NoahCraft.Input;

import net.TeSqGr.NoahCraft.NoahCraft;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class Input {

    public void update() {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            NoahCraft.instance.renderer.setdZ(-0.1f);
        }
    }

}
