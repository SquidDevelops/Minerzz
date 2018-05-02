package net.TeSqGr.NoahCraft.Rendering;
import net.TeSqGr.NoahCraft.World.Chunk;

public class RenderChunk {

    private float[] texCoords;
    private int vertexCount = 0, indexCount = 0, texCoordCount = 0;
    private final int subTextureSize = 32;
    private int textureSize;
    private float texCoordSize;
    private final byte XY_TYPE = 0, XZ_TYPE = 1, YZ_TYPE = 2;
    private int chunkX, chunkZ;

    private Texture texture;
    private Mesh chunkMesh;

    private int[] chunkBlocks;

    private boolean isEmpty = true;



    RenderChunk(int[] chunk, int chunkX, int chunkZ, Texture _texture, boolean update){
        chunkBlocks = chunk;
        texture = _texture;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.textureSize = texture.size;
        texCoordSize = 1.0f/(textureSize/subTextureSize);

        texCoords = new float[]{
                0.0f, texCoordSize,
                0.0f, 0.0f,
                texCoordSize, 0.0f,
                texCoordSize, texCoordSize,
        };
        if (update == true)
        update(chunk, chunkX, chunkZ);

    }

    RenderChunk(int[] chunk, int chunkX, int chunkZ, Texture _texture){
        chunkBlocks = chunk;
        texture = _texture;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.textureSize = texture.size;
        texCoordSize = 1.0f/(textureSize/subTextureSize);

        texCoords = new float[]{
                0.0f, texCoordSize,
                0.0f, 0.0f,
                texCoordSize, 0.0f,
                texCoordSize, texCoordSize,
        };

        update(chunk, chunkX, chunkZ);

    }

