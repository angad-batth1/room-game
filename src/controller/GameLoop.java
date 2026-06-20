package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import view.Renderer;

/**
 * This is the heartbeat of the entire game, initializes gsm, rendeder, java timer and updates everything 
 * @author Gurangad Batth 
 */
public class GameLoop implements ActionListener{
    // Instances for timer, gsm and renderer
    private Timer timer;
    private GameStateManager gsm;
    private Renderer renderer;

    /**
     * Constructor for the gameloop, caculates delay and then initializes timer
     * @param gsm the game state manager being passed in
     * @param renderer the renderer being bassed in
     */
    public GameLoop(GameStateManager gsm, Renderer renderer){
        this.gsm = gsm;
        this.renderer = renderer;
        int delay = 1000 / 60; // delay in miliseconds for 60 FPS (1000 ms / 60 frames = 16 ms)
        this.timer = new Timer(delay, this); // Initialize the timer.
    }

    /**
     * Starts the timer if it is not already running
     */
    public void start(){
        if(!timer.isRunning()){
            timer.start();
        }
    }

    /**
     * Called by the timer at each frame to update the game state and redraw the screen
     * @param e the action event triggered by the timer
     */
    @Override
    public void actionPerformed(ActionEvent e){
        gsm.update();
        renderer.repaint();
    }
}
