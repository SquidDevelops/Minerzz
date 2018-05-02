package net.TeSqGr.NoahCraft;

import net.TeSqGr.NoahCraft.Entity.Camera;
import net.TeSqGr.NoahCraft.Entity.Player.Player;
import net.TeSqGr.NoahCraft.Input.Input;
import net.TeSqGr.NoahCraft.Input.MouseInput;
import net.TeSqGr.NoahCraft.Rendering.Renderer;
import net.TeSqGr.NoahCraft.Timing.Timing;
import net.TeSqGr.NoahCraft.Window.Window;
import net.TeSqGr.NoahCraft.World.Coordinate;
import org.lwjgl.glfw.GLFWErrorCallback;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;

public class NoahCraft {

    public Logger noahLogger = Logger.getLogger("Noah L");

    public GLFWErrorCallback errorCallback;

    public Window window;
    public Renderer renderer;
    public Timing timer;
    public Input input;
    public MouseInput mouseInput;

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
        } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }


    }

    public void dispose() {
        window.visible(false);
        renderer.dispose();
        window.dispose();
        //System.exit(0);
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
            //System.out.println("FPS:" + timer.getFPS());
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



    public void music() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        InputStream audioSrc = getClass().getResourceAsStream("Audio/strad.wav");
        InputStream bufferedIn = new BufferedInputStream(audioSrc);
        AudioInputStream asl = AudioSystem.getAudioInputStream(bufferedIn);
        AudioFormat af = asl.getFormat();
        Clip clip1 = AudioSystem.getClip();
        DataLine.Info info = new DataLine.Info(Clip.class, af);

        Line line1 = AudioSystem.getLine(info);

        if ( ! line1.isOpen() ) {
            clip1.open(asl);
            clip1.loop(Clip.LOOP_CONTINUOUSLY);
            clip1.start();
        }
    }

}
