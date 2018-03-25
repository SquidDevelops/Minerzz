package net.TeSqGr.NoahCraft.Window;

import org.lwjgl.glfw.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private long window;

    public Window(int width, int height, String title){
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

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int x = (vidmode.width() - width) / 2;
        int y = (vidmode.height() - height) / 2;
        glfwSetWindowPos(window, x, y);
    }

    public void dispose(){
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

}
