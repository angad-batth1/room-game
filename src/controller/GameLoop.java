package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import view.Renderer;

public class Gameloop implements ActionListener{
    
    private Timer timer;
    private GameStateManager gsm;
    private Renderer renderer;

    public GameLoop(GamestateManager gsm, Renderer renderer){
        this.gsm = gsm;
        this.renderer = renderer;

        // Calculate the delay

        // Intialize the swing timer, where 'this' means it will call the actionperformed method

    }

    public void start(){
        if(!timer.isRunning()){
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // update all the game math
        // update the visual graphics of the screen.
    }
}
