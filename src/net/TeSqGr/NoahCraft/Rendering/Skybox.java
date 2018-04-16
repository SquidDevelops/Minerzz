package net.TeSqGr.NoahCraft.Rendering;

public class Skybox extends Mesh {

    static Texture skyTexture = new Texture("skybox.png");

    public Skybox(){
        super(vertices, texCoords, indices, skyTexture);
    }

    public void update(float x){

    }

    private static float[] vertices = new float[] {
            // V0
            -100.0f, 100.0f, 100.0f,
            // V1
            -100.0f, -100.0f, 100.0f,
            // V2
            100.0f, -100.0f, 100.0f,
            // V3
            100.0f, 100.0f, 100.0f,
            // V4
            -100.0f, 100.0f, -100.0f,
            // V5
            100.0f, 100.0f, -100.0f,
            // V6
            -100.0f, -100.0f, -100.0f,
            // V7
            100.0f, -100.0f, -100.0f,

            // For text coords in top face
            // V8: V4 repeated
            -100.0f, 100.0f, -100.0f,
            // V9: V5 repeated
            100.0f, 100.0f, -100.0f,
            // V10: V0 repeated
            -100.0f, 100.0f, 100.0f,
            // V11: V3 repeated
            100.0f, 100.0f, 100.0f,

            // For text coords in right face
            // V12: V3 repeated
            100.0f, 100.0f, 100.0f,
            // V13: V2 repeated
            100.0f, -100.0f, 100.0f,

            // For text coords in left face
            // V14: V0 repeated
            -100.0f, 100.0f, 100.0f,
            // V15: V1 repeated
            -100.0f, -100.0f, 100.0f,

            // For text coords in bottom face
            // V16: V6 repeated
            -100.0f, -100.0f, -100.0f,
            // V17: V7 repeated
            100.0f, -100.0f, -100.0f,
            // V18: V1 repeated
            -100.0f, -100.0f, 100.0f,
            // V19: V2 repeated
            100.0f, -100.0f, 100.0f,
    };

    private static float[] texCoords = new float[]{
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

    
    private static  int[] indices = new int[]{
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
