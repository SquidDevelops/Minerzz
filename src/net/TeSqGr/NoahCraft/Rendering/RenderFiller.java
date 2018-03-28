package net.TeSqGr.NoahCraft.Rendering;


import net.TeSqGr.NoahCraft.Window.Window;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public class RenderFiller {

    private int vao, vbo;
    private int vertShader, fragShader, shaderProg;
    private long context;

    public RenderFiller(long context){
        this.context = context;
        glfwMakeContextCurrent(context);
        GL.createCapabilities();

        vertShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertShader, Shaders.vertShader);
        glCompileShader(vertShader);

        if (glGetShaderi(vertShader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vertShader));
        }

        fragShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShader, Shaders.fragShader);
        glCompileShader(fragShader);

        if (glGetShaderi(fragShader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(fragShader));
        }

        shaderProg = glCreateProgram();
        glAttachShader(shaderProg, vertShader);
        glAttachShader(shaderProg, fragShader);
        //glBindFragDataLocation(shaderProg, 0, "fragC");
        glLinkProgram(shaderProg);

        if (glGetProgrami(shaderProg, GL_LINK_STATUS) != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(shaderProg));
        }


    }

    public void render(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glUseProgram(shaderProg);

        /*int posAttrib = glGetAttribLocation(shaderProg, "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 6 * 4, 0);

        int colAttrib = glGetAttribLocation(shaderProg, "color");
        glEnableVertexAttribArray(colAttrib);
        glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, 6 * 4, 3 * 4);

        int uniModel = glGetUniformLocation(shaderProg, "model");
        FloatBuffer model = BufferUtils.createFloatBuffer(16);
        new Matrix4f().get(model);
        glUniformMatrix4fv(uniModel, false, model);

        int uniView = glGetUniformLocation(shaderProg, "view");
        FloatBuffer view = BufferUtils.createFloatBuffer(16);
        new Matrix4f().get(view);
        glUniformMatrix4fv(uniView, false, view);

        int uniProjection = glGetUniformLocation(shaderProg, "projection");
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        new Matrix4f().perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
                .lookAt(0.0f, 0.0f, 10.0f,
                        0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f).get(projection);
        glUniformMatrix4fv(uniProjection, false, projection);*/

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        /*try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer vertices = stack.mallocFloat(2*3*8);
            vertices.put(1.0f).put(- 1.0f).put( - 1.0f).put(1.0f).put(0.0f).put(0.0f);
            vertices.put(1.0f).put(-1.0f).put(1.0f).put(0.0f).put(1.0f).put(0.0f);
            vertices.put(-1.0f).put(-1.0f).put(1.0f).put(0.0f).put(0.0f).put( 1.0f);
            vertices.put(-1.0f).put(-1.0f).put(-1.0f).put(1.0f).put(1.0f).put(0.0f);
            vertices.put(1.0f).put(1.0f).put(-1.0f).put(1.0f).put(0.0f).put(1.0f);
            vertices.put(1.0f).put(1.0f).put(1.0f).put(0.0f).put(1.0f).put(1.0f);
            vertices.put(-1.0f).put(1.0f).put(1.0f).put(0.0f).put(0.0f).put(0.0f);
            vertices.put(-1.0f).put(1.0f).put(-1.0f).put(1.0f).put(1.0f).put(1.0f);
            vertices.flip();

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        }*/
        FloatBuffer vertices;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            vertices = stack.mallocFloat(3 * 3);
            vertices.put(-0.6f).put(-0.4f).put(0f);
            vertices.put(0.6f).put(-0.4f).put(0f);
            vertices.put(0f).put(0.6f).put(0f);
            vertices.flip();

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
            memFree(vertices);
        }

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        //glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        if (vertices != null) {
            MemoryUtil.memFree(vertices);
        }
    }

    public void dispose(){
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteShader(vertShader);
        glDeleteShader(fragShader);
        glDeleteProgram(shaderProg);
    }

}
