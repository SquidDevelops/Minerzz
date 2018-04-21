package net.TeSqGr.NoahCraft.Rendering;


import net.TeSqGr.NoahCraft.Entity.Camera;
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
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Renderer {

    private Skybox skybox;

    private Texture texture;

    private int[] randomChunk = new int[16 * 256 * 16], solidChunk = new int[16 * 256 * 16], oneBlockChunk = new int[16 * 256 * 16];

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

    public Renderer(Window window) {
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
        texture = new Texture("texture3.png");

        glActiveTexture(GL_TEXTURE1);
        skybox = new Skybox();

        aspect = (float) window.getWidth() / window.getHeight();
        projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        translation = new Matrix4f().translation(new Vector3f(-8.0f, -16.0f, -30.0f)).
                rotateX((float) Math.toRadians(0.0f)).
                rotateY((float) Math.toRadians(0.0f)).
                rotateZ((float) Math.toRadians(0.0f)).
                scale(1.0f);

        for (int i = 0; i < randomChunk.length; i++)
            randomChunk[i] = new Random().nextInt(3);

        RenderChunk renderChunk = new RenderChunk(randomChunk, 0, 0, texture.size);
        meshes.add(new Mesh(renderChunk.getVertices(), renderChunk.getTexCoords(), renderChunk.getIndices(), texture));

        for (int i = 0; i < solidChunk.length; i++)
            solidChunk[i] = 1;

        RenderChunk renderChunk2 = new RenderChunk(solidChunk, 1, 0, texture.size);
        meshes.add(new Mesh(renderChunk2.getVertices(), renderChunk2.getTexCoords(), renderChunk2.getIndices(), texture));

        oneBlockChunk[20] = 2;
        RenderChunk renderChunk3 = new RenderChunk(oneBlockChunk, 4, 0, texture.size);
        meshes.add(new Mesh(renderChunk3.getVertices(), renderChunk3.getTexCoords(), renderChunk3.getIndices(), texture));
    }

    public void render(Window window, Camera camera) {
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
        Shaders.setUniform("transform", getViewMatrix(camera));
        Shaders.setUniform("textureSampler", 0);

        dX = 0;
        dY = 0;
        dZ = 0;
        dRY = 0;
        dRX = 0;


        for (Mesh chunk : meshes)
            chunk.render();

        skybox.render();


        Shaders.unbind();

    }

    public void dispose() {
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        for (Mesh chunk : meshes)
            chunk.dispose();
        texture.dispose();
        Shaders.dispose();
    }


    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();

        translation.identity();
        // First do the rotation so camera rotates over its position
        translation.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        translation.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return translation;
    }

    public void addChunk(int[] blocks, int chunkX, int chunkZ){
        RenderChunk renderChunk = new RenderChunk(blocks, chunkX, chunkZ, texture.size);
        meshes.add(new Mesh(renderChunk.getVertices(), renderChunk.getTexCoords(), renderChunk.getIndices(), texture));
    }

}
