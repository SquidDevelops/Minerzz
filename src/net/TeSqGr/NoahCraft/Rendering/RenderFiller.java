package net.TeSqGr.NoahCraft.Rendering;


import net.TeSqGr.NoahCraft.Window.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;

public class RenderFiller {

    public RenderFiller(long context){
        glfwMakeContextCurrent(context);
        GL.createCapabilities();
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    public void render(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

}