    public void update(int[] chunk, int chunkX, int chunkZ){
        if (this.isEmpty == true) {
            int[] chunks = Chunk.readChunkFromFile(chunkX, chunkZ);
            if (chunks != null) {
                chunk = chunks;
            }
        }
        chunkBlocks = chunk;
        float[] chunkV = new float[2359296]; //16*256*16*6*3 ( 16*256*16 max blocks * 6 faces/block * 12 vert coords/face)
        int[] chunkI = new int[1179648]; //16*256*16*6*3 ( 16*256*16 max blocks * 6 faces/block * 6 ind/face)
        float[] chunkT = new float[1572864]; //16*256*16*8*6 ( 16*256*16 max blocks * 6 faces/block * 8 coords/face)

        int offsetX = chunkX*16, offsetZ = chunkZ*16;
        int vSize = 0, iSize = 0, tSize = 0, counter = 0;

        for (int y = 0; y < 256; y++){
            for (int z = 0; z < 16; z++){
                for (int x = 0; x < 16; x++){
                    if(chunk[(y*16*16)+(z*16)+x] > 0) {
                        if (x > 0) {
                            if (chunk[(y * 16 * 16) + (z * 16) + (x - 1)] == 0) {
                                //make left YZ face
                                chunkV = genFace(chunkV, YZ_TYPE, vSize, -0.5f + x + offsetX, y, z + offsetZ);
                                vSize+=12;
                                chunkI = genIndices(chunkI, counter, iSize);
                                counter++;
                                iSize+=6;
                                chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 0);
                                tSize+=8;
                            }
                        } else {
                            chunkV = genFace(chunkV, YZ_TYPE, vSize, -0.5f + x + offsetX, y, z + offsetZ);
                            vSize+=12;
                            chunkI = genIndices(chunkI, counter, iSize);
                            counter++;
                            iSize+=6;
                            chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 0);
                            tSize+=8;
                        }
                        if (x < 15) {
                            if (chunk[(y * 16 * 16) + (z * 16) + (x + 1)] == 0) {
                                //make right YZ face
                                chunkV = genFace(chunkV,  YZ_TYPE, vSize, 0.5f + x + offsetX, y, z + offsetZ);
                                vSize+=12;
                                chunkI = genIndices(chunkI, counter, iSize);
                                counter++;
                                iSize+=6;
                                chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 1);
                                tSize+=8;
                            }
                        } else {
                            chunkV = genFace(chunkV,  YZ_TYPE, vSize, 0.5f + x + offsetX, y, z + offsetZ);
                            vSize+=12;
                            chunkI = genIndices(chunkI, counter, iSize);
                            counter++;
                            iSize+=6;
                            chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 1);
                            tSize+=8;
                        }
                        if (z > 0) {
                            if (chunk[(y * 16 * 16) + ((z - 1) * 16) + x] == 0) {
                                //make front XY face
                                chunkV = genFace(chunkV, XY_TYPE, vSize, x + offsetX, y, -0.5f + z + offsetZ);
                                vSize+=12;
                                chunkI = genIndices(chunkI, counter, iSize);
                                counter++;
                                iSize+=6;
                                chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 2);
                                tSize+=8;
                            }
                        } else {
                            chunkV = genFace(chunkV, XY_TYPE, vSize, x + offsetX, y, -0.5f + z + offsetZ);
                            vSize+=12;
                            chunkI = genIndices(chunkI, counter, iSize);
                            counter++;
                            iSize+=6;
                            chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 2);
                            tSize+=8;
                        }
                        if (z < 15) {
                            if (chunk[(y * 16 * 16) + ((z + 1)* 16) + x] == 0) {
                                chunkV = genFace(chunkV, XY_TYPE, vSize, x + offsetX, y, 0.5f + z + offsetZ);
                                vSize+=12;
                                chunkI = genIndices(chunkI, counter, iSize);
                                counter++;
                                iSize+=6;
                                chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 3);
                                tSize+=8;
                            }
                        } else {
                            chunkV = genFace(chunkV, XY_TYPE, vSize, x + offsetX, y, 0.5f + z + offsetZ);
                            vSize+=12;
                            chunkI = genIndices(chunkI, counter, iSize);
                            counter++;
                            iSize+=6;
                            chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 3);
                            tSize+=8;
                        }
                        if (y > 0) {
                            if (chunk[(y-1)*16*16 + (z * 16) + x] == 0) {
                                //Make bottom XZ face
                                chunkV = genFace(chunkV, XZ_TYPE, vSize, x + offsetX, -0.5f + y, z + offsetZ);
                                vSize += 12;
                                chunkI = genIndices(chunkI, counter, iSize);
                                counter++;
                                iSize+=6;
                                chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 4);
                                tSize+=8;
                            }
                        } else {
                            chunkV = genFace(chunkV, XZ_TYPE, vSize, x + offsetX, -0.5f + y, z + offsetZ);
                            vSize+=12;
                            chunkI = genIndices(chunkI, counter, iSize);
                            counter++;
                            iSize+=6;
                            chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 4);
                            tSize+=8;
                        }
                        if (y < 255) {
                            if (chunk[(y+1)*(16 * 16) + (z * 16) + x] == 0) {
                                //make top XZ face
                                chunkV = genFace(chunkV, XZ_TYPE, vSize, x + offsetX, 0.5f + y, z + offsetZ);
                                vSize+=12;
                                chunkI = genIndices(chunkI, counter, iSize);
                                counter++;
                                iSize+=6;
                                chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 5);
                                tSize+=8;
                            }
                        } else {
                            chunkV = genFace(chunkV, XZ_TYPE, vSize, x + offsetX, 0.5f + y, z + offsetZ);
                            vSize += 12;
                            chunkI = genIndices(chunkI, counter, iSize);
                            counter++;
                            iSize += 6;
                            chunkT = genTexCoords(chunkT, tSize, chunk[(y*16*16)+(z*16)+x], 5);
                            tSize+=8;
                        }
                    }
                }
            }
        }

        texCoordCount = tSize;
        vertexCount = vSize;
        indexCount = iSize;

        if(chunkMesh != null)
            chunkMesh.dispose();
        chunkMesh = new Mesh(getVertices(chunkV), getTexCoords(chunkT), getIndices(chunkI), texture);
        isEmpty = false;
    }

    private float[] genFace(float[] chunkV, byte type, int location, float x, float y, float z){
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
        return chunkV;
    }

    private int[] genIndices(int[] chunkI, int count, int size){
        for(int a : baseIndices){
            chunkI[size++] = a+(count*3)+count;
        }
        return chunkI;
    }

    private float[] genTexCoords(float[] chunkT, int size, int blockID, int face){
        float x = (((blockID-1)*6+face)%(textureSize/subTextureSize))*texCoordSize,
            y = (((blockID-1)*6+face)/(textureSize/subTextureSize))*texCoordSize;
        for(int i = 0; i < 4; i++){
            chunkT[2*i+size] = texCoords[2*i] + x;
            chunkT[2*i+size+1] = texCoords[2*i+1] + y;
        }
        return chunkT;
    }


    public float[] getVertices(float[] chunkV){
        float[] fVertices = new float[vertexCount];
        System.arraycopy(chunkV, 0, fVertices, 0, vertexCount);
        return fVertices;
    }

    public int[] getIndices(int[] chunkI){
        int[] fIndices = new int[indexCount];
        System.arraycopy(chunkI, 0, fIndices, 0, indexCount);
        return fIndices;
    }

    public float[] getTexCoords(float[] chunkT){
        float[] fTexCoords = new float[texCoordCount];
        System.arraycopy(chunkT, 0, fTexCoords, 0, texCoordCount);
        return fTexCoords;
    }

    public boolean getEmpty() {
        return this.isEmpty;
    }

    public Mesh getChunkMesh(){
        return chunkMesh;
    }

    public void dispose(){

        texture.dispose();
        if (this.isEmpty == false)
        chunkMesh.dispose();
    }

    public int getChunkX(){return chunkX;}

    public int getChunkZ(){return chunkZ;}

    public int[] getChunkBlocks() {
        return this.chunkBlocks;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }


    private static final int[] baseIndices = {0, 1, 3, 3, 1, 2};


    private static final float[] XY =  {
            -0.5f,  -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.5f,  -0.5f, 0.0f
    };

    private static final float[] YZ = {
            0.0f,  -0.5f, -0.5f,
            0.0f,  0.5f, -0.5f,
            0.0f,  0.5f, 0.5f,
            0.0f,  -0.5f, 0.5f
    };

    private static final float[] XZ = {
            -0.5f,  0.0f, 0.5f,
            -0.5f, 0.0f, -0.5f,
            0.5f,  0.0f, -0.5f,
            0.5f,  0.0f, 0.5f
    };
}
