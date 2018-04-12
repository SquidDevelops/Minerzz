package net.TeSqGr.NoahCraft.Rendering;

public class RenderChunk {

    private float[] chunkV = new float[4718592]; //16*256*16*6*3 ( 16*256*16 max blocks * 6 faces/block * 12 vert coords/face)
    private int[] chunkI = new int[2359296]; //16*256*16*6*3 ( 16*256*16 max blocks * 6 faces/block * 6 ind/face)
    private float[] chunkT = new float[3145728]; //16*256*16*8*6 ( 16*256*16 max blocks * 6 faces/block * 8 coords/face)
    private float[] texCoords;
    private int vertexCount = 0, indexCount = 0, texCoordCount = 0;
    private final int subTextureSize = 32;
    private int textureSize;
    private float texCoordSize;
    private final byte XY_TYPE = 0, XZ_TYPE = 1, YZ_TYPE = 2;


    RenderChunk(int[] chunk, int chunkX, int chunkZ, int textureSize){
        this.textureSize = textureSize;
        texCoordSize = 1.0f/(textureSize/subTextureSize);

        texCoords = new float[]{
            0.0f, 0.0f,
            0.0f, texCoordSize,
            texCoordSize, texCoordSize,
            texCoordSize, 0.0f
        };

        update(chunk, chunkX, chunkZ);

    }

    public void update(int[] chunk, int chunkX, int chunkZ){
        int offsetX = chunkX*16, offsetZ = chunkZ*16;
        int vSize = 0, iSize = 0, tSize = 0, counter = 0;

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
                                genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 0);
                                tSize+=8;
                            }
                        } else {
                            genFace( YZ_TYPE, vSize, -0.5f + x + offsetX, y, z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                            genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 0);
                            tSize+=8;
                        }
                        if (x < 15) {
                            if (chunk[(y * 16 * 16) + (z * 16) + (x + 1)] == 0) {
                                //make right YZ face
                                genFace( YZ_TYPE, vSize, 0.5f + x + offsetX, y, z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                                genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 1);
                                tSize+=8;
                            }
                        } else {
                            genFace( YZ_TYPE, vSize, 0.5f + x + offsetX, y, z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                            genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 1);
                            tSize+=8;
                        }
                        if (z > 0) {
                            if (chunk[(y * 16 * 16) + ((z - 1) * 16) + x] == 0) {
                                //make front XY face
                                genFace( XY_TYPE, vSize, x + offsetX, y, -0.5f + z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                                genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 2);
                                tSize+=8;
                            }
                        } else {
                            genFace( XY_TYPE, vSize, x + offsetX, y, -0.5f + z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                            genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 2);
                            tSize+=8;
                        }
                        if (z < 15) {
                            if (chunk[(y * 16 * 16) + ((z + 1)* 16) + x] == 0) {
                                genFace( XY_TYPE, vSize, x + offsetX, y, 0.5f + z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                                genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 3);
                                tSize+=8;
                            }
                        } else {
                            genFace( XY_TYPE, vSize, x + offsetX, y, 0.5f + z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                            genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 3);
                            tSize+=8;
                        }
                        if (y > 0) {
                            if (chunk[(y-1)*16*16 + (z * 16) + x] == 0) {
                                //Make bottom XZ face
                                genFace(XZ_TYPE, vSize, x + offsetX, -0.5f + y, z + offsetZ);
                                vSize += 12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                                genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 4);
                                tSize+=8;
                            }
                        } else {
                            genFace(XZ_TYPE, vSize, x + offsetX, -0.5f + y, z + offsetZ);
                            vSize+=12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize+=6;
                            genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 4);
                            tSize+=8;
                        }
                        if (y < 255) {
                            if (chunk[(y+1)*(16 * 16) + (z * 16) + x] == 0) {
                                //make top XZ face
                                genFace(XZ_TYPE, vSize, x + offsetX, 0.5f + y, z + offsetZ);
                                vSize+=12;
                                genIndices(counter, iSize);
                                counter++;
                                iSize+=6;
                                genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 5);
                                tSize+=8;
                            }
                        } else {
                            genFace(XZ_TYPE, vSize, x + offsetX, 0.5f + y, z + offsetZ);
                            vSize += 12;
                            genIndices(counter, iSize);
                            counter++;
                            iSize += 6;
                            genTexCoords(tSize, chunk[(y*16*16)+(z*16)+x], 5);
                            tSize+=8;
                        }
                    }
                }
            }
        }

        texCoordCount = tSize;
        vertexCount = vSize;
        indexCount = iSize;
    }

    private void genFace(byte type, int location, float x, float y, float z){
        switch(type){
            case XY_TYPE:
                for(byte i = 0; i < 4; i++) {
                    chunkV[location++] = XY[i*3] + x;
                    chunkV[location++] = XY[i*3+1] + y;
                    chunkV[location++] = z;//XY[i*3+2] + z;
                }
                break;
            case XZ_TYPE:
                for(byte i = 0; i < 4; i++) {
                    chunkV[location++] = XZ[i*3] + x;
                    chunkV[location++] = y;//XZ[i*3+1] + y;
                    chunkV[location++] = XZ[i*3+2] + z;
                }
                break;
            case YZ_TYPE:
                for(byte i = 0; i < 4; i++) {
                    chunkV[location++] = x;//YZ[i*3] + x;
                    chunkV[location++] = YZ[i*3+1] + y;
                    chunkV[location++] = YZ[i*3+2] + z;
                }
                break;
        }
    }

    private void genIndices(int count, int size){
        for(int a : baseIndices){
            chunkI[size++] = a+(count*3)+count;
        }
    }

    private void genTexCoords(int size, int blockID, int face){
        float x = (((blockID-1)*6+face)%(textureSize/subTextureSize))*texCoordSize,
            y = (((blockID-1)*6+face)/(textureSize/subTextureSize))*texCoordSize;
        for(int i = 0; i < 4; i++){
            chunkT[2*i+size] = texCoords[2*i] + x;
            chunkT[2*i+size+1] = texCoords[2*i+1] + y;
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
        float[] fTexCoords = new float[texCoordCount];
        System.arraycopy(chunkT, 0, fTexCoords, 0, texCoordCount);
        return fTexCoords;
    }

    private static int[] baseIndices = {0, 1, 3, 3, 1, 2};


    private static float[] XY =  {
            -0.5f,  -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.5f,  -0.5f, 0.0f
    };

    private static float[] YZ = {
            0.0f,  -0.5f, -0.5f,
            0.0f,  0.5f, -0.5f,
            0.0f,  0.5f, 0.5f,
            0.0f,  -0.5f, 0.5f
    };

    private static float[] XZ = {
            -0.5f,  0.0f, 0.5f,
            -0.5f, 0.0f, -0.5f,
            0.5f,  0.0f, -0.5f,
            0.5f,  0.0f, 0.5f
    };
}
