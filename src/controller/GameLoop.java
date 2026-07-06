package controller;

import javax.swing.Timer;
import utils.Constants;
import view.Renderer;

/**
 * This is the game loop class that updates the game and repaints the screen on a timer.
 * @author Gurangad Batth
 */
public class GameLoop{
    private static final long STEP_NANOS = 1_000_000_000L / Constants.TARGET_FPS;
    private static final long MAX_ELAPSED_NANOS = STEP_NANOS * 5L;

    private Timer timer;
    private final GameStateManager gsm;
    private final Renderer renderer;
    private long previousTickNanos;
    private long accumulatedNanos;

    /**
     * This is the constructor for the game loop.
     * @param gsm the game state manager that updates the active state
     * @param renderer the renderer that repaints the active screen
     */
    public GameLoop(GameStateManager gsm, Renderer renderer){
        this.gsm = gsm;
        this.renderer = renderer;
    }

    /**
     * This method starts the timer driven game loop.
     */
    public void start(){
        if(timer == null){
            previousTickNanos = System.nanoTime();
            int frameDelayMs = Math.max(1, Math.round(1000f / Constants.TARGET_FPS));
            timer = new Timer(frameDelayMs, event -> onTick());
        }
        timer.start();
    }

    /**
     * This method runs one frame of game logic and rendering.
     */
    private void onTick(){
        long now = System.nanoTime();
        long elapsed = now - previousTickNanos;
        previousTickNanos = now;
        accumulatedNanos += Math.min(elapsed, MAX_ELAPSED_NANOS);

        int updates = 0;
        while(accumulatedNanos >= STEP_NANOS && updates < 5){
            gsm.update();
            accumulatedNanos -= STEP_NANOS;
            updates++;
        }

        if(updates == 0){
            gsm.update();
            accumulatedNanos = 0;
        }

        renderer.repaint();
    }
}
