package net.TeSqGr.NoahCraft.Input;

import net.TeSqGr.NoahCraft.Constants;
import net.TeSqGr.NoahCraft.Entity.Camera;
import net.TeSqGr.NoahCraft.NoahCraft;
import net.TeSqGr.NoahCraft.Window.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;
import java.security.Key;

import static org.lwjgl.glfw.GLFW.*;

public class Input implements Constants {
    Vector3f cameraInc = new Vector3f();
    DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
    DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
    public void input(Window window) {
        cameraInc.set(0, 0, 0);
        if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            cameraInc.z = -1f;
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            cameraInc.z = 1f;
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
            cameraInc.x = -1f;
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
            cameraInc.x = 1f;
        }

        if(!KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_CONTROL)){

        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_UP)) {
            NoahCraft.instance.renderer.setdRX(-1f);
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_DOWN)) {
            NoahCraft.instance.renderer.setdRX(1f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_Q)) {
            NoahCraft.instance.renderer.setdRX(-1f);
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_E)) {
            NoahCraft.instance.renderer.setdRX(1f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_SPACE)) {
            cameraInc.y = -1f;
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
            cameraInc.y = 1f;
        }

    }

    public void update(MouseInput mouseInput) {
// Update camera position
        NoahCraft.instance.getCamera().movePosition(cameraInc.x * CAMERA_POS_STEP,
                cameraInc.y * CAMERA_POS_STEP,
                cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse
//        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            NoahCraft.instance.getCamera().moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
//        }
    }

}
