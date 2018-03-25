package net.TeSqGr.NoahCraft;

import java.util.logging.Logger;

import net.TeSqGr.NoahCraft.Rendering.RenderFiller;
import net.TeSqGr.NoahCraft.Window.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;

public class NoahCraft {

    public Logger noahLogger = Logger.getLogger("Noah L");

    public GLFWErrorCallback errorCallback;
    public Window window;
    public RenderFiller renderer;

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
        renderer = new RenderFiller(window.getContext());
        window.visible(true);
    }

    public void dispose(){
        window.visible(false);
        window.dispose();
    }

    public void gameLoop(){
        while(!window.shouldClose()){
            input();
            update();
            render();
            sleep();
        }
    }

    public void input(){
        glfwPollEvents();
    }

    public void update(){

    }

    public void render(){
        renderer.render();
        window.render();
    }

    public void sleep(){
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
