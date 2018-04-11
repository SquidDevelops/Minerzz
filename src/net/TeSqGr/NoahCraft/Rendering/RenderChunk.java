package net.TeSqGr.NoahCraft.Rendering;

public class RenderChunk {

    private float[] chunkV = new float[16*16*256*18*6];
    private int[] chunkI = new int[16*16*256*6*6];
    private int vertexCount = 0, indexCount = 0;
    private final int subTextureSize = 32;
    private float texCoordSize;
    private final byte XY_TYPE = 0, XZ_TYPE = 1, YZ_TYPE = 2;


    RenderChunk(int[] chunk, int chunkX, int chunkZ, int textureSize){
        texCoordSize = 1.0f/(textureSize/subTextureSize);
        update(chunk, chunkX, chunkZ);
    }

    public void update(int[] chunk, int chunkX, int chunkZ){
        int offsetX = chunkX*16, offsetZ = chunkZ*16;
        int vSize = 0, iSize = 0, counter = 0;

        for (int y = 0; y < 256; y++){
            for (int z = 0; z < 16; z++){
                for (int x = 0; x < 16; x++){
                    if(chunk[(y*16*16)+(z*16)+x] > 0) {
                        if (x > 0) {
                            if (chunk[(y * 16 * 16) + (z * 16) + (x - 1)] == 0) {
                                //make left YZ face
                                genFace( YZ_TYPE, vSize, -0.5f + x + offsetX, y, z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                            }
                        } else {
                            genFace( YZ_TYPE, vSize, -0.5f + x + offsetX, y, z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                        }
                        if (x < 15) {
                            if (chunk[(y * 16 * 16) + (z * 16) + (x + 1)] == 0) {
                                //make right YZ face
                                genFace( YZ_TYPE, vSize, 0.5f + x + offsetX, y, z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                            }
                        } else {
                            genFace( YZ_TYPE, vSize, 0.5f + x + offsetX, y, z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                        }
                        if (z > 0) {
                            if (chunk[(y * 16 * 16) + ((z - 1) * 16) + x] == 0) {
                                //make front XY face
                                genFace( XY_TYPE, vSize, x + offsetX, y, -0.5f + z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                            }
                        } else {
                            genFace( XY_TYPE, vSize, x + offsetX, y, -0.5f + z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                        }
                        if (z < 15) {
                            if (chunk[(y * 16 * 16) + ((z + 1)* 16) + x] == 0) {
                                genFace( XY_TYPE, vSize, x + offsetX, y, 0.5f + z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                            }
                        } else {
                            genFace( XY_TYPE, vSize, x + offsetX, y, 0.5f + z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                        }
                        if (y > 0) {
                            if (chunk[(y-1)*16*16 + (z * 16) + x] == 0) {
                                //Make bottom XZ face
                                genFace(XZ_TYPE, vSize, x + offsetX, -0.5f + y, z + offsetZ);
                                vSize += 12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                            }
                        } else {
                            genFace(XZ_TYPE, vSize, x + offsetX, -0.5f + y, z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                        }
                        if (y < 255) {
                            if (chunk[(y+1)*(16 * 16) + (z * 16) + x] == 0) {
                                //make top XZ face
                                genFace(XZ_TYPE, vSize, x + offsetX, 0.5f + y, z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                            }
                        } else {
                            genFace(XZ_TYPE, vSize, x + offsetX, 0.5f + y, z + offsetZ);
                            vSize += 12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize += 6;
                        }
                    }
                }
            }
        }

        vertexCount = vSize;
        indexCount = iSize;
    }

    private void genFace(byte type, int location, float x, float y, float z){
        switch(type){
            case XY_TYPE:
                for(byte i = 0; i < 4; i++) {
                    chunkV[location++] = XY2[i*3] + x;
                    chunkV[location++] = XY2[i*3+1] + y;
                    chunkV[location++] = XY2[i*3+2] + z;
                }
                break;
            case XZ_TYPE:
                for(byte i = 0; i < 4; i++) {
                    chunkV[location++] = XZ2[i*3] + x;
                    chunkV[location++] = XZ2[i*3+1] + y;
                    chunkV[location++] = XZ2[i*3+2] + z;
                }
                break;
            case YZ_TYPE:
                for(byte i = 0; i < 4; i++) {
                    chunkV[location++] = YZ2[i*3] + x;
                    chunkV[location++] = YZ2[i*3+1] + y;
                    chunkV[location++] = YZ2[i*3+2] + z;
                }
                break;
        }
    }

    private void genIndices(int count, int size){
        for(int a : baseIndices){
            chunkI[size++] = a+(count*3)+count;
        }
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
        float[] fTexCoords = new float[indexCount*2];
        for(int i = 0; i < fTexCoords.length/8; i++){
            System.arraycopy(texCoords, 0, fTexCoords, 8*i, 8);
        }
        return fTexCoords;
    }

    private static int[] baseIndices = {0, 1, 3, 3, 1, 2};


    private static float[] XY2 =  {
            -0.5f,  -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.5f,  -0.5f, 0.0f
    };

    private static float[] YZ2 = {
            0.0f,  -0.5f, -0.5f,
            0.0f,  0.5f, -0.5f,
            0.0f,  0.5f, 0.5f,
            0.0f,  -0.5f, 0.5f
    };

    private static float[] XZ2 = {
            -0.5f,  0.0f, 0.5f,
            -0.5f, 0.0f, -0.5f,
            0.5f,  0.0f, -0.5f,
            0.5f,  0.0f, 0.5f
    };

    private float[] texCoords = {
        0.0f, 0.0f,
        0.0f, 0.5f,
        0.5f, 0.5f,
        0.5f, 0.0f
    };

}
