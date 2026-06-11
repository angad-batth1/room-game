package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import view.Renderer;

public class GameLoop implements ActionListener{
    
    private Timer timer;
    private GameStateManager gsm;
    private Renderer renderer;

    public GameLoop(GameStateManager gsm, Renderer renderer){
        this.gsm = gsm;
        this.renderer = renderer;

        int delay = 1000 / 60; // delay in miliseconds for 60 FPS (1000 ms / 60 frames = 16 ms)
        this.timer = new Timer(delay, this); // Initialize the timer.
    }

    public void start(){
        if(!timer.isRunning()){
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        gsm.update();
        renderer.repaint();
    }
}
