package view; 

import javax.swing.*;
import controller.*;
import utils.Constants;

public class GameWindow{
    // Our only instance is the JFrame
    private JFrame frame;

    public GameWindow(){
        // Initialize the JFrame, set size, close operation.
        frame = new JFrame("Platformer Game");
        frame.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Last year, this was not present and allowed people to stretch the window
        frame.setLocationRelativeTo(null); // Centers the window on the computer monitor

        // Instead of doing frame.add with a method, lets use renderer class to add our drawing canvas to this window
        Renderer renderer = new Renderer();
        InputHandler input = new InputHandler();
        GameStateManager gsm = new GameStateManager();
        renderer.setGSM(gsm);

        // add the keyboard listeners to the canvas
        renderer.addKeyListener(input);
        renderer.setFocusable(true); // tells java panel is allowed to hear keyboard

        // load the first screen into the stack
        gsm.pushState(new MainMenuState(gsm, input));


        // add canvas to window and show
        frame.add(renderer);
        frame.setVisible(true);

        // start the game
        GameLoop gameLoop = new GameLoop(gsm, renderer);
        gameLoop.start();
    }
}