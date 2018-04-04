package net.TeSqGr.NoahCraft.Rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private final int vertexCount, vao, vbo;//, indiciesvbo;

    public Mesh(float[] vertices) {
        FloatBuffer verticesBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
            vertexCount = vertices.length / 3;
            verticesBuffer.put(vertices).flip();

            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);


            /*indiciesvbo = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indiciesvbo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);*/

            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glBindVertexArray(0);

        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
            /*if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }*/
        }
    }

    public int getVAO() {
        return vao;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void dispose() {
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vbo);
        //glDeleteBuffers(indiciesvbo);

        glBindVertexArray(0);
        glDeleteVertexArrays(vao);
    }
}