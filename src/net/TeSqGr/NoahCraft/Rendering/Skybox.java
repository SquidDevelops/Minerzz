package net.TeSqGr.NoahCraft.Rendering;

public class Skybox extends Mesh {

    static Texture skyTexture = new Texture("skybox.png");

    public Skybox(){
        super(vertices, texCoords, indices, skyTexture);
    }

    private static float[] vertices = {
            -100.0f,  100.0f,  100.0f,
            -100.0f, -100.0f,  100.0f,
            100.0f, -100.0f,  100.0f,
            100.0f,  100.0f,  100.0f,
            -100.0f,  100.0f, -100.0f,
            100.0f,  100.0f, -100.0f,
            -100.0f, -100.0f, -100.0f,
            100.0f, -100.0f, -100.0f,
    };

    private static int[] indices = {
            0, 1, 3, 3, 1, 2,
            4, 0, 3, 5, 4, 3,
            3, 2, 7, 5, 3, 7,
            6, 1, 0, 6, 0, 4,
            2, 1, 6, 2, 6, 7,
            7, 6, 4, 7, 4, 5,
    };

    private static float[] texCoords = {
            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,

    };
}
