package net.TeSqGr.NoahCraft.Rendering;

public class RenderChunk {

    private float[] chunkV = new float[16*16*256*18*6];
    private int[] chunkI = new int[16*16*256*6*2*4];
    private int vertexCount = 0, indexCount = 0;


    RenderChunk(int[] chunk, int chunkX, int chunkZ){
        update(chunk, chunkX, chunkZ);
    }

    public void update(int[] chunk, int chunkX, int chunkZ){
        int offsetX = chunkX*16, offsetZ = chunkZ*16;
        int vSize = 0, iSize = 0, counter = 0;

        for (int y = 0; y < 256; y++){
            for (int z = 0; z < 16; z++){
                for (int x = 0; x < 16; x++){
                    if (x > 0){
                        if (chunk[(y*16*16)+(z*16)+(x-1)] == 0){
                            //make left YZ face
                            for(int i=0; i<6; i++){
                                chunkI[iSize++] = i + vSize;
                            }
                            for (int i = 0; i < 6; i++){
                                chunkV[vSize++] = YZ[i*3] + 0.5f + x + offsetX;
                                chunkV[vSize++] = YZ[i*3+1] + y;
                                chunkV[vSize++] = YZ[i*3+2] + z + offsetZ;
                            }
                        }
                    } else {
                        for(int i=0; i<6; i++){
                            chunkI[iSize++] = i + vSize;
                        }
                        for (int i = 0; i < 6; i++) {
                            chunkV[vSize++] = YZ[i * 3] + 0.5f + x + offsetX;
                            chunkV[vSize++] = YZ[i * 3 + 1] + y;
                            chunkV[vSize++] = YZ[i * 3 + 2] + z + offsetZ;
                        }
                        counter++;
                    }
                    if (x < 15){
                        if (chunk[(y*16*16)+(z*16)+(x+1)] == 0){
                            //make right YZ face
                            for(int i=0; i<6; i++){
                                chunkI[iSize++] = i + vSize;
                            }
                            for (int i = 0; i < 6; i++){
                                chunkV[vSize++] = YZ[i*3] - 0.5f + x + offsetX;
                                chunkV[vSize++] = YZ[i*3+1] + y;
                                chunkV[vSize++] = YZ[i*3+2] + z + offsetZ;
                            }
                        }
                    } else {
                        for(int i=0; i<6; i++){
                            chunkI[iSize++] = i + vSize;
                        }
                        for (int i = 0; i < 6; i++){
                            chunkV[vSize++] = YZ[i*3] - 0.5f + x + offsetX;
                            chunkV[vSize++] = YZ[i*3+1] + y;
                            chunkV[vSize++] = YZ[i*3+2] + z + offsetZ;
                        }
                        counter++;
                    }
                    if (z > 0){
                        if (chunk[(y*16*16)+(z*(16-1))+x] == 0){
                            //make front XY face
                            for(int i=0; i<6; i++){
                                chunkI[iSize++] = i + vSize;
                            }
                            for (int i = 0; i < 6; i++){
                                chunkV[vSize++] = XY[i*3] + x + offsetX;
                                chunkV[vSize++] = XY[i*3+1] + y;
                                chunkV[vSize++] = XY[i*3+2] + 0.5f + z + offsetZ;
                            }
                        }
                    } else {
                        for(int i=0; i<6; i++){
                            chunkI[iSize++] = i + vSize;
                        }
                        for (int i = 0; i < 6; i++){
                            chunkV[vSize++] = XY[i*3] + x + offsetX;
                            chunkV[vSize++] = XY[i*3+1] + y;
                            chunkV[vSize++] = XY[i*3+2] + 0.5f + z + offsetZ;
                        }
                        counter++;
                    }
                    if (z < 15){
                        if (chunk[(y*16*16)+(z*(16+1))+x] == 0){
                            //make back XY face
                            for(int i=0; i<6; i++){
                                chunkI[iSize++] = i + vSize;
                            }
                            for (int i = 0; i < 6; i++){
                                chunkV[vSize++] = XY[i*3] + x + offsetX;
                                chunkV[vSize++] = XY[i*3+1] + y;
                                chunkV[vSize++] = XY[i*3+2] - 0.5f + z + offsetZ;
                            }
                        }
                    } else {
                        for(int i=0; i<6; i++){
                            chunkI[iSize++] = i + vSize;
                        }
                        for (int i = 0; i < 6; i++){
                            chunkV[vSize++] = XY[i*3] + x + offsetX;
                            chunkV[vSize++] = XY[i*3+1] + y;
                            chunkV[vSize++] = XY[i*3+2] - 0.5f + z + offsetZ;
                        }
                        counter++;
                    }
                    if (y > 0){
                        if (chunk[(y*((16*16)-1))+(z*16)+x] == 0){
                            //make bottom XZ face
                            for(int i=0; i<6; i++){
                                chunkI[iSize++] = i + vSize;
                            }
                            for (int i = 0; i < 6; i++){
                                chunkV[vSize++] = XZ[i*3] + x + offsetX;
                                chunkV[vSize++] = XZ[i*3+1] + 0.5f + y;
                                chunkV[vSize++] = XZ[i*3+2] + z + offsetZ;
                            }
                        }
                    } else {
                        for(int i=0; i<6; i++){
                            chunkI[iSize++] = i + vSize;
                        }
                        for (int i = 0; i < 6; i++){
                            chunkV[vSize++] = XZ[i*3] + x + offsetX;
                            chunkV[vSize++] = XZ[i*3+1] + 0.5f + y;
                            chunkV[vSize++] = XZ[i*3+2] + z + offsetZ;
                        }
                        counter++;
                    }
                    if (y < 255){
                        if (chunk[(y*((16*16)+1))+(z*16)+x] == 0){
                            //make top XZ face
                            for(int i=0; i<6; i++){
                                chunkI[iSize++] = i + vSize;
                            }
                            for (int i = 0; i < 6; i++){
                                chunkV[vSize++] = XZ[i*3] + x + offsetX;
                                chunkV[vSize++] = XZ[i*3+1] - 0.5f + y;
                                chunkV[vSize++] = XZ[i*3+2] + z + offsetZ;
                            }
                        }
                    } else {
                        for(int i=0; i<6; i++){
                            chunkI[iSize++] = i + vSize;
                        }
                        for (int i = 0; i < 6; i++){
                            chunkV[vSize++] = XZ[i*3] + x + offsetX;
                            chunkV[vSize++] = XZ[i*3+1] - 0.5f + y;
                            chunkV[vSize++] = XZ[i*3+2] + z + offsetZ;
                        }
                        counter++;
                    }
                }
            }
        }
        System.out.println(counter);
        vertexCount = vSize;
        indexCount = iSize;
    }


