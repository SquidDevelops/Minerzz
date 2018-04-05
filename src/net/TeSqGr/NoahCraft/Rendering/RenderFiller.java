package net.TeSqGr.NoahCraft.Rendering;


import net.TeSqGr.NoahCraft.Window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

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

    private int[] randomChunk = new int[16*256*16];

    private static final float FOV = (float) Math.toRadians(60.0f), Z_NEAR = 0.01f, Z_FAR = 1000.0f;

    private float aspect;

    private Matrix4f projection;

    float xRot = 0.0f;

    private List<Mesh> meshes = new ArrayList<>();

    public RenderFiller(Window window){
        GL.createCapabilities();
        Shaders.compileShaders();
        glEnable(GL_DEPTH_TEST);
        //glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        try {
            Shaders.createUniform("projection");
            Shaders.createUniform("transform");
            Shaders.createUniform("textureSampler");
        }catch (Exception e){
            e.printStackTrace();
        }

        glActiveTexture(GL_TEXTURE0);
        texture = new Texture("texture.png");

        aspect = (float) window.getWidth()/window.getHeight();
        projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);

        //Mesh_cube = new Mesh(CubeData.positions, CubeData.textCoords, CubeData.indices, texture);

        //meshes.add(new Mesh(CubeData.positions, CubeData.textCoords, CubeData.indices, texture));
        //meshes.add(new Mesh(CubeData.positions2, CubeData.textCoords, CubeData.indices, texture));

        for (int i = 0; i < randomChunk.length; i++)
            randomChunk[i] = new Random().nextInt(2);

        RenderChunk renderChunk = new RenderChunk(randomChunk, 0, 0);
       Mesh_chunk = new Mesh(renderChunk.getVertices(), renderChunk.getTexCoords(), renderChunk.getIndices(), texture );
        meshes.add(Mesh_chunk);
    }

    public void render(Window window){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (window.isResized()){
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
            aspect = (float) window.getWidth()/window.getHeight();
            projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        }

        xRot += 0.01f;

        Shaders.bind();

        Shaders.setUniform("projection", projection);
        Shaders.setUniform("transform", new Matrix4f().identity().translate(new Vector3f(-8.0f,0.0f,-30.0f)).
                rotateX((float)Math.toRadians(xRot)).
                rotateY((float)Math.toRadians(45)).
                rotateZ((float)Math.toRadians(45)).
                scale(1.0f));
        Shaders.setUniform("textureSampler", 0);


        for( Mesh cube : meshes )
            cube.render();

        Shaders.unbind();

    }

    public float[] generateChunkVerts(int[] chunk){
        float[] chunkV = new float[16*16*16*18*6];
        int size = 0, coord = 0;
        for(int block : chunk){
            if(block != 0){
                System.arraycopy(QuadVerts.genXYFace(((float)coord%16)+0.5f),0, chunkV, size, 18);
                size+=18;
                System.arraycopy(QuadVerts.genXYFace(((float)coord%16)-0.5f), 0, chunkV, size, 18);
                size+=18;
                System.arraycopy(QuadVerts.genYZFace(((float)(coord/16))+0.5f),0, chunkV, size, 18);
                size+=18;
                System.arraycopy(QuadVerts.genYZFace(((float)(coord/16))-0.5f),0, chunkV, size, 18);
                size+=18;
                System.arraycopy(QuadVerts.genXZFace(((float)(coord/256))+0.5f),0, chunkV, size, 18);
                size+=18;
                System.arraycopy(QuadVerts.genXZFace(((float)(coord/256))-0.5f),0, chunkV, size, 18);
                size+=18;
            }
            coord++;
        }
        float[] chunkVFinal = new float[size];
        System.arraycopy(chunkV, 0, chunkVFinal, 0, size);
        return chunkVFinal;
    }

    public float[] generateCube(){
        float[] cube = new float[18*6];
        System.arraycopy(QuadVerts.genXYFace(0.5f), 0, cube, 0, 18);
        System.arraycopy(QuadVerts.genXYFace(-0.5f), 0, cube, 18, 18);
        System.arraycopy(QuadVerts.genYZFace(0.5f), 0, cube, 18*2, 18);
        System.arraycopy(QuadVerts.genYZFace(-0.5f), 0, cube, 18*3, 18);
        System.arraycopy(QuadVerts.genXZFace(0.5f), 0, cube, 18*4, 18);
        System.arraycopy(QuadVerts.genXZFace(-0.5f), 0, cube, 18*5, 18);
        return cube;
    }

    public void dispose(){
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        for (Mesh cube : meshes)
            cube.dispose();
        texture.dispose();
        Shaders.dispose();
    }

}
