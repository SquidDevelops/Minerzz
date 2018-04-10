package net.TeSqGr.NoahCraft.Input;

import net.TeSqGr.NoahCraft.NoahCraft;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    public void update() {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            NoahCraft.instance.renderer.setdZ(0.5f);
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            NoahCraft.instance.renderer.setdZ(-0.5f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
            NoahCraft.instance.renderer.setdX(0.5f);
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
            NoahCraft.instance.renderer.setdX(-0.5f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_Q)) {
            NoahCraft.instance.renderer.setdRY(0.5f);
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_E)) {
            NoahCraft.instance.renderer.setdRY(-0.5f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_SPACE)) {
            NoahCraft.instance.renderer.setdY(-0.5f);
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
            NoahCraft.instance.renderer.setdY(0.5f);
        }
    }

}
