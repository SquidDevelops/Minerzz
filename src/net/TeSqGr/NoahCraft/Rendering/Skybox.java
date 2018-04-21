package net.TeSqGr.NoahCraft.Rendering;

import org.joml.Vector3f;

public class Skybox {

    Mesh skyboxMesh;

    static Texture skyTexture = new Texture("skybox.png");

    public Skybox(Vector3f position){
        update(position);
    }

    public void update(Vector3f position){
        float[] vertices = new float[60];
        for(int i = 0; i<60; i+=3){
            vertices[i] = defaultVertices[i] + position.x;
            vertices[i+1] = defaultVertices[i+1];
            vertices[i+2] = defaultVertices[i+2] + position.z;
        }
        skyboxMesh = new Mesh(vertices, texCoords, indices, skyTexture);
    }

    public void render(){
        skyboxMesh.render();
    }

    public void dispose(){
        skyTexture.dispose();
        skyboxMesh.dispose();
    }

    private static float[] defaultVertices = new float[] {
            // V0
            -155.0f, 300.0f, 155.0f,
            // V1
            -155.0f, -10.0f, 155.0f,
            // V2
            155.0f, -10.0f, 155.0f,
            // V3
            155.0f, 300.0f, 155.0f,
            // V4
            -155.0f, 300.0f, -155.0f,
            // V5
            155.0f, 300.0f, -155.0f,
            // V6
            -155.0f, -10.0f, -155.0f,
            // V7
            155.0f, -10.0f, -155.0f,

            // For text coords in top face
            // V8: V4 repeated
            -155.0f, 300.0f, -155.0f,
            // V9: V5 repeated
            155.0f, 300.0f, -155.0f,
            // V10: V0 repeated
            -155.0f, 300.0f, 155.0f,
            // V11: V3 repeated
            155.0f, 300.0f, 155.0f,

            // For text coords in right face
            // V12: V3 repeated
            155.0f, 300.0f, 155.0f,
            // V13: V2 repeated
            155.0f, -10.0f, 155.0f,

            // For text coords in left face
            // V14: V0 repeated
            -155.0f, 300.0f, 155.0f,
            // V15: V1 repeated
            -155.0f, -10.0f, 155.0f,

            // For text coords in bottom face
            // V16: V6 repeated
            -155.0f, -10.0f, -155.0f,
            // V17: V7 repeated
            155.0f, -10.0f, -155.0f,
            // V18: V1 repeated
            -155.0f, -10.0f, 155.0f,
            // V19: V2 repeated
            155.0f, -10.0f, 155.0f,
    };

    private static final float[] texCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,

            // For text coords in top face
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,

            // For text coords in right face
            0.0f, 0.0f,
            0.0f, 0.5f,

            // For text coords in left face
            0.5f, 0.0f,
            0.5f, 0.5f,

            // For text coords in bottom face
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
    };

    
    private static final int[] indices = new int[]{
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            8, 10, 11, 9, 8, 11,
            // Right face
            12, 13, 7, 5, 12, 7,
            // Left face
            14, 15, 6, 4, 14, 6,
            // Bottom face
            16, 18, 19, 17, 16, 19,
            // Back face
            4, 6, 7, 5, 4, 7,};
}
