package net.TeSqGr.NoahCraft.Rendering;


import net.TeSqGr.NoahCraft.Entity.Camera;
import net.TeSqGr.NoahCraft.Window.Window;
import net.TeSqGr.NoahCraft.World.Chunk;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {

    private Skybox skybox;

    private Skybox blocks;

    private Texture blockTexture;

    private static final float FOV = (float) Math.toRadians(120.0f), Z_NEAR = 0.01f, Z_FAR = 1000.0f;

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

    private List<RenderChunk> chunks = new ArrayList<>();

    public Renderer(Window window) {
        GL.createCapabilities();
        Shaders.compileShaders();
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_TEXTURE_2D);
        //glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        try {
            Shaders.createUniform("projection");
            Shaders.createUniform("transform");
            Shaders.createUniform("textureSampler");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //glActiveTexture(GL_TEXTURE0);
        try {
            blockTexture = new Texture("texture3.png", GL_TEXTURE0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        skybox = new Skybox(new Vector3f());

        aspect = (float) window.getWidth() / window.getHeight();
        projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        translation = new Matrix4f().translation(new Vector3f(0.0f, 0.0f, 0.0f)).
                rotateX((float) Math.toRadians(0.0f)).
                rotateY((float) Math.toRadians(0.0f)).
                rotateZ((float) Math.toRadians(0.0f)).
                scale(1.0f);


        //2nd mesh is always broken, so flush meshes
        addChunk(0, 0);
        addChunk(0, 0);
        removeChunk(1);
        removeChunk(0);

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                addChunk(x, z);

    }

    public void render(Window window, Camera camera) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
            aspect = (float) window.getWidth() / window.getHeight();
            projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        }


        Shaders.bind();

        Shaders.setUniform("projection", projection);
        Shaders.setUniform("transform", getViewMatrix(camera));
        Shaders.setUniform("textureSampler", 0);

        dX = 0;
        dY = 0;
        dZ = 0;
        dRY = 0;
        dRX = 0;

        for (RenderChunk chunk1 : chunks) {
            if (camera.getPosition().x < chunk1.getChunkX()) {

            }
        }


        for (RenderChunk chunk : chunks)
            chunk.getChunkMesh().render();

        skybox.update(camera.getPosition());
        skybox.render();

        Shaders.unbind();

    }

    public void dispose() {
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        for (int chunk = 0; chunk < chunks.size(); chunk++)
            removeChunk(chunk);
        skybox.dispose();
        Shaders.dispose();
    }


    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();
        //  System.out.println(rotation.x);

        translation.identity();
        // First do the rotation so camera rotates over its position
        translation.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        translation.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return translation;
    }

    public void addChunk(int chunkX, int chunkZ) {
        RenderChunk renderChunk = new RenderChunk(Chunk.genChunk(16 * chunkZ, 16 * chunkX), chunkX, chunkZ, blockTexture);
        chunks.add(renderChunk);
    }

    public void removeChunk(int index) {
        unloadChunk(index);
        chunks.remove(index);
    }

    private void unloadChunk(int index) {
        if (chunks.get(index) != null) {
            chunks.get(index).dispose();
            chunks.set(index, null);
        }
    }

    private void update(){
        chunks.remove(true);
    }

    /*private void changeChunk(int index){
        unloadChunk(index);
        chunks.set(index, new);
    }*/

}
