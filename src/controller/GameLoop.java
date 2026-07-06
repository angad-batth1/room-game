package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import view.Renderer;

/**
 * This is the game loop class that updates the game and repaints the screen on a timer.
 * @author Gurangad Batth
 */
public class GameLoop implements ActionListener{

    private Timer timer;
    private GameStateManager gsm;
    private Renderer renderer;

    /**
     * This is the constructor for the game loop.
     * @param gsm the game state manager that updates the active state
     * @param renderer the renderer that repaints the active screen
     */
    public GameLoop(GameStateManager gsm, Renderer renderer){
        this.gsm = gsm;
        this.renderer = renderer;
        timer = new Timer(1000 / 60, this);
    }

    /**
     * This method starts the timer driven game loop.
     */
    public void start(){
        timer.start();
    }

    /**
     * This method runs one frame of game logic and rendering whenever the timer fires.
     * @param e the timer event for the current frame
     */
    @Override
    public void actionPerformed(ActionEvent e){
        gsm.update();
        renderer.repaint();
    }
}
