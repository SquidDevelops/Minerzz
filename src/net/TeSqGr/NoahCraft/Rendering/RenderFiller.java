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

    private int[] randomChunk = new int[16 * 256 * 16], solidChunk = new int[16 * 256 * 16];

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

    public void setdRY(float dRY) {
        this.dRY = dRY;
    }

    private float dRY = 0;

    public void setdRX(float dRX) {
        this.dRX = dRX;
    }

    private float dRX = 0;

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
                rotateY((float) Math.toRadians(0.0f)).
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

        for (int i = 0; i < solidChunk.length; i++)
            solidChunk[i] = 1;

        RenderChunk renderChunk2 = new RenderChunk(solidChunk, 2, 0);
        meshes.add(new Mesh(renderChunk2.getVertices(), renderChunk2.getTexCoords(), renderChunk2.getIndices(), texture));
    }

    public void render(Window window) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
            aspect = (float) window.getWidth() / window.getHeight();
            projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        }


//        translation.rotateY((float) Math.toRadians(1.0));

        Shaders.bind();

        Shaders.setUniform("projection", projection);
        Shaders.setUniform("transform", translation);
        Shaders.setUniform("textureSampler", 0);

        dX = 0;
        dY = 0;
        dZ = 0;
        dRY = 0;
        dRX = 0;


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

    public void update() {
        translation.translate(dX, dY, dZ);
        projection.rotateY((float) Math.toRadians((dRY)));
        projection.rotateX((float) Math.toRadians((dRX)));

    }

    public void translate(float x, float y, float z) {
        translation.translate(x, y, z);
    }

}
