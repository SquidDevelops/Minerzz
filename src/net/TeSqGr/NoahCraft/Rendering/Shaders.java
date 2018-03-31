package net.TeSqGr.NoahCraft.Rendering;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shaders {

    private static int vertShader, fragShader, shaderProg;

    private static final Map<String, Integer> uniforms = new HashMap<>();

    private static final CharSequence vertShaderCS =
            "#version 330\n" +
                    "layout (location=0) in vec3 position;" +
                    "uniform mat4 projection;" +
                    "uniform mat4 transform;" +
                    "void main(){" +
                        "gl_Position = projection * transform * vec4(position, 1.0);" +
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

    public static void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(shaderProg, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public static void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public static void dispose(){
        unbind();
        glDeleteShader(vertShader);
        glDeleteShader(fragShader);
        glDeleteProgram(shaderProg);
    }
}