    public float[] getVertices(){
        float[] fVertices = new float[vertexCount];
        System.arraycopy(chunkV, 0, fVertices, 0, vertexCount);
        return fVertices;
    }

    public int[] getIndices(){
        int[] fIndices = new int[indexCount];
        System.arraycopy(chunkI, 0, fIndices, 0, indexCount);
        return fIndices;
    }

    public float[] getTexCoords(){
        float[] fTexCoords = new float[indexCount*8];
        for(int i = 0; i < fTexCoords.length/8; i+=8){
            System.arraycopy(texCoords, 0, fTexCoords, 8*i, 8);
        }
        return fTexCoords;
    }

    private static float[] XY =  {
            -0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    private static float[] YZ = {
            0.0f,  0.5f, -0.5f,
            0.0f, -0.5f, -0.5f,
            0.0f,  0.5f, 0.5f,
            0.0f,  0.5f, 0.5f,
            0.0f, -0.5f, -0.5f,
            0.0f, -0.5f, 0.5f
    };

    private static float[] XZ = {
            -0.5f,  0.0f, 0.5f,
            -0.5f, 0.0f, -0.5f,
            0.5f,  0.0f, 0.5f,
            0.5f,  0.0f, 0.5f,
            -0.5f, 0.0f, -0.5f,
            0.5f, 0.0f, -0.5f
    };

    private float[] texCoords = new float[]{
        0.0f, 0.0f,
        0.0f, 0.5f,
        0.5f, 0.5f,
        0.5f, 0.0f
    };

}
