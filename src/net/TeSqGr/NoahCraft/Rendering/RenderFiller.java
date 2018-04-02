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
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;
import java.util.Random;

public class RenderFiller {

    private Mesh Mesh_triangle, Mesh_quad, Mesh_cube, Mesh_chunk;

    private int[] randomChunk = new int[16*16*16];

    private static final float FOV = (float) Math.toRadians(60.0f), Z_NEAR = 0.01f, Z_FAR = 1000.0f;

    private float aspect;

    private Matrix4f projection;

    float xRot = 0.0f;

    float yRot = 0.0f;

    float zRot = 0.0f;

    public RenderFiller(Window window){

        GL.createCapabilities();
        Shaders.compileShaders();
//        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        try {
            Shaders.createUniform("projection");
            Shaders.createUniform("transform");
        }catch (Exception e){
            e.printStackTrace();
        }

        aspect = (float) window.getWidth()/window.getHeight();
        projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);

        Mesh_triangle = new Mesh(
            new float[]{0.0f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f});

        Mesh_quad = new Mesh(QuadVerts.genXYFace(-1.05f));

        Mesh_cube = new Mesh(generateCube());

        for (int i = 0; i < randomChunk.length; i++)
            randomChunk[i] = new Random().nextInt(2);

        Mesh_chunk = new Mesh(generateChunkVerts(randomChunk));

    }

    public void render(Window window){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (window.isResized()){
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
            aspect = (float) window.getWidth()/window.getHeight();
            projection = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
        }

        xRot += 1f;

        yRot += 0f;

        zRot += 0f;


        Shaders.bind();

        Shaders.setUniform("projection", projection);

        Shaders.setUniform("transform", new Matrix4f().identity().translate(new Vector3f(0f,0.0f,-2.0f)).
                rotateX((float)Math.toRadians(xRot)).
                rotateY((float)Math.toRadians(yRot)).
                rotateZ((float)Math.toRadians(zRot)).
                scale(1.0f));

        glBindVertexArray(Mesh_cube.getVAO());
        glEnableVertexAttribArray(0);
//        glEnableVertexAttribArray(1);
//        glDrawElements(GL_TRIANGLES, Mesh_quad.getVertexCount(), GL_UNSIGNED_INT, 0);
        glBufferData(GL_ARRAY_BUFFER, Mesh_cube.getVAO(), GL_STATIC_DRAW);
        glDrawArrays(GL_TRIANGLES, 0, Mesh_cube.getVertexCount());
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

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
        Mesh_triangle.dispose();
        Mesh_chunk.dispose();
        Mesh_quad.dispose();
        Mesh_cube.dispose();
        Shaders.dispose();
    }

}
