package net.TeSqGr.NoahCraft.Rendering;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL13.GL_TEXTURE1;


public class Skybox {

    private Mesh skyboxMesh;

    private Texture skyTexture;

    public Skybox(Vector3f position) {
        try {
            skyTexture = new Texture("texture3.png", GL_TEXTURE1);
        }catch(Exception e){
            e.printStackTrace();
        }
        update(position);
    }

    public void update(Vector3f position) {
        float[] vertices = new float[72];
        for (int i = 0; i < 72; i += 3) {
            vertices[i] = defaultVertices[i] + position.x;
            vertices[i + 1] = defaultVertices[i + 1] + position.y;
            vertices[i + 2] = defaultVertices[i + 2] + position.z;
        }
        if (skyboxMesh != null)
            skyboxMesh.dispose();
        skyboxMesh = new Mesh(vertices, texCoords, indices, skyTexture);
    }

    public void render() {
        skyboxMesh.render();
    }

    public void dispose() {
        skyTexture.dispose();
        skyboxMesh.dispose();
    }

    private static final float defaultVertices[] = {
            //Vertices according to faces
            -155.0f,155.0f,-155.0f,
            -155.0f,-155.0f,-155.0f,
            155.0f,-155.0f,-155.0f,
            155.0f,155.0f,-155.0f,

            -155.0f,155.0f,155.0f,
            -155.0f,-155.0f,155.0f,
            155.0f,-155.0f,155.0f,
            155.0f,155.0f,155.0f,

            155.0f,155.0f,-155.0f,
            155.0f,-155.0f,-155.0f,
            155.0f,-155.0f,155.0f,
            155.0f,155.0f,155.0f,

            -155.0f,155.0f,-155.0f,
            -155.0f,-155.0f,-155.0f,
            -155.0f,-155.0f,155.0f,
            -155.0f,155.0f,155.0f,

            -155.0f,155.0f,155.0f,
            -155.0f,155.0f,-155.0f,
            155.0f,155.0f,-155.0f,
            155.0f,155.0f,155.0f,

            -155.0f,-155.0f,155.0f,
            -155.0f,-155.0f,-155.0f,
            155.0f,-155.0f,-155.0f,
            155.0f,-155.0f,155.0f
    };

    private static final int[] indices = new int[]{
            0,1,3,
            3,1,2,
            4,5,7,
            7,5,6,
            8,9,11,
            11,9,10,
            12,13,15,
            15,13,14,
            16,17,19,
            19,17,18,
            20,21,23,
            23,21,22};


    private static final float[] texCoords = new float[]{
            0.25f, 0.25f,
            0.25f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.25f,

            1.0f, 0.25f,
            1.0f, 0.5f,
            0.75f, 0.5f,
            0.75f, 0.25f,

            0.5f, 0.25f,
            0.5f, 0.5f,
            0.75f, 0.5f,
            0.75f, 0.25f,

            0.25f, 0.25f,
            0.25f, 0.5f,
            0.0f, 0.5f,
            0.0f, 0.25f,

            0.25f, 0.0f,
            0.25f, 0.25f,
            0.5f, 0.25f,
            0.5f, 0.0f,

            0.25f, 0.75f,
            0.25f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.75f,

    };
}


