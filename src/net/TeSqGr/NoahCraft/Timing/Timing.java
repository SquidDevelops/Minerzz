package net.TeSqGr.NoahCraft.Timing;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timing {

    private int fps, fpsCount, ups, upsCount;

    private double lastTime, timeAccumulator;

    public void init(){
        lastTime = getTime();
    }

    public double getTime(){
        return glfwGetTime();
    }

    public double delta(){
        double time = getTime();
        double delta = time - lastTime;
        lastTime = time;
        timeAccumulator += delta;
        return delta;
    }

    public void updateFPS(){
        fpsCount++;
    }

    public void updateUPS(){
        upsCount++;
    }

    public void update(){
        if(timeAccumulator > 1.0){
            fps = fpsCount;
            fpsCount = 0;

            ups = upsCount;
            upsCount = 0;

            timeAccumulator -= 1.0;
        }
    }

    public int getFPS(){
        return fps;
    }

    public int getUPS(){
        return ups;
    }

}
