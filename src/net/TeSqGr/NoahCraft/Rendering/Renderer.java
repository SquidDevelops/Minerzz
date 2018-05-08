package net.TeSqGr.NoahCraft.Rendering;


import net.TeSqGr.NoahCraft.Entity.Camera;
import net.TeSqGr.NoahCraft.Input.KeyboardHandler;
import net.TeSqGr.NoahCraft.Window.Window;
import net.TeSqGr.NoahCraft.World.Chunk;
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

    private Texture blockTexture, skyTexture;

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

    private List<RenderChunk> chunks = new ArrayList<>();

    public Renderer(Window window) {
        GL.createCapabilities();
        Shaders.compileShaders();
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_SCISSOR_TEST);
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
            skyTexture = new Texture("skybox3.png");
            blockTexture = new Texture("texture3.png");
        }catch(Exception e){
            e.printStackTrace();
        }

        //glActiveTexture(GL_TEXTURE1);
        skybox = new Skybox(new Vector3f(), skyTexture);


        aspect = (float) window.getWidth() / window.getHeight();
        projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        translation = new Matrix4f().translation(new Vector3f(0.0f, 0.0f, 0.0f)).
                rotateX((float) Math.toRadians(0.0f)).
                rotateY((float) Math.toRadians(0.0f)).
                rotateZ((float) Math.toRadians(0.0f)).
                scale(1.0f);


        //2nd mesh is always broken, so flush meshes
        /*addChunk(0, 0);
        addChunk(0, 0);
        removeChunk(1);
        removeChunk(0);*/

        for(int x = -4; x<4; x++)
            for(int z = -4; z<4; z++)
                addChunk(x,z);

        /*for(int x = 0; x<8; x++)
            for(int z = 0; z<8; z++)
                addChunk(x,z);*/
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

         for(int chunk = 0; chunk < chunks.size(); chunk++){
            if ((int)(camera.getPosition().x+128)/16 < chunks.get(chunk).getChunkX()+4){
                addChunk(chunk,chunks.get(chunk).getChunkX()-8, chunks.get(chunk).getChunkZ());
                removeChunk(chunk+1);
            }
             if ((int)(camera.getPosition().x-128)/16 > chunks.get(chunk).getChunkX()-4){
                 addChunk(chunk,chunks.get(chunk).getChunkX()+8, chunks.get(chunk).getChunkZ());
                 removeChunk(chunk+1);
             }
             if ((int)(camera.getPosition().z+128)/16 < chunks.get(chunk).getChunkZ()+4){
                 addChunk(chunk,chunks.get(chunk).getChunkX(), chunks.get(chunk).getChunkZ()-8);
                 removeChunk(chunk+1);
             }
             if ((int)(camera.getPosition().z-128)/16 > chunks.get(chunk).getChunkZ()-4){
                 addChunk(chunk,chunks.get(chunk).getChunkX(), chunks.get(chunk).getChunkZ()+8);
                 removeChunk(chunk+1);
             }
        }


        skybox.update(camera.getPosition());
        skybox.render();


        for (RenderChunk chunk : chunks)
            chunk.getChunkMesh().render();

        //Shaders.setUniform("textureSampler", 1);


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

        translation.identity();
        // First do the rotation so camera rotates over its position
        translation.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        translation.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return translation;
    }

    public void addChunk(int index, int chunkX, int chunkZ){
        RenderChunk renderChunk = new RenderChunk(Chunk.genChunk(16*chunkZ, 16*chunkX), chunkX, chunkZ, blockTexture);
        chunks.add(index, renderChunk);
    }

    public void addChunk(int chunkX, int chunkZ){
        RenderChunk renderChunk = new RenderChunk(Chunk.genChunk(16*chunkZ, 16*chunkX), chunkX, chunkZ, blockTexture);
        chunks.add(renderChunk);
    }

    public void removeChunk(int index){
        unloadChunk(index);
        chunks.remove(index);
    }

    private void unloadChunk(int index){
        if(chunks.get(index) != null) {
            chunks.get(index).dispose();
            chunks.set(index, null);
        }
    }

}
