package net.TeSqGr.NoahCraft;

import java.util.logging.Logger;

import net.TeSqGr.NoahCraft.Entity.Camera;
import net.TeSqGr.NoahCraft.Entity.Player.Player;
import net.TeSqGr.NoahCraft.Input.Input;
import net.TeSqGr.NoahCraft.Input.KeyboardHandler;
import net.TeSqGr.NoahCraft.Input.MouseInput;
import net.TeSqGr.NoahCraft.Rendering.RenderFiller;
import net.TeSqGr.NoahCraft.Timing.Timing;
import net.TeSqGr.NoahCraft.Window.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.system.MemoryUtil.*;

import net.TeSqGr.NoahCraft.World.Coordinate;
import net.TeSqGr.NoahCraft.World.WorldFiller;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

public class NoahCraft {

    public Logger noahLogger = Logger.getLogger("Noah L");

    public GLFWErrorCallback errorCallback;

    public Window window;
    public RenderFiller renderer;
    public Timing timer;
    public Input input;
    public MouseInput mouseInput;

    public Camera getCamera() {
        return camera;
    }

    private Camera camera;
    public Player testPlayer = new Player(1, new Coordinate(0, 0, 0));

    //DELET THIS
    WorldFiller w = new WorldFiller();

    public static void main(String[] args) {
        new NoahCraft();
        instance.noahLogger.exiting("NoahCraft", "Main");
    }


    public static NoahCraft instance;

    public NoahCraft() {
        if (instance == null) {
            instance = this;
        }
        init();
        gameLoop();
        dispose();
    }

    public void init() {
        glfwSetErrorCallback(errorCallback = createPrint(System.err));

        glfwInit();
        window = new Window(640, 480, "NoahCraft");
        renderer = new RenderFiller(window);
        timer = new Timing();
        input = new Input();
        mouseInput = new MouseInput();
        mouseInput.init(window);
        camera = new Camera();
        window.visible(true);
    }

    public void dispose() {
        window.visible(false);
        renderer.dispose();
        window.dispose();
    }

    public void gameLoop() {
        double delta, accumulator = 0.0, interval = 1.0 / 20.0, alpha;
        timer.init();
        while (!window.shouldClose()) {
            timer.update();
            delta = timer.delta();
            accumulator += delta;
            input();
            while (accumulator > interval) {
                update(delta);
                accumulator -= interval;
            }
            render();
            System.out.println("FPS:" + timer.getFPS());
        }
    }

    public void input() {
        glfwPollEvents();
        mouseInput.input(window);
        input.input(window);

    }

    public void update(double delta) {
        timer.updateUPS();
        input.update(mouseInput);
    }

    public void render() {
        renderer.render(window, camera);
        window.render();
        testPlayer.update();
        timer.updateFPS();
    }

}
