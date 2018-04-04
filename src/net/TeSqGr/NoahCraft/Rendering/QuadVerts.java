package net.TeSqGr.NoahCraft.Rendering;

public class QuadVerts {

    public static float[] XY =  {
            -0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    public static float[] YZ = {
            0.0f,  0.5f, -0.5f,
            0.0f, -0.5f, -0.5f,
            0.0f,  0.5f, 0.5f,
            0.0f,  0.5f, 0.5f,
            0.0f, -0.5f, -0.5f,
            0.0f, -0.5f, 0.5f
    };

    public static float[] XZ = {
            -0.5f,  0.0f, 0.5f,
            -0.5f, 0.0f, -0.5f,
            0.5f,  0.0f, 0.5f,
            0.5f,  0.0f, 0.5f,
            -0.5f, 0.0f, -0.5f,
            0.5f, 0.0f, -0.5f
    };

    public static float[] genXYFace(float z){
        float[] XYcopy = XY;
        XYcopy[2] = z;
        XYcopy[5] = z;
        XYcopy[8] = z;
        XYcopy[11] = z;
        XYcopy[14] = z;
        XYcopy[17] = z;
        return XYcopy;
    }

    public static float[] genYZFace(float x){
        float[] YZcopy = YZ;
        YZcopy[0] = x;
        YZcopy[3] = x;
        YZcopy[6] = x;
        YZcopy[9] = x;
        YZcopy[12] = x;
        YZcopy[15] = x;
        return YZcopy;
    }

    public static float[] genXZFace(float y){
        float[] XZcopy = XZ;
        XZcopy[1] = y;
        XZcopy[4] = y;
        XZcopy[7] = y;
        XZcopy[10] = y;
        XZcopy[13] = y;
        XZcopy[16] = y;
        return XZcopy;
    }



}
