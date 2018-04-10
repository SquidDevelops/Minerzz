package net.TeSqGr.NoahCraft.Window;

import net.TeSqGr.NoahCraft.Input.KeyboardHandler;
import org.lwjgl.glfw.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private long window;

    private int width, height;

    private GLFWKeyCallback keyCallback;

    private boolean resized = false;

    public Window(int width, int height, String title){

        this.height = height;
        this.width = width;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_DECORATED, GL_TRUE);
        glfwWindowHint(GLFW_FOCUSED, GL_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        glfwSetFramebufferSizeCallback(window, (window, _width, _height) -> {
            this.width = _width;
            this.height = _height;
            this.setResized(true);
        });

        glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int x = (vidmode.width() - width) / 2;
        int y = (vidmode.height() - height) / 2;

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);

        glfwSetWindowPos(window, x, y);
    }

    public void dispose(){
        keyCallback.free();
        glfwDestroyWindow(window);
    }

    public void visible(boolean show){
        if (show)
            glfwShowWindow(window);
        else
            glfwHideWindow(window);
    }

    public void render(){
        glfwSwapBuffers(window);
    }

    public void setTitle(String title){
        glfwSetWindowTitle(window, title);
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }

    public long getContext(){
        return window;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isResized(){
        return resized;
    }

    public void setResized(boolean resized){
        this.resized = resized;
    }
}
