package net.TeSqGr.NoahCraft;

import java.util.logging.Logger;

import net.TeSqGr.NoahCraft.Entity.Player.Player;
import net.TeSqGr.NoahCraft.Rendering.RenderFiller;
import net.TeSqGr.NoahCraft.Timing.Timing;
import net.TeSqGr.NoahCraft.Window.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.system.MemoryUtil.*;

import net.TeSqGr.NoahCraft.World.Coordinate;
import org.lwjgl.glfw.GLFWErrorCallback;

public class NoahCraft {

    public Logger noahLogger = Logger.getLogger("Noah L");

    public GLFWErrorCallback errorCallback;
    public Window window;
    public RenderFiller renderer;
    public Timing timer;
    public Player testPlayer = new Player(1, new Coordinate(0,0,0));

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

    public void init(){
        glfwSetErrorCallback(errorCallback = createPrint(System.err));
        glfwInit();
        window = new Window(640, 480,"NoahCraft");
        renderer = new RenderFiller(window);
        timer = new Timing();
        window.visible(true);
    }

    public void dispose(){
        window.visible(false);
        renderer.dispose();
        window.dispose();
    }

    public void gameLoop(){
        double delta, accumulator = 0.0, interval = 1.0/20.0, alpha;
        timer.init();
        while(!window.shouldClose()){
            timer.update();
            delta = timer.delta();
            accumulator += delta;
            input();
            while(accumulator > interval) {
                update(delta);
                accumulator -= interval;
            }
            render();
            System.out.println("FPS:" + timer.getFPS());
        }
    }

    public void input(){
        glfwPollEvents();
    }

    public void update(double delta){
        timer.updateUPS();
    }

    public void render(){
        renderer.render(window);
        window.render();
        testPlayer.update();
        timer.updateFPS();
    }

}
