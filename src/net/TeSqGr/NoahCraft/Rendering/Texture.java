package net.TeSqGr.NoahCraft.Rendering;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private int tID;
    private static int tempSize;
    public int size;

    public Texture(String file) throws Exception{
        this(init(file));
    }

    public Texture(int tID){
        this.tID = tID;
        System.out.println("tID: " + tID);
        size = tempSize;
    }

    private static int init(String file) throws Exception{
            PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(file));

            tempSize = decoder.getWidth();
            System.out.println("Tsize: "+tempSize);


            ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();

            int _tID = glGenTextures();
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, _tID);

            //System.out.println(_tID);

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);


            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

            glGenerateMipmap(GL_TEXTURE_2D);
            return _tID;
    }


    public int getID(){
        return tID;
    }

    public void dispose(){
        glDeleteTextures(tID);
    }
}
