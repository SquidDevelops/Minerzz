package net.TeSqGr.NoahCraft.Rendering;


import net.TeSqGr.NoahCraft.Input.KeyboardHandler;
import net.TeSqGr.NoahCraft.Window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RenderFiller {

    private Mesh Mesh_triangle, Mesh_quad, Mesh_cube, Mesh_chunk;

    private Texture texture;

    private int[] randomChunk = new int[16 * 256 * 16];

    private static final float FOV = (float) Math.toRadians(60.0f), Z_NEAR = 0.01f, Z_FAR = 1000.0f;

    private float aspect;

    private Matrix4f projection;

    private Matrix4f translation;

    public void setdX(float dX) {
        this.dX = dX;
    }

    public void setdY(float dY) {
        this.dY = dY;
    }

    public void setdZ(float dZ) {
        this.dZ = dZ;
    }

    private float dX = 0, dY = 0, dZ = 0;
    float xRot = 0.0f, zRot = 0.0f;

    private List<Mesh> meshes = new ArrayList<>();

    public RenderFiller(Window window) {
        GL.createCapabilities();
        Shaders.compileShaders();
        glEnable(GL_DEPTH_TEST);
        //glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        try {
            Shaders.createUniform("projection");
            Shaders.createUniform("transform");
            Shaders.createUniform("textureSampler");
        } catch (Exception e) {
            e.printStackTrace();
        }

        glActiveTexture(GL_TEXTURE0);
        texture = new Texture("texture.png");

        aspect = (float) window.getWidth() / window.getHeight();
        projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        translation = new Matrix4f().translation(new Vector3f(-8.0f, -16.0f, -30.0f)).
                rotateX((float) Math.toRadians(0.0f)).
                rotateY((float) Math.toRadians(zRot)).
                rotateZ((float) Math.toRadians(0.0f)).
                scale(1.0f);
        //Mesh_cube = new Mesh(CubeData.positions, CubeData.textCoords, CubeData.indices, texture);

        //meshes.add(new Mesh(CubeData.positions, CubeData.textCoords, CubeData.indices, texture));
        //meshes.add(new Mesh(CubeData.positions2, CubeData.textCoords, CubeData.indices, texture));

        for (int i = 0; i < randomChunk.length; i++)
            randomChunk[i] = new Random().nextInt(2);

        RenderChunk renderChunk = new RenderChunk(randomChunk, 0, 0);
        Mesh_chunk = new Mesh(renderChunk.getVertices(), renderChunk.getTexCoords(), renderChunk.getIndices(), texture);
        meshes.add(Mesh_chunk);
    }

    public void render(Window window) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
            aspect = (float) window.getWidth() / window.getHeight();
            projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        }

        translation.translate(dX, dY, dZ);
        dX = 0;
        dY = 0;
        dZ = 0;
//        translation.rotateY((float) Math.toRadians(1.0));

        Shaders.bind();

        Shaders.setUniform("projection", projection);
        Shaders.setUniform("transform", translation);
        Shaders.setUniform("textureSampler", 0);


        for (Mesh chunk : meshes)
            chunk.render();

        Shaders.unbind();

    }


    public void dispose() {
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        for (Mesh cube : meshes)
            cube.dispose();
        texture.dispose();
        Shaders.dispose();
    }

}
