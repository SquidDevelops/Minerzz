package net.TeSqGr.NoahCraft.Rendering;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shaders {
    private static int vertShader, fragShader, shaderProg;
    /*public static final CharSequence vertShader =
        "#version 150 core\n" +
        "in vec3 position;" +
        "in vec3 color;" +
        "out vec3 vColor;" +
        "uniform mat4 model;" +
        "uniform mat4 view;" +
        "uniform mat4 projection;" +
        "void main(){" +
            "vColor = color;" +
            "mat4 mvp = projection * view * model;" +
            "gl_Position = mvp * vec4(position, 1.0);" +
        "}";

    public static final CharSequence fragShader =
        "#version 150 core\n" +
        "in vec3 vColor;" +
        "out vec4 fragColor;" +
        "void main(){" +
            "fragColor = vec4(vColor, 1.0);" +
        "}";*/
    private static final CharSequence vertShaderCS =
            "#version 330\n" +
                    "layout (location=0) in vec3 position;" +
                    "void main(){" +
                    "gl_Position = vec4(position, 1.0);" +
                    "}";

    private static final CharSequence fragShaderCS =
            "#version 330\n" +
                    "out vec4 fragC;" +
                    "void main(){" +
                    "fragC = vec4(0.0, 0.5, 0.5, 1.0);" +
                    "}";

    public static void compileShaders() {
        vertShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertShader, Shaders.vertShaderCS);
        glCompileShader(vertShader);

        if (glGetShaderi(vertShader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vertShader));
        }

        fragShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShader, Shaders.fragShaderCS);
        glCompileShader(fragShader);

        if (glGetShaderi(fragShader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(fragShader));
        }

        shaderProg = glCreateProgram();
        glAttachShader(shaderProg, vertShader);
        glAttachShader(shaderProg, fragShader);
        glLinkProgram(shaderProg);
        if (glGetProgrami(shaderProg, GL_LINK_STATUS) != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(shaderProg));
        }

        glDetachShader(shaderProg, vertShader);
        glDetachShader(shaderProg, fragShader);

        glValidateProgram(shaderProg);
        if (glGetProgrami(shaderProg, GL_VALIDATE_STATUS) == 0) {
            System.out.println("Warning validating Shader code: " + glGetShaderInfoLog(shaderProg, 1024));
        }

    }

    public static void bind(){
        glUseProgram(shaderProg);
    }

    public static void unbind() {
        glUseProgram(0);
    }

    public static void dispose(){
        unbind();
        glDeleteShader(vertShader);
        glDeleteShader(fragShader);
        glDeleteProgram(shaderProg);
    }
}


