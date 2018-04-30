package net.TeSqGr.NoahCraft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import net.TeSqGr.NoahCraft.Entity.Camera;
import net.TeSqGr.NoahCraft.Entity.Player.Player;
import net.TeSqGr.NoahCraft.Input.Input;
import net.TeSqGr.NoahCraft.Input.KeyboardHandler;
import net.TeSqGr.NoahCraft.Input.MouseInput;
import net.TeSqGr.NoahCraft.Rendering.Renderer;
import net.TeSqGr.NoahCraft.Rendering.Skybox;
import net.TeSqGr.NoahCraft.Timing.Timing;
import net.TeSqGr.NoahCraft.Window.Window;

import static net.TeSqGr.NoahCraft.Audio.Music.music;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.system.MemoryUtil.*;

import net.TeSqGr.NoahCraft.World.Chunk;
import net.TeSqGr.NoahCraft.World.Coordinate;
import net.TeSqGr.NoahCraft.World.Noise;
import org.lwjgl.glfw.GLFWErrorCallback;
import net.TeSqGr.NoahCraft.Audio.Music;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class NoahCraft {

    public Logger noahLogger = Logger.getLogger("Noah L");

    public GLFWErrorCallback errorCallback;

    public Window window;
    public Renderer renderer;
    public Timing timer;
    public Input input;
    public MouseInput mouseInput;
    public AudioPlayer MGP = AudioPlayer.player;
    public ContinuousAudioDataStream loop = null;
    public AudioStream BGM;

    public Camera getCamera() {
        return camera;
    }

    private Camera camera;
    public Player testPlayer = new Player(1, new Coordinate(0, 0, 0));


    //DELET THIS
    //WorldFiller w = new WorldFiller();


    public static void main(String[] args) {
        new NoahCraft();
        instance.noahLogger.exiting("NoahCraft", "Main");
    }


    public static NoahCraft instance;

    public NoahCraft() {
        if (instance == null) {
            instance = this;
        }
        init();
        gameLoop();
        dispose();
        AudioPlayer.player.stop(BGM);
    }

    private void init() {
        glfwSetErrorCallback(errorCallback = createPrint(System.err));

        glfwInit();
        window = new Window(640, 480, "NoahCraft");
        renderer = new Renderer(window);
        timer = new Timing();
        input = new Input();
        mouseInput = new MouseInput();
        mouseInput.init(window);
        camera = new Camera();
        window.visible(true);
        try {
            music();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void dispose() {
        window.visible(false);
        renderer.dispose();
        window.dispose();
    }

    private void gameLoop() {
        double delta, accumulator = 0.0, interval = 1.0 / 20.0, alpha;
        timer.init();
        while (!window.shouldClose()) {
            timer.update();
            delta = timer.delta();
            accumulator += delta;
            input();
            while (accumulator > interval) {
                update(delta);
                accumulator -= interval;
            }
            render();
            System.out.println("FPS:" + timer.getFPS());
        }
    }

    public void input() {
        glfwPollEvents();
        mouseInput.input(window);
        input.input(window);

    }

    public void update(double delta) {
        timer.updateUPS();
        input.update(mouseInput);
    }

    public void render() {
        renderer.render(window, camera);
        window.render();
        testPlayer.update();
        timer.updateFPS();
    }



    public void music() throws IOException{
        AudioData MD;

            InputStream test = new FileInputStream("src/net/TeSqGr/NoahCraft/Audio/minecraft.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
            MD = BGM.getData();
            loop = new ContinuousAudioDataStream(MD);

        //MGP.start(loop);
    }

}
