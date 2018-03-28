package net.TeSqGr.NoahCraft.Rendering;

public class Shaders {

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
    public static final CharSequence vertShader =
        "#version 330\n" +
        "layout (location=0) in vec3 position;" +
        "void main(){" +
            "gl_Position = vec4(position, 1.0);" +
        "}";

    public static final CharSequence fragShader =
        "#version 330\n" +
        "out vec4 fragC;" +
        "void main(){" +
            "fragC = vec4(0.0, 0.5, 0.5, 1.0);" +
        "}";


}
